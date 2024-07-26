package com.sosadwaden.sobestesttask.service.impl;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import com.sosadwaden.sobestesttask.mapper.AccountMapper;
import com.sosadwaden.sobestesttask.repository.AccountRepository;
import com.sosadwaden.sobestesttask.repository.AccountSpecifications;
import com.sosadwaden.sobestesttask.service.AccountService;
import com.sosadwaden.sobestesttask.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ValidationService validationService;
    private final AccountMapper accountMapper;
    private final AccountSpecifications accountSpecifications;


    @Override
    public AccountDtoPostResponse createAccount(String source, AccountDtoPostRequest request) {
        validationService.validate(source, request);

        Account account = accountMapper.toEntity(request);

        account = accountRepository.save(account);

        return accountMapper.toResponseDto(account);
    }

    @Override
    public AccountDtoPostResponse getAccountById(UUID id) {
        return accountRepository.findById(id)
                .map(accountMapper::toResponseDto)
                .orElseThrow(() -> new EntityNotFoundException("Аккаунт не найден"));
    }

    @Override
    public Optional<Account> searchAccount(String lastName,
                                           String firstName,
                                           String middleName,
                                           String phone,
                                           String email) {
        return accountRepository.findOne(accountSpecifications.getAccountsByCriteria(lastName, firstName, middleName, phone, email));
    }
}
