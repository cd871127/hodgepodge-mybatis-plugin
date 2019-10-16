package io.github.cd871127.hodgepodge.mybatis.interceptor;

import io.github.cd871127.hodgepodge.mybatis.annotation.TargetDataSource;
import io.github.cd871127.hodgepodge.mybatis.datasource.MultiDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;

/**
 * @author anthonychen
 */
@Slf4j
@Intercepts(value = {
        @Signature(type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
                        CacheKey.class, BoundSql.class})})
public class MultiDataSourceInterceptor extends AbstractMybatisInterceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = null;
        for (Object o : invocation.getArgs()) {
            if (o instanceof MappedStatement) {
                mappedStatement = (MappedStatement) o;
                break;
            }
        }
        if (mappedStatement != null) {
            String signature = mappedStatement.getId();
            String className = signature.substring(0, signature.lastIndexOf("."));
            String methodName = signature.substring(signature.lastIndexOf(".") + 1);
            Class<?> clazz = Class.forName(className);
            TargetDataSource targetDataSource = clazz.getAnnotation(TargetDataSource.class);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    TargetDataSource tmp = method.getAnnotation(TargetDataSource.class);
                    targetDataSource = tmp == null ? targetDataSource : tmp;
                    break;
                }
            }
            MultiDataSource.getInstance().setCurrentDataSource(targetDataSource == null ? null : targetDataSource.value());
        }
        log.debug("targetDataSource: {}", MultiDataSource.getInstance().getCurrentDataSource());
        //TODO 用反射获取transaction的connection如果为空不处理，如果不为空，需要和datasource的con比较，如果不一样要还con
        Object result = invocation.proceed();
        MultiDataSource.getInstance().clear();
        return result;
    }


}
