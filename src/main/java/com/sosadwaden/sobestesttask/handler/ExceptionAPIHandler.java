package com.sosadwaden.sobestesttask.handler;

import com.sosadwaden.sobestesttask.api.response.MessageDtoResponse;
import com.sosadwaden.sobestesttask.api.response.ValidationExceptionResponse;
import com.sosadwaden.sobestesttask.exception.AccountNotFoundException;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class ExceptionAPIHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAPIHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionResponse> handleValidationException(ValidationException exception) {
        ValidationExceptionResponse response = ValidationExceptionResponse.builder()
                .errors(exception.getValidationErrors())
                .source(exception.getMessage())
                .build();

        logger.error("Ошибка валидации: {}", exception.getMessage());
        logger.debug("Детали исключения: ", exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageDtoResponse> handleEntityNotFoundException(AccountNotFoundException exception) {
        MessageDtoResponse response = MessageDtoResponse.builder()
                .message(exception.getMessage())
                .build();

        logger.debug("Детали исключения: ", exception);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<MessageDtoResponse> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        MessageDtoResponse response = MessageDtoResponse.builder()
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
