package io.github.cd871127.hodgepodge.mybatis.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;

/**
 * @author anthonychen
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class}
)})
public class ShardingInterceptor extends AbstractMybatisInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }
}
