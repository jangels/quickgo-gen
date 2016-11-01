package com.qitoon.framework.annotation;

/**
 * @author : huangjie
 * @since : 16/5/2
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
public @interface Alias {
    String value();
}
