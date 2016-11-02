package com.quickgo.platform.cache;

/**
 * @author huangjie
 * @since :  2016-07-28
 */
public interface Cache {

    void put(String token, String key, Object data, int expires);

    Object get(String token, String key, int expires);

}
