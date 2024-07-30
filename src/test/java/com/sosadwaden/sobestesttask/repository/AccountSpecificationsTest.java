package com.sosadwaden.sobestesttask.repository;

import com.sosadwaden.sobestesttask.entity.Account;
import com.sosadwaden.sobestesttask.exception.DuplicateAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountSpecificationsTest {

    private AccountSpecifications accountSpecifications;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<?> criteriaQuery;
    private Root<Account> root;

    @BeforeEach
    public void setUp() {
        accountSpecifications = new AccountSpecifications();
        criteriaBuilder = mock(CriteriaBuilder.class);
        criteriaQuery = mock(CriteriaQuery.class);
        root = mock(Root.class);
    }

    @Test
    public void testGetAccountsByCriteria_allFieldsPresent() {
        String lastName = "Кузнецов";
        String firstName = "Иван";
        String middleName = "Иванов";
        String phone = "1234567890";
        String email = "kzn.ivan@example.com";

        Predicate lastNamePredicate = mock(Predicate.class);
        Predicate firstNamePredicate = mock(Predicate.class);
        Predicate middleNamePredicate = mock(Predicate.class);
        Predicate phonePredicate = mock(Predicate.class);
        Predicate emailPredicate = mock(Predicate.class);

        when(criteriaBuilder.equal(root.get("lastName"), lastName)).thenReturn(lastNamePredicate);
        when(criteriaBuilder.equal(root.get("firstName"), firstName)).thenReturn(firstNamePredicate);
        when(criteriaBuilder.equal(root.get("middleName"), middleName)).thenReturn(middleNamePredicate);
        when(criteriaBuilder.equal(root.get("phone"), phone)).thenReturn(phonePredicate);
        when(criteriaBuilder.equal(root.get("email"), email)).thenReturn(emailPredicate);
        when(criteriaBuilder.and(lastNamePredicate, firstNamePredicate, middleNamePredicate, phonePredicate, emailPredicate))
                .thenReturn(mock(Predicate.class));

        var spec = accountSpecifications.getAccountsByCriteria(lastName, firstName, middleName, phone, email);
        Predicate result = spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        verify(criteriaBuilder).and(lastNamePredicate, firstNamePredicate, middleNamePredicate, phonePredicate, emailPredicate);
    }

    @Test
    public void testGetAccountsByCriteria_someFieldsMissing() {
        String lastName = "Кузнецов";
        String firstName = null;
        String middleName = "Иванович";
        String phone = null;
        String email = "kzn.ivan@example.com";

        Predicate lastNamePredicate = mock(Predicate.class);
        Predicate middleNamePredicate = mock(Predicate.class);
        Predicate emailPredicate = mock(Predicate.class);

        when(criteriaBuilder.equal(root.get("lastName"), lastName)).thenReturn(lastNamePredicate);
        when(criteriaBuilder.equal(root.get("middleName"), middleName)).thenReturn(middleNamePredicate);
        when(criteriaBuilder.equal(root.get("email"), email)).thenReturn(emailPredicate);
        when(criteriaBuilder.and(lastNamePredicate, middleNamePredicate, emailPredicate))
                .thenReturn(mock(Predicate.class));

        var spec = accountSpecifications.getAccountsByCriteria(lastName, firstName, middleName, phone, email);
        Predicate result = spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        verify(criteriaBuilder).and(lastNamePredicate, middleNamePredicate, emailPredicate);
    }

    @Test
    public void testGetAccountsByCriteria_noFields() {
        var spec = accountSpecifications.getAccountsByCriteria(null, null, null, null, null);
        Predicate result = spec.toPredicate(root, criteriaQuery, criteriaBuilder);

        verify(criteriaBuilder).and(new Predicate[0]);
    }
}
