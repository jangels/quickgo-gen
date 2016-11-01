package com.qitoon.framework.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhoujingjie
 * @date 2016/4/22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Delete {

    String[] value() default {};

    String template() default "";

    boolean singleton() default false;
}
