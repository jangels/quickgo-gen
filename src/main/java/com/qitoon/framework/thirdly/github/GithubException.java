package com.qitoon.framework.thirdly.github;


import com.qitoon.framework.thirdly.exception.ThirdlyException;

/**
 * @author : huangjie
 * @since : 16/9/2
 */
public class GithubException extends ThirdlyException {
    public GithubException() {
    }

    public GithubException(String message) {
        super(message);
    }

    public GithubException(String message, Throwable cause) {
        super(message, cause);
    }

    public GithubException(Throwable cause) {
        super(cause);
    }

    public GithubException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
