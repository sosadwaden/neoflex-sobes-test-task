package com.sosadwaden.sobestesttask.service;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.response.AccountDtoGetResponse;
import com.sosadwaden.sobestesttask.exception.AccountNotFoundException;
import com.sosadwaden.sobestesttask.mapper.AccountMapper;
import com.sosadwaden.sobestesttask.repository.AccountRepository;
import com.sosadwaden.sobestesttask.repository.AccountSpecifications;
import com.sosadwaden.sobestesttask.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplSearchAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountSpecifications accountSpecifications;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private AccountDtoGetResponse accountDtoGetResponse;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(UUID.randomUUID());
        account.setFirstName("Иван");
        account.setLastName("Кузнецов");
        account.setEmail("example@example.com");

        accountDtoGetResponse = new AccountDtoGetResponse();
        accountDtoGetResponse.setId(account.getId());
        accountDtoGetResponse.setFirstName("Иван");
        accountDtoGetResponse.setLastName("Кузнецов");
        accountDtoGetResponse.setEmail("example@example.com");
    }

    @Test
    public void searchAccount_Success() {
        Specification<Account> spec = (root, query, builder) -> builder.equal(root.get("lastName"), "Кузнецов");
        when(accountSpecifications.getAccountsByCriteria(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(spec);
        when(accountRepository.findOne(spec)).thenReturn(Optional.of(account));
        when(accountMapper.toResponseDto(account)).thenReturn(accountDtoGetResponse);

        AccountDtoGetResponse response = accountService.searchAccount("Кузнецов", "Иван", "Иванович", "123-456-7890", "example@example.com");

        assertNotNull(response);
        assertEquals(account.getId(), response.getId());
        assertEquals(account.getFirstName(), response.getFirstName());
        assertEquals(account.getLastName(), response.getLastName());
        assertEquals(account.getEmail(), response.getEmail());

        verify(accountRepository).findOne(spec);
        verify(accountMapper).toResponseDto(account);
    }

    @Test
    public void searchAccount_NotFound() {
        Specification<Account> spec = (root, query, builder) -> builder.equal(root.get("lastName"), "Кузнецов");
        when(accountSpecifications.getAccountsByCriteria(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(spec);
        when(accountRepository.findOne(spec)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.searchAccount("Кузнецов", "Иван", "Иванович", "123-456-7890", "example@example.com"));

        verify(accountRepository).findOne(spec);
        verify(accountMapper, never()).toResponseDto(any());
    }
}
