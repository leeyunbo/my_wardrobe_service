package com.cloth.wardrobe.advice;

import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.exception.WrongAccessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 게시판과 같이 등록할 수 있는 데이터에 대한 Exception을 관리하는 Advice
 * 2021-08-02 이윤복
 */
@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)   // Advice의 우선순위를 의미한다.
public class PostAdvice {

    @ExceptionHandler(WrongAccessException.class)
    public ResponseEntity<?> doNotClicked(Exception e) {
        Response responseForError = new Response();
        responseForError.set_code(200);
        responseForError.set_message("권한이 없습니다.");
        return new ResponseEntity<>(responseForError, HttpStatus.BAD_REQUEST);
    }
}
