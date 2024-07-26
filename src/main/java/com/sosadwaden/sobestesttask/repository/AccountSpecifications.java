package com.sosadwaden.sobestesttask.repository;

import com.sosadwaden.sobestesttask.Entity.Account;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountSpecifications {

    public Specification<Account> getAccountsByCriteria(String lastName,
                                                        String firstName,
                                                        String middleName,
                                                        String phone,
                                                        String email) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(lastName)) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"), lastName));
            }
            if (StringUtils.hasText(firstName)) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"), firstName));
            }
            if (StringUtils.hasText(middleName)) {
                predicates.add(criteriaBuilder.equal(root.get("middleName"), middleName));
            }
            if (StringUtils.hasText(phone)) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), phone));
            }
            if (StringUtils.hasText(email)) {
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
