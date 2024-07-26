package com.sosadwaden.sobestesttask.service.impl;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.service.ValidationService;
import com.sosadwaden.sobestesttask.validation.ValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Map<String, ValidationStrategy> strategies;

    @Autowired
    public ValidationServiceImpl(List<ValidationStrategy> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(
                strategy -> strategy.getClass().getSimpleName().replace("ValidationStrategy", "").toLowerCase(),
                strategy -> strategy
        ));
    }

    @Override
    public void validate(String source, AccountDtoPostRequest accountDtoPostRequest) {

        ValidationStrategy strategy = strategies.get(source.toLowerCase());

        if (strategy != null) {
            strategy.validate(accountDtoPostRequest);
        } else {
            throw new ValidationException("Неподдерживаемый источник: " + source);
        }
    }
}
