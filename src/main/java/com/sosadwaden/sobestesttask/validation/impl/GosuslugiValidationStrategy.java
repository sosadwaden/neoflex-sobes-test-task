package com.sosadwaden.sobestesttask.validation.impl;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.validation.ValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.sosadwaden.sobestesttask.validation.ValidateField.validateField;

@Component
public class GosuslugiValidationStrategy implements ValidationStrategy {

    @Override
    public void validate(AccountDtoPostRequest account) {
        List<ValidationError> validationErrors = new ArrayList<>();

        validateField(account.getBankId(), "bankId", "Bank ID является обязательным", validationErrors);
        validateField(account.getLastName(), "lastName", "Last name является обязательным", validationErrors);
        validateField(account.getFirstName(), "firstName", "First name является обязательным", validationErrors);
        validateField(account.getMiddleName(), "middleName", "Middle name является обязательным", validationErrors);
        validateField(account.getBirthdate(), "birthdate", "Birth date является обязательным", validationErrors);
        validateField(account.getPassportNumber(), "passportNumber", "Passport number является обязательным", validationErrors);
        validateField(account.getPlaceOfBirth(), "placeOfBirth", "Place Of Birth является обязательным", validationErrors);
        validateField(account.getPhone(), "phone", "Phone является обязательным", validationErrors);
        validateField(account.getRegistrationAddress(), "registrationAddress",
                "Registration address является обязательным", validationErrors);

        if (!validationErrors.isEmpty()) {
            throw new ValidationException("Пропущены обязательные поля при создании аккаунта при помощи госуслуг", validationErrors);
        }
    }
}
