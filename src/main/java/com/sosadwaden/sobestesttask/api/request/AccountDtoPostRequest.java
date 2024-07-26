package com.sosadwaden.sobestesttask.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Schema(description = "DTO для представления информации об аккаунте для его создания")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDtoPostRequest {

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
