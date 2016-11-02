package com.qitoon.framework.utils;

import org.springframework.beans.BeanUtils;

/**
 * create by huangjie
 * on  2016-05-12
 */
public class BeanCopy {

    public static <T> T convert(Class<T> cla,Object value){
        try {
            Object dest = cla.newInstance();
            BeanUtils.copyProperties(dest,value);
            return (T) dest;
        } catch (InstantiationException | IllegalAccessException  e) {
            throw new RuntimeException(e.getCause());
        }
    }

}
