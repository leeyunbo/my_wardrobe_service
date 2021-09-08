package com.cloth.wardrobe.exception;

public class DoNotFoundContentException extends RuntimeException {
    public DoNotFoundContentException() {
        super();
    }

    public DoNotFoundContentException(String message) {
        super(message);
    }

    public DoNotFoundContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoNotFoundContentException(Throwable cause) {
        super(cause);
    }

    protected DoNotFoundContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
