package com.sosadwaden.sobestesttask.exception;

import com.sosadwaden.sobestesttask.service.impl.AccountMatchInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class DuplicateAccountException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<AccountMatchInfo> matchedAccounts;

    public DuplicateAccountException(String message, List<AccountMatchInfo> matchedAccounts) {
        super(message);
        this.matchedAccounts = matchedAccounts;
    }
}
