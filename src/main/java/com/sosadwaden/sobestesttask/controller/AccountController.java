package com.sosadwaden.sobestesttask.controller;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import com.sosadwaden.sobestesttask.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDtoPostResponse> createAccount(@RequestHeader("x-Source") String source,
                                                 @RequestBody AccountDtoPostRequest request) {
        AccountDtoPostResponse response = accountService.createAccount(source, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDtoPostResponse> getAccountById(@PathVariable UUID id) {
        AccountDtoPostResponse response = accountService.getAccountById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccount(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {

        if (lastName == null && firstName == null && middleName == null && phone == null && email == null) {
            return ResponseEntity.badRequest().body("Должен быть указан хотя бы один параметр поиска");
        }

        Optional<Account> account = accountService.searchAccount(lastName, firstName, middleName, phone, email);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
