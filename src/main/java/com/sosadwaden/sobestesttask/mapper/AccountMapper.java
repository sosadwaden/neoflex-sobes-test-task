package com.sosadwaden.sobestesttask.mapper;

import com.sosadwaden.sobestesttask.Entity.Account;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    Account toEntity(AccountDtoPostRequest dto);

    AccountDtoPostRequest toDto(Account account);

    AccountDtoPostResponse toResponseDto(Account account);
}
