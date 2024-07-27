package com.sosadwaden.sobestesttask.validation;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.validation.impl.MobileValidationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MobileValidationStrategyTest {

    private MobileValidationStrategy mobileValidationStrategy;

    @BeforeEach
    public void setUp() {
        mobileValidationStrategy = new MobileValidationStrategy();
    }

    @Test
    public void throwValidationException_MobileValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            mobileValidationStrategy.validate(account);
        });

        Assertions.assertEquals("Пропущены обязательные поля при создании аккаунта при помощи номера телефона", exception.getMessage());

        List<ValidationError> errors = exception.getValidationErrors();
        Assertions.assertEquals(1, errors.size());
        Assertions.assertEquals("phone", errors.get(0).getField());
        Assertions.assertEquals("Phone является обязательным", errors.get(0).getMessage());
    }

    @Test
    public void passedValidation_MobileValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();
        account.setPhone("123-456-7890");

        Assertions.assertDoesNotThrow(() -> mobileValidationStrategy.validate(account));
    }
}
