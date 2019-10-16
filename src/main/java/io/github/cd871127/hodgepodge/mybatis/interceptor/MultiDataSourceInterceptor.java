package io.github.cd871127.hodgepodge.mybatis.interceptor;

import io.github.cd871127.hodgepodge.mybatis.annotation.TargetDataSource;
import io.github.cd871127.hodgepodge.mybatis.datasource.MultiDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

import java.lang.reflect.Method;

/**
 * @author anthonychen
 */
@Slf4j
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
        return invocation.proceed();
    }


}
