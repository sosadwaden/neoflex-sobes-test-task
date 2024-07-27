package com.sosadwaden.sobestesttask.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Ответ для получения аккаунта")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDtoGetResponse {

    UUID id;

    @Schema(description = "ID банка", example = "5")
    Long bankId;

    @Schema(description = "Фамилия", example = "Кузнецов")
    String lastName;

    @Schema(description = "Имя", example = "Петр")
    String firstName;

    @Schema(description = "Отчество", example = "Петрович")
    String middleName;

    @Schema(description = "Дата рождения", example = "1995-05-23")
    LocalDate birthdate;

    @Schema(description = "Номер паспорта", example = "1234 567890")
    String passportNumber;

    @Schema(description = "Место рождения", example = "Moscow")
    String placeOfBirth;

    @Schema(description = "Телефон", example = "79991234567")
    String phone;

    @Schema(description = "Email", example = "example@gmail.com")
    String email;

    @Schema(description = "Адрес регистрации", example = "123 Main Street, Moscow, 123456")
    String registrationAddress;

    @Schema(description = "Фактический адрес проживания", example = "123 Main Street, Moscow, 123456")
    String residentialAddress;
}
