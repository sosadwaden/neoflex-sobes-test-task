package com.sosadwaden.sobestesttask.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    private final List<ValidationError> validationErrors;

    public ValidationException(String message) {
        super(message);
        this.validationErrors = Collections.emptyList();
    }
    public ValidationException(String message, List<ValidationError> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

}
