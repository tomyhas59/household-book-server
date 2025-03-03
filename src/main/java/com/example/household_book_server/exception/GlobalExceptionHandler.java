package com.example.household_book_server.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice //모든 컨트롤러에서 발생하는 예외를 공통으로 처리하는 역할
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class) //특정 예외가 발생했을 때 실행되는 메서드를 지정
    public ResponseEntity<Map<String, String>> /*JSON 형식으로 오류 메시지를 응답*/
    handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode()) // 예외에서 설정한 HTTP 상태 코드 유지 (예: 400, 403, 404 등)
                .body(Map.of("message", ex.getReason())); // 예외에서 설정한 메시지를 JSON 응답으로 반환
    }
}
