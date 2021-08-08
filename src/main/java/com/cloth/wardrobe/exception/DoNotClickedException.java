package com.cloth.wardrobe.exception;

public class DoNotClickedException extends RuntimeException {
    public DoNotClickedException() {
        super();
    }

    public DoNotClickedException(String message) {
        super(message);
    }

    public DoNotClickedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoNotClickedException(Throwable cause) {
        super(cause);
    }
}
