package com.sosadwaden.sobestesttask.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Schema(description = "Описание ошибки валидации")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationError {

    @Schema(description = "Поле, вызвавшее ошибку", example = "birthdate")
    String field;

    @Schema(description = "Сообщение об ошибке", example = "Birth date является обязательным")
    String message;
}
