package io.github.cd871127.hodgepodge.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author anthonychen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TargetDataSource {
    String value() default "";
}
