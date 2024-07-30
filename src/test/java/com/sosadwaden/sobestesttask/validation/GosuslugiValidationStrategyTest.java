package com.sosadwaden.sobestesttask.validation;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationError;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.validation.impl.GosuslugiValidationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class GosuslugiValidationStrategyTest {

    private GosuslugiValidationStrategy gosuslugiValidationStrategy;

    @BeforeEach
    public void setUp() {
        gosuslugiValidationStrategy = new GosuslugiValidationStrategy();
    }

    @Test
    public void throwValidationException_GosuslugiValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();

        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            gosuslugiValidationStrategy.validate(account);
        });

        Assertions.assertEquals("Пропущены обязательные поля при создании аккаунта при помощи госуслуг", exception.getMessage());

        List<ValidationError> errors = exception.getValidationErrors();
        Assertions.assertEquals(9, errors.size());
    }

    @Test
    public void passedValidation_GosuslugiValidationStrategy() {
        AccountDtoPostRequest account = new AccountDtoPostRequest();
        account.setBankId(5L);
        account.setLastName("lastName");
        account.setFirstName("firstName");
        account.setMiddleName("middleName");
        account.setBirthdate(LocalDate.of(1990, 10, 10));
        account.setPassportNumber("1234 567890");
        account.setPlaceOfBirth("Place of Birth");
        account.setPhone("123-456-7890");
        account.setRegistrationAddress("1234 Registration Address");

        Assertions.assertDoesNotThrow(() -> gosuslugiValidationStrategy.validate(account));
    }
}
