package com.sosadwaden.sobestesttask.controller;

import com.sosadwaden.sobestesttask.api.request.AccountDtoPostRequest;
import com.sosadwaden.sobestesttask.api.response.AccountDtoGetResponse;
import com.sosadwaden.sobestesttask.api.response.AccountDtoPostResponse;
import com.sosadwaden.sobestesttask.api.response.MessageDtoResponse;
import com.sosadwaden.sobestesttask.api.response.ValidationExceptionResponse;
import com.sosadwaden.sobestesttask.exception.AccountNotFoundException;
import com.sosadwaden.sobestesttask.service.AccountService;
import com.sosadwaden.sobestesttask.utils.JsonExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Account Controller", description = "Контроллер для работы с учетными записями клиентов")
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Создать учётную запись",
            description = "Создание учётной записи из различных приложений"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Аккаунт успешно создан", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDtoPostResponse.class)
            )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации или неверное значение x-Source",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ValidationExceptionResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "ValidationException",
                                            value = JsonExamples.VALIDATION_EXCEPTION_EXAMPLE
                                    ),
                                    @ExampleObject(
                                            name = "InvalidSourceException",
                                            value = JsonExamples.INVALID_SOURCE_EXCEPTION_EXAMPLE
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "422", description = "Отсутствует заголовок x-Source", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageDtoResponse.class),
                    examples = @ExampleObject(name = "MissingHeaderError", value = "{\n" +
                                                                                   "  \"message\": \"Required request header 'x-Source' for method parameter type String is not present\"\n" +
                                                                                   "}")
            ))
    })
    @PostMapping
    public ResponseEntity<AccountDtoPostResponse> createAccount(
            @Parameter(
                    description = "Значение заголовка x-Source. Возможные значения: mail, bank, mobile, gosuslugi",
                    required = true,
                    example = "mail"
            )
            @RequestHeader("x-Source") String source,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для создания учётной записи",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AccountDtoPostRequest.class)
                    )
            ) AccountDtoPostRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /api/v1/account/: {}", request);

        AccountDtoPostResponse response = accountService.createAccount(source, request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от /api/v1/account/: {}, (время выполнения: {} ms)", response, duration);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Найти учётную запись",
            description = "Поиск учётной записи по переданному id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Учётная запись найдена", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDtoGetResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Учётная запись не найдена", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageDtoResponse.class)
            ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountDtoGetResponse> getAccountById(
            @Parameter(
                    description = "UUID пользователя",
                    required = true,
                    example = "10ca5818-96bb-44e0-a3cf-5d696d19f8b0"
            )
            @PathVariable UUID id) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /api/v1/account/{id}: {}", id);

        AccountDtoGetResponse response = accountService.getAccountById(id);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от /api/v1/account/{id}: {}, (время выполнения: {} ms)", response, duration);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Поиск учётной записи",
            description = "Поиск учётной записи по полям [фамилия, имя, отчество, телефон, емейл]. " +
                          "При этом необходимо передать http-заголовок \"x-Source\". " +
                          "Список значений \"x-Source\": [mail, mobile, bank, gosuslugi]"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Учётная запись найдена", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountDtoGetResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Неверный запрос, необходимо указать хотя бы один параметр поиска", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageDtoResponse.class),
                    examples = @ExampleObject(value = "{\n" +
                                                      "  \"message\": \"Должен быть указан хотя бы один параметр поиска\"\n" +
                                                      "}")
            )),
            @ApiResponse(responseCode = "404", description = "Учетная запись не найдена в соответствии с указанными критериями", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AccountNotFoundException.class),
                    examples = @ExampleObject(value = JsonExamples.ACCOUNT_NOT_FOUND_ACCORDING_SPECIFIED_CRITERIA)
            )),
            @ApiResponse(
                    responseCode = "409",
                    description = "Найдено два или более аккаунта по заданным критериям",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = JsonExamples.DUPLICATE_ACCOUNT_EXAMPLE)
                    )
            )
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchAccount(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String middleName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {

        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /api/v1/account/{id}");

        if (lastName == null && firstName == null && middleName == null && phone == null && email == null) {
            return ResponseEntity.badRequest().body(MessageDtoResponse.builder().message("Должен быть указан хотя бы один параметр поиска").build());
        }

        AccountDtoGetResponse response = accountService.searchAccount(lastName, firstName, middleName, phone, email);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от /api/v1/account/{id}: {}, (время выполнения: {} ms)", response, duration);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
