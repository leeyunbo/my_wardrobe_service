package com.cloth.wardrobe.exception;

/**
 * js 강제 수정하여 서버 로직에 접근하는 경우 발생하는 Exception
 * 보통 특정 사용자가 js를 조작하여 타 사용자의 권한에 접근하려는 경우 발생한다.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException() {

    }
}
