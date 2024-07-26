package com.sosadwaden.sobestesttask.validation.impl;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.validation.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.sosadwaden.sobestesttask.validation.ValidateField.validateField;

@Component
public class MailValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(AccountDtoPostRequest account) {
        List<ValidationError> validationErrors = new ArrayList<>();

        validateField(account.getFirstName(), "firstName", "First name является обязательным", validationErrors);
        validateField(account.getEmail(), "email", "Email является обязательным", validationErrors);

        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Пропущены обязательные поля при создании аккаунта при помощи почты", validationErrors);
        }
    }
}
