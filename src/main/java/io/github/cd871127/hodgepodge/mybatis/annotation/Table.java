package io.github.cd871127.hodgepodge.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author anthonychen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Table {
    String tableName();

    String[] cols();

    Class<?> strategyClass();

}
