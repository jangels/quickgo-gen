package com.qitoon.framework.utils;


import com.qitoon.framework.exception.InvalidArgumentException;
import com.qitoon.framework.exception.InvalidResultException;

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
