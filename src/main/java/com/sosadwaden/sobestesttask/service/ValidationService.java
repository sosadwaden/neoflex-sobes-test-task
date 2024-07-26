package com.sosadwaden.sobestesttask.service;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;

public interface ValidationService {

    void validate(String source, AccountDtoPostRequest accountDtoPostRequest);
}
