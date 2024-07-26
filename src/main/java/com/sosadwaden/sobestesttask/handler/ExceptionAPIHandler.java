package com.sosadwaden.sobestesttask.handler;

import com.sosadwaden.sobestesttask.api.response.ValidationExceptionResponse;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAPIHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionResponse> handleValidationException(ValidationException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(exception.getValidationErrors())
                .source(exception.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
