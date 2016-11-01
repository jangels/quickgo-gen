package com.qitoon.framework.utils;


import com.qitoon.framework.exception.Handler;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : huangjie
 * @since : 16/5/11
 */
public class ResultUtils {

    public static <T> List<T> list(List<T> rs) {
        if (rs == null) {
            return new ArrayList<>();
        }
        return rs;
    }

    public static <T> T get(List<T> rs) {
        if (rs != null && rs.size() > 0)
            return rs.get(0);
        return null;
    }

    public static <T> Map<String, List<T>> listToMap(List<T> list, Handler handler) {
        Map<String, List<T>> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for(T item:list){
                String key = handler.key(item);
                List<T> temp = map.get(key);
                if(temp == null){
                    temp = new ArrayList<>();
                    map.put(key,temp);
                }
                temp.add(item);
            }
        }
        return map;
    }
}
