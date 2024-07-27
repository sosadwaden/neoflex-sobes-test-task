package com.sosadwaden.sobestesttask.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Schema(description = "Ответ после создания Account")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDtoPostResponse {

    @Schema(description = "UUID пользователя", example = "16e0ba44-0109-45a6-b106-3122da7ecc36")
    UUID id;

    @Schema(description = "Сообщение", example = "Аккаунт успешно создан")
    String message;
}
