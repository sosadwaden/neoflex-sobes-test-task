package com.sosadwaden.sobestesttask.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Schema(description = "Ответ в виде сообщения")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDtoResponse {
    @Schema(description = "Сообщение", example = "Аккаунт с UUID: 7d033175-aa3a-4da6-9f50-a08ffa2f16b7 не найден")
    String message;
}
