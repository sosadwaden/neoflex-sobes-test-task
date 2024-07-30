package com.sosadwaden.sobestesttask.service.impl;

import com.sosadwaden.sobestesttask.entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoGetResponse;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import com.sosadwaden.sobestesttask.exception.AccountNotFoundException;
import com.sosadwaden.sobestesttask.exception.DuplicateAccountException;
import com.sosadwaden.sobestesttask.mapper.AccountMapper;
import com.sosadwaden.sobestesttask.repository.AccountRepository;
import com.sosadwaden.sobestesttask.repository.AccountSpecifications;
import com.sosadwaden.sobestesttask.service.AccountService;
import com.sosadwaden.sobestesttask.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ValidationService validationService;
    private final AccountMapper accountMapper;
    private final AccountSpecifications accountSpecifications;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public AccountDtoPostResponse createAccount(String source, AccountDtoPostRequest request) {
        logger.info("Запрос на создание аккаунта с источником: {} и данными: {}", source, request);

        validationService.validate(source, request);
        logger.debug("Валидация прошла успешно");

        Account account = accountMapper.toEntity(request);
        logger.debug("Преобразование DTO в сущность: {}", account);

        try {
            account = accountRepository.save(account);
            logger.debug("Сущность успешно сохранена в БД: {}", account);
        } catch (Exception e) {
            logger.error("Ошибка при сохранении сущности в БД: {}", e.getMessage(), e);
            throw new RuntimeException("Не удалось сохранить сущность в БД", e);
        }

        return AccountDtoPostResponse.builder()
                .id(account.getId())
                .message("Аккаунт успешно создан")
                .build();
    }

    @Override
    public AccountDtoGetResponse getAccountById(UUID id) {
        logger.info("Поиск аккаунта с UUID: {}", id);

        return accountRepository.findById(id)
                .map(account -> {
                    logger.info("Аккаунт найден: {}", account);
                    return accountMapper.toResponseDto(account);
                })
                .orElseThrow(() -> {
                    logger.warn("Аккаунт с UUID: {} не найден", id);
                    return new AccountNotFoundException("Аккаунт с UUID: " + id + " не найден");
                });
    }

    @Override
    public AccountDtoGetResponse searchAccount(String lastName,
                                               String firstName,
                                               String middleName,
                                               String phone,
                                               String email) {
        logger.info("Начинается поиск аккаунта");

        List<Account> accounts = accountRepository.findAll(accountSpecifications.getAccountsByCriteria(
                lastName, firstName, middleName, phone, email));

        if (accounts.isEmpty()) {
            logger.warn("Аккаунт не найден по указанным критериям");
            throw new AccountNotFoundException("Учетная запись не найдена в соответствии с указанными критериями");
        }

        List<AccountMatchInfo> matchedAccounts = new ArrayList<>();

        for (Account account : accounts) {

            AccountMatchInfo matchInfo = new AccountMatchInfo(account);

            if (lastName != null && lastName.equals(account.getLastName())) {
                matchInfo.addMatchedCriterion("lastName");
            }

            if (firstName != null && firstName.equals(account.getFirstName())) {
                matchInfo.addMatchedCriterion("firstName");
            }

            if (middleName != null && middleName.equals(account.getMiddleName())) {
                matchInfo.addMatchedCriterion("middleName");
            }

            if (phone != null && phone.equals(account.getPhone())) {
                matchInfo.addMatchedCriterion("phone");
            }

            if (email != null && email.equals(account.getEmail())) {
                matchInfo.addMatchedCriterion("email");
            }

            matchedAccounts.add(matchInfo);
        }

        if (matchedAccounts.size() > 1) {
            logger.warn("Найдено более одной учетной записи, соответствующей указанным критериям");
            throw new DuplicateAccountException("Найдено более одной учетной записи, соответствующей указанным критериям", matchedAccounts);
        }

        AccountMatchInfo matchedAccount = matchedAccounts.get(0);

        logger.info("Аккаунт найден: {}", matchedAccount.getAccount());
        logger.info("Совпадающие критерии: {}", matchedAccount.getMatchedCriteria());

        return accountMapper.toResponseDto(matchedAccount.getAccount());
    }
}
