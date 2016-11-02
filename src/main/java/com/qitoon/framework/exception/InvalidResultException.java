package com.qitoon.framework.exception;

/**
 * @author huangjie
 * @since  2016-10-12
 */
public class InvalidResultException extends MangoException {
    public InvalidResultException() {
        super();
    }

    public InvalidResultException(String message) {
        super(message);
    }

    public InvalidResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResultException(Throwable cause) {
        super(cause);
    }

    protected InvalidResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
