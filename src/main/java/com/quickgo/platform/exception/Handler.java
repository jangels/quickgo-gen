package com.quickgo.platform.exception;

/**
 * @author huangjie
 * @since  2016-07-27
 */
public interface Handler<T> {

    String key(T item);

}
