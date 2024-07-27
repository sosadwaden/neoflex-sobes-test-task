package com.sosadwaden.sobestesttask.service.impl;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.exception.ValidationException;
import com.sosadwaden.sobestesttask.service.ValidationService;
import com.sosadwaden.sobestesttask.validation.ValidationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final Map<String, ValidationStrategy> strategies;
    private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

    @Autowired
    public ValidationServiceImpl(List<ValidationStrategy> strategies) {
        this.strategies = strategies.stream().collect(Collectors.toMap(
                strategy -> strategy.getClass().getSimpleName().replace("ValidationStrategy", "").toLowerCase(),
                strategy -> strategy
        ));
        logger.info("Validation strategies инициализированы: {}", this.strategies.keySet());
    }

    @Override
    public void validate(String source, AccountDtoPostRequest accountDtoPostRequest) {
        logger.info("Начало валидации для источника: {} и данных: {}", source, accountDtoPostRequest);

        ValidationStrategy strategy = strategies.get(source.toLowerCase());

        if (strategy != null) {
            logger.debug("Используется стратегия валидации: {}", strategy.getClass().getSimpleName());
            strategy.validate(accountDtoPostRequest);
            logger.info("Валидация прошла успешно для источника: {}", source);
        } else {
            logger.error("Неподдерживаемый источник: {}", source);
            throw new ValidationException("Неподдерживаемый источник: " + source);
        }
    }
}
