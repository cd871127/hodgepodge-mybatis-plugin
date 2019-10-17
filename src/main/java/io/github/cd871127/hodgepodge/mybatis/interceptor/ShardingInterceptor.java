package io.github.cd871127.hodgepodge.mybatis.interceptor;

import io.github.cd871127.hodgepodge.mybatis.annotation.Sharding;
import io.github.cd871127.hodgepodge.mybatis.annotation.Table;
import io.github.cd871127.hodgepodge.mybatis.sharding.stragtegy.ShardingStrategy;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;

/**
 * @author anthonychen
 */
//@Intercepts(value = {
//        @Signature(type = Executor.class,
//                method = "update",
//                args = {MappedStatement.class, Object.class}),
//        @Signature(type = Executor.class,
//                method = "query",
//                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
//                        CacheKey.class, BoundSql.class})})
@Intercepts(value = {
        @Signature(type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class})})
public class ShardingInterceptor extends AbstractMybatisInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        BoundSql boundSql = routingStatementHandler.getBoundSql();
        String sql = boundSql.getSql();

        Field field = RoutingStatementHandler.class.getDeclaredField("delegate");
        field.setAccessible(true);
        PreparedStatementHandler preparedStatementHandler = (PreparedStatementHandler) field.get(routingStatementHandler);

        field = BaseStatementHandler.class.getDeclaredField("mappedStatement");
        field.setAccessible(true);
        MappedStatement mappedStatement = (MappedStatement) field.get(preparedStatementHandler);
        Sharding sharding = getAnnotation(mappedStatement, Sharding.class);
        Table[] tables = sharding.tables();
        for (Table table : tables) {
            table.cols();
            ShardingStrategy strategy = (ShardingStrategy) table.strategyClass().newInstance();
            sql = strategy.sharding(sql, table.tableName(), table.cols());
        }
        field = BoundSql.class.getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, sql);
        return invocation.proceed();
    }
}
