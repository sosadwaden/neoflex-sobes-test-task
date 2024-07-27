package com.sosadwaden.sobestesttask.validation;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.validation.impl.MailValidationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MailValidationStrategyTest {

    private MailValidationStrategy mailValidationStrategy;

    @BeforeEach
    public void setUp() {
        mailValidationStrategy = new MailValidationStrategy();
    }

    @Test
    public void throwValidationException_MailValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            mailValidationStrategy.validate(account);
        });

        Assertions.assertEquals("Пропущены обязательные поля при создании аккаунта при помощи почты", exception.getMessage());
        List<ValidationError> errors = exception.getValidationErrors();
        Assertions.assertEquals(2, errors.size());
    }

    @Test
    public void passedValidation_MailValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();
        account.setFirstName("firstName");
        account.setEmail("email@example.com");

        Assertions.assertDoesNotThrow(() -> mailValidationStrategy.validate(account));
    }
}
