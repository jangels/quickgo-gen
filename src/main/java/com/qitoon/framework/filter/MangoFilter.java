package com.qitoon.framework.filter;

import com.qitoon.framework.param.Parameter;

import java.lang.reflect.Method;

/**
 * @author huangjie
 * @since  2016-10-22
 */
public interface MangoFilter {
    boolean doFilter(Parameter parameter, Method method) throws Exception;
}
