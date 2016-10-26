package com.qitoon.framework.utils;


import com.qitoon.framework.exception.InvalidArgumentException;
import com.qitoon.framework.exception.InvalidResultException;
import com.qitoon.framework.param.Parameter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author huangjie
 * @since  2016-05-12
 */
public class AssertUtils {

    public static void notNull(Object data, String errorMsg) {
        if (data == null)
            throw new InvalidArgumentException(errorMsg);
        if (data instanceof String) {
            if (data ==null) {
                throw new InvalidArgumentException(errorMsg);
            }
        }
    }

    public static void notNull(Parameter parameter, String... keys) {
        if (keys != null && keys.length > 0) {
            for(String key:keys){
                if(StringUtils.isBlank(parameter.getParamString().get(key))){
                    if(parameter.getParamFile().get(key) == null || parameter.getParamFile().get(key).size()==0)
                        throw new InvalidArgumentException("missing "+key);
                }
            }
        }
    }

    public static void isTrue(boolean expression, String errorMsg) {
        if (!expression)
            throw new InvalidArgumentException(errorMsg);
    }

    public static void error(String errorMsg) {
        throw new InvalidResultException(errorMsg);
    }

    public static void result(boolean rs, String errorMsg) {
        if (!rs)
            throw new InvalidResultException(errorMsg);
    }

}
