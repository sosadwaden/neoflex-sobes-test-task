package com.sosadwaden.sobestesttask.service;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    AccountDtoPostResponse createAccount(String source, AccountDtoPostRequest request);

    AccountDtoPostResponse getAccountById(UUID id);

    Optional<Account> searchAccount(String lastName,
                           String firstName,
                           String middleName,
                           String phone,
                           String email);
}
