package com.qitoon.framework.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author : huangjie
 * @since : 16/9/14
 */
public class JsonUtils {
    public static String toString(Object data){
        return JSON.toJSONString(data, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteMapNullValue);
    }
}
