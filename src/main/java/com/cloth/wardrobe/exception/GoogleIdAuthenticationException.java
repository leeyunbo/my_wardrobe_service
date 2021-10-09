package com.cloth.wardrobe.exception;

public class GoogleIdAuthenticationException extends RuntimeException {

    public GoogleIdAuthenticationException() {
        super();
    }

    public GoogleIdAuthenticationException(String message) {
        super(message);
    }

    public GoogleIdAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoogleIdAuthenticationException(Throwable cause) {
        super(cause);
    }

    protected GoogleIdAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
