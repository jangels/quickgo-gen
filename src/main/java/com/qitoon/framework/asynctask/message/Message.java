package com.qitoon.framework.asynctask.message;

import java.lang.annotation.ElementType;

/**
 * @author : huangjie
 * @since : 16/5/2
 */
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.METHOD})
public @interface Message {
    String value();
}
