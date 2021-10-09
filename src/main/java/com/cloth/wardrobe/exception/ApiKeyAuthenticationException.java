package com.cloth.wardrobe.exception;

public class ApiKeyAuthenticationException extends RuntimeException {
    public ApiKeyAuthenticationException() {
        super();
    }

    public ApiKeyAuthenticationException(String message) {
        super(message);
    }

    public ApiKeyAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiKeyAuthenticationException(Throwable cause) {
        super(cause);
    }

    protected ApiKeyAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
