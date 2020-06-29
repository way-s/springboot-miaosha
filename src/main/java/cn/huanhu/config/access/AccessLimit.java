package cn.huanhu.config.access;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author m
 * @className AccessLimit
 * @description AccessLimit
 * @date 2020/6/28
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
