package com.qitoon.framework.thirdly.qq;


import com.qitoon.framework.thirdly.exception.ThirdlyException;

/**
 * @author huangjie
 * @since  2016-07-28
 */
public class QQException extends ThirdlyException {
    public QQException() {
        super();
    }

    public QQException(String message) {
        super(message);
    }

    public QQException(String message, Throwable cause) {
        super(message, cause);
    }

    public QQException(Throwable cause) {
        super(cause);
    }

    protected QQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
