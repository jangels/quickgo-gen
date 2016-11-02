package com.qitoon.framework.asynctask;

import java.lang.reflect.Method;

/**
 * @author : huangjie
 * @since : 16/9/14
 */
public class ObjectMethod {
    private Object instance;
    private Method method;

    public ObjectMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
