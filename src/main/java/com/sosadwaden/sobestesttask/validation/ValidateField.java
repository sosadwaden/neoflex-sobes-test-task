package com.sosadwaden.sobestesttask.validation;

import com.sosadwaden.sobestesttask.exception.ValidationError;

import java.util.List;

public class ValidateField {

    public static void validateField(Object fieldValue, String fieldName, String errorMessage, List<ValidationError> errors) {
        if (fieldValue == null) {
            errors.add(new ValidationError(fieldName, errorMessage));
        }
    }
}
