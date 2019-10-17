package io.github.cd871127.hodgepodge.mybatis.interceptor;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author anthonychen
 */
public abstract class AbstractMybatisInterceptor implements Interceptor {
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @SuppressWarnings("unchecked")
    protected <T> T getArg(Invocation invocation, Class<T> clazz) {
        for (Object o : invocation.getArgs()) {
            if (o.getClass().equals(clazz)) {
                return (T) o;
            }
        }
        return null;
    }

    protected <T extends Annotation> T getAnnotation(MappedStatement mappedStatement, Class<T> annotationClazz) throws ClassNotFoundException {
        String signature = mappedStatement.getId();
        String className = signature.substring(0, signature.lastIndexOf("."));
        String methodName = signature.substring(signature.lastIndexOf(".") + 1);
        Class<?> clazz = Class.forName(className);
        T annotation = clazz.getAnnotation(annotationClazz);
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                T tmp = method.getAnnotation(annotationClazz);
                annotation = tmp == null ? annotation : tmp;
                break;
            }
        }
        return annotation;
    }
}
