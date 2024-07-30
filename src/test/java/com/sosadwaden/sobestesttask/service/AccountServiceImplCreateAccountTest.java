package com.sosadwaden.sobestesttask.service;

import com.sosadwaden.sobestesttask.entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.mapper.AccountMapper;
import com.sosadwaden.sobestesttask.repository.AccountRepository;
import com.sosadwaden.sobestesttask.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplCreateAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() {
        AccountDtoPostRequest request = AccountDtoPostRequest.builder()
                .bankId(5L)
                .lastName("Кузнецов")
                .firstName("Иван")
                .middleName("Иванович")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportNumber("AB123456")
                .placeOfBirth("City")
                .phone("123-456-7890")
                .registrationAddress("123 Street")
                .build();
        UUID id = UUID.randomUUID();
        Account account = new Account();
        account.setId(id);

        AccountDtoPostResponse expectedResponse = AccountDtoPostResponse.builder()
                .id(id)
                .message("Аккаунт успешно создан")
                .build();

        when(accountMapper.toEntity(request)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        AccountDtoPostResponse response = accountService.createAccount("mail", request);

        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getMessage(), response.getMessage());
        verify(validationService).validate("mail", request);
        verify(accountRepository).save(account);
    }

    @Test
    public void testCreateAccount_ValidationError() {
        AccountDtoPostRequest request = AccountDtoPostRequest.builder()
                .bankId(5L)
                .lastName("Кузнецов")
                .firstName("Иван")
                .middleName("Иванович")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportNumber("AB123456")
                .placeOfBirth("City")
                .phone("123-456-7890")
                .registrationAddress("123 Street")
                .build();
        doThrow(new ValidationException("Validation failed")).when(validationService).validate("mail", request);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            accountService.createAccount("mail", request);
        });
        assertEquals("Validation failed", exception.getMessage());
        verify(validationService).validate("mail", request);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    public void testCreateAccount_SaveError() {
        AccountDtoPostRequest request = AccountDtoPostRequest.builder()
                .bankId(5L)
                .lastName("Кузнецов")
                .firstName("Иван")
                .middleName("Иванович")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportNumber("AB123456")
                .placeOfBirth("City")
                .phone("123-456-7890")
                .registrationAddress("123 Street")
                .build();
        UUID id = UUID.randomUUID();
        Account account = new Account();
        account.setId(id);

        when(accountMapper.toEntity(request)).thenReturn(account);
        when(accountRepository.save(account)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount("mail", request);
        });
        assertEquals("Не удалось сохранить сущность в БД", exception.getMessage());
        verify(validationService).validate("mail", request);
        verify(accountRepository).save(account);
    }
}
