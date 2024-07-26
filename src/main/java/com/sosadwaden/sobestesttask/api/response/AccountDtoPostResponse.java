package com.sosadwaden.sobestesttask.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO для представления информации об аккаунте. Ответ после создания Account")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDtoPostResponse {

    UUID id;

    Long bankId;

    String lastName;

    String firstName;

    String middleName;

    LocalDate birthdate;

    String passportNumber;

    String placeOfBirth;

    String phone;

    String email;

    String registrationAddress;

    String residentialAddress;
}
