package com.sosadwaden.sobestesttask.service.impl;

import com.sosadwaden.sobestesttask.entity.Account;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountMatchInfo {

    Account account;
    List<String> matchedCriteria;

    public AccountMatchInfo(Account account) {
        this.account = account;
        this.matchedCriteria = new ArrayList<>();
    }

    public void addMatchedCriterion(String criterion) {
        matchedCriteria.add(criterion);
    }

}
