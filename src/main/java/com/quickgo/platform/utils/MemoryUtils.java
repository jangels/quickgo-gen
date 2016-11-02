package com.quickgo.platform.utils;

import com.quickgo.platform.cache.Cache;
import com.quickgo.platform.cache.MemoryCache;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Parameter;

import java.util.UUID;

/**
 * @author : huangjie
 * @since : 16/10/4
 */
public class MemoryUtils {
    private static Cache cache;
    private static int expires=  ConfigUtils.getTokenExpires();
    static {
        cache = new MemoryCache();
    }

    public static String token(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }
    public static void putUser(String token, User user){
        cache.put(token,"user",user,expires);
    }

    public static User getUser(String token){
        return (User) cache.get(token,"user",expires);
    }

    public static User getUser(Parameter parameter){
        String token=parameter.getParamString().get("token");
        if(""==token)
            return null;
        return (User) cache.get(token,"user",expires);
    }

    public static void put(String token,String key,Object data){
        cache.put(token,key,data,expires);
    }
    public static Object get(String token,String key){
        return cache.get(token,key,expires);
    }
}
