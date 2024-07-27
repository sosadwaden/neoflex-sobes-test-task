package com.sosadwaden.sobestesttask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoGetResponse;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import com.sosadwaden.sobestesttask.exception.AccountNotFoundException;
import com.sosadwaden.sobestesttask.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount_Success() throws Exception {
        AccountDtoPostRequest request = AccountDtoPostRequest.builder()
                .bankId(5L)
                .lastName("Кузнецов")
                .firstName("Иван")
                .middleName("Иванович")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportNumber("AB123456")
                .placeOfBirth("City")
                .phone("123-456-7890")
                .registrationAddress("123 Street")
                .build();

        UUID id = UUID.randomUUID();
        AccountDtoPostResponse response = AccountDtoPostResponse.builder()
                .id(id)
                .message("Аккаунт успешно создан")
                .build();

        when(accountService.createAccount("mail", request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account")
                        .header("x-Source", "mail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateAccount_MissingHeader() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"someField\":\"value\"}"))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void testGetAccountById_Success() throws Exception {
        UUID id = UUID.randomUUID();
        AccountDtoGetResponse response = new AccountDtoGetResponse();
        response.setLastName("Иван");

        when(accountService.getAccountById(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAccountById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(accountService.getAccountById(id)).thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testSearchAccount_Success() throws Exception {
        AccountDtoGetResponse response = AccountDtoGetResponse.builder()
                .lastName("Кузнецов")
                .firstName("Иван")
                .build();

        when(accountService.searchAccount("Кузнецов", "Иван", null, null, null))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/search")
                        .param("lastName", "Кузнецов")
                        .param("firstName", "Иван")
                        .header("x-Source", "mail"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testSearchAccount_BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/search")
                        .header("x-Source", "mail"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Должен быть указан хотя бы один параметр поиска"));
    }

    @Test
    public void searchAccount_NotFound() throws Exception {
        when(accountService.searchAccount("Кузнецов", "Иван", null, null, null))
                .thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/search")
                        .param("lastName", "Кузнецов")
                        .param("firstName", "Иван")
                        .header("x-Source", "mail"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
