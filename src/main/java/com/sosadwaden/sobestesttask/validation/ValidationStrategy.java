package com.sosadwaden.sobestesttask.validation;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;

public interface ValidationStrategy {
    void validate(AccountDtoPostRequest accountDtoPostRequest);
}
