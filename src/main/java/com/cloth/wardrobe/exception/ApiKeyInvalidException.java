package com.cloth.wardrobe.exception;

public class ApiKeyInvalidException extends RuntimeException {
    public ApiKeyInvalidException() {
        super();
    }

    public ApiKeyInvalidException(String message) {
        super(message);
    }

    public ApiKeyInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiKeyInvalidException(Throwable cause) {
        super(cause);
    }

    protected ApiKeyInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
