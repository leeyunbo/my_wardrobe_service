package com.cloth.wardrobe.exception;

public class JwtAuthenticaitonException extends RuntimeException {
    public JwtAuthenticaitonException() {
        super();
    }

    public JwtAuthenticaitonException(String message) {
        super(message);
    }

    public JwtAuthenticaitonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticaitonException(Throwable cause) {
        super(cause);
    }

    protected JwtAuthenticaitonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
