package com.cloth.wardrobe.advice;

import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.exception.DoNotFoundContentException;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)   // Advice의 우선순위를 의미한다.
public class CommonAdvice {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchError(Exception e) {
        Response responseForError = createResponse(e);
        return new ResponseEntity<>(responseForError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(Exception e) {
        Response responseForError = createResponse(e);
        return new ResponseEntity<>(responseForError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoNotFoundContentException.class)
    public ResponseEntity<?> doNotFoundContent(Exception e) {
        Response responseForError = createResponse(e);
        return new ResponseEntity<>(responseForError, HttpStatus.NO_CONTENT);
    }

    private Response createResponse(Exception e) {
        Response responseForError = new Response();
        responseForError.set_code(200);
        responseForError.set_message(e.getMessage());
        return responseForError;
    }
}
