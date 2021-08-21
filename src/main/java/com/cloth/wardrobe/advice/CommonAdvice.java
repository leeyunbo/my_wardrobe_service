package com.cloth.wardrobe.advice;

import com.cloth.wardrobe.dto.common.ResponseForError;
import com.cloth.wardrobe.exception.BadRequestException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * 모든 데이터에 대한 공통 Exception을 관리하는 Advice
 * 2021-08-16 이윤복
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)   // Advice의 우선순위를 의미한다.
public class CommonAdvice {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchError(Exception e) {
        ResponseForError responseForError = new ResponseForError();
        responseForError.set_code(200);
        responseForError.set_message("잘못된 입력입니다.");
        return new ResponseEntity<>(responseForError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(Exception e) {
        ResponseForError responseForError = new ResponseForError();
        responseForError.set_code(200);
        responseForError.set_message("잘못된 접근입니다.");
        return new ResponseEntity<>(responseForError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> noContent(Exception e) {
        ResponseForError responseForError = new ResponseForError();
        responseForError.set_code(200);
        responseForError.set_message("잘못된 접근입니다.");
        return new ResponseEntity<>(responseForError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) {
        ResponseForError responseForError = new ResponseForError();
        responseForError.set_code(200);
        responseForError.set_message("서버 점검 중입니다.");
        return new ResponseEntity<>(responseForError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
