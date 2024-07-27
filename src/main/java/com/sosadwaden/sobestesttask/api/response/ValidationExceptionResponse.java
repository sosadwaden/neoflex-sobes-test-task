package com.sosadwaden.sobestesttask.api.response;

import com.sosadwaden.sobestesttask.exception.ValidationError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Schema(description = "Ответ при ошибке валидации")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationExceptionResponse {

    @Schema(description = "Источник ошибки", example = "Пропущены обязательные поля при создании аккаунта при помощи почты")
    String source;

    @Schema(description = "Список ошибок валидации")
    List<ValidationError> errors;
}
