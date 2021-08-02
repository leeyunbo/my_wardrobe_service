package com.cloth.wardrobe.advice;

import com.cloth.wardrobe.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 게시판과 같이 등록할 수 있는 데이터에 대한 Exception을 관리하는 Advice
 * 2021-08-02 이윤복
 */
@RestControllerAdvice
public class PostAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> noContent(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
