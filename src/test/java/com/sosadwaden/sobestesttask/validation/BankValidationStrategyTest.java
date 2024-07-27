package com.sosadwaden.sobestesttask.validation;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.validation.impl.BankValidationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

class BankValidationStrategyTest {

    private BankValidationStrategy bankValidationStrategy;

    @BeforeEach
    public void setUp() {
        bankValidationStrategy = new BankValidationStrategy();
    }

    @Test
    public void throwValidationException_BankValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            bankValidationStrategy.validate(account);
        });

        Assertions.assertEquals("Пропущены обязательные поля при создании аккаунта при помощи банка", exception.getMessage());
        List<ValidationError> errors = exception.getValidationErrors();
        Assertions.assertEquals(6, errors.size());
    }

    @Test
    public void passedValidation_BankValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();
        account.setBankId(5L);
        account.setLastName("lastName");
        account.setFirstName("firstName");
        account.setMiddleName("middleName");
        account.setBirthdate(LocalDate.of(1990, 10, 10));
        account.setPassportNumber("1234 567890");

        Assertions.assertDoesNotThrow(() -> bankValidationStrategy.validate(account));
    }
}
