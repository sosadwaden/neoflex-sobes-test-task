package com.sosadwaden.sobestesttask.service;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.response.AccountDtoGetResponse;
import com.sosadwaden.sobestesttask.exception.AccountNotFoundException;
import com.sosadwaden.sobestesttask.mapper.AccountMapper;
import com.sosadwaden.sobestesttask.repository.AccountRepository;
import com.sosadwaden.sobestesttask.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplGetAccountByIdTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private UUID id;
    private Account account;
    private AccountDtoGetResponse accountDtoGetResponse;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        account = new Account();
        account.setId(id);

        accountDtoGetResponse = new AccountDtoGetResponse();
        accountDtoGetResponse.setId(id);
        accountDtoGetResponse.setFirstName("Иван");
        accountDtoGetResponse.setLastName("Кузнецов");
        accountDtoGetResponse.setEmail("example@example.com");
    }

    @Test
    public void getAccountById_Success() {
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));
        when(accountMapper.toResponseDto(account)).thenReturn(accountDtoGetResponse);

        AccountDtoGetResponse response = accountService.getAccountById(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        verify(accountRepository).findById(id);
        verify(accountMapper).toResponseDto(account);
    }

    @Test
    public void getAccountById_NotFound() {
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(id));

        verify(accountRepository).findById(id);
        verify(accountMapper, never()).toResponseDto(any());
    }
}
