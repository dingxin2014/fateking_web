package com.fateking.exception;

import java.io.Serializable;

/**
 * Created by dingxin on 17/1/9.
 */
public class FatekingException extends RuntimeException implements Serializable{


    public FatekingException() {
        super();
    }

    public FatekingException(String message) {
        super(message);
    }

    public FatekingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FatekingException(Throwable cause) {
        super(cause);
    }

    protected FatekingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
