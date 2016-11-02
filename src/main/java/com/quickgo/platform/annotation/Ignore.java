package com.quickgo.platform.annotation;

import java.lang.annotation.ElementType;

/**
 * @author : huangjie
 * @since : 16/5/2
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
public @interface Ignore {
}
