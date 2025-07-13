package org.istad.mobilebankingfs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class HandleServiceException {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException exception){
        return new ResponseEntity<>(Map.of(
                "message", "Error business logic",
                "status", exception.getStatusCode().value(),
                "timestamp", LocalDateTime.now(),
                "details", exception.getReason()
        ), exception.getStatusCode());
    }
}
