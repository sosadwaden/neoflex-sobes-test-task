package com.sosadwaden.sobestesttask.service;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoGetResponse;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;

import java.util.UUID;

public interface AccountService {

    AccountDtoPostResponse createAccount(String source, AccountDtoPostRequest request);

    AccountDtoGetResponse getAccountById(UUID id);

    AccountDtoGetResponse searchAccount(String lastName,
                                        String firstName,
                                        String middleName,
                                        String phone,
                                        String email);
}
