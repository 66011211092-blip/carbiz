package com.example.carbiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", "validation_error");
        body.put("details", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> Map.of(err.getField(), err.getDefaultMessage()))
                .toList());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        return build(HttpStatus.BAD_REQUEST, "invalid_operation", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "server_error", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String code, String msg) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", code);
        body.put("message", msg);
        return ResponseEntity.status(status).body(body);
    }
}
