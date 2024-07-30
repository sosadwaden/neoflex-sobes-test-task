package com.sosadwaden.sobestesttask.utils;

public class JsonExamples {

    public static final String VALIDATION_EXCEPTION_EXAMPLE = "{\n" +
                                                              "  \"source\": \"Пропущены обязательные поля при создании аккаунта при помощи банка\",\n" +
                                                              "  \"errors\": [\n" +
                                                              "    {\n" +
                                                              "      \"field\": \"birthdate\",\n" +
                                                              "      \"message\": \"Birth date является обязательным\"\n" +
                                                              "    },\n" +
                                                              "    {\n" +
                                                              "      \"field\": \"passportNumber\",\n" +
                                                              "      \"message\": \"Passport number является обязательным\"\n" +
                                                              "    }\n" +
                                                              "  ]\n" +
                                                              "}";

    public static final String INVALID_SOURCE_ERROR_EXAMPLE = "{\n" +
                                                              "  \"source\": \"Неподдерживаемый источник: source_name\",\n" +
                                                              "  \"errors\": []\n" +
                                                              "}";

    public static final String ACCOUNT_NOT_FOUND_ACCORDING_SPECIFIED_CRITERIA = "{\n" +
                                      "  \"source\": null,\n" +
                                      "  \"errors\": [\n" +
                                      "    {\n" +
                                      "      \"field\": \"searchCriteria\",\n" +
                                      "      \"message\": \"Учетная запись не найдена в соответствии с указанными критериями\"\n" +
                                      "    }\n" +
                                      "  ]\n" +
                                      "}";
    public static final String DUPLICATE_ACCOUNT_EXAMPLE = "{\n" +
                                                           "  \"message\": \"Найдено более одной учетной записи, соответствующей указанным критериям\",\n" +
                                                           "  \"duplicates\": [\n" +
                                                           "    {\n" +
                                                           "      \"account\": {\n" +
                                                           "        \"id\": \"166b63bc-8fc1-4c20-816e-973a1c21cd20\",\n" +
                                                           "        \"bankId\": 5,\n" +
                                                           "        \"lastName\": \"Кузнецов\",\n" +
                                                           "        \"firstName\": \"Петр\",\n" +
                                                           "        \"middleName\": \"Петрович\",\n" +
                                                           "        \"birthdate\": \"1995-05-23\",\n" +
                                                           "        \"passportNumber\": \"1234 567890\",\n" +
                                                           "        \"placeOfBirth\": \"Moscow\",\n" +
                                                           "        \"phone\": \"79991234567\",\n" +
                                                           "        \"email\": \"example@gmail.com\",\n" +
                                                           "        \"registrationAddress\": \"123 Main Street, Moscow, 123456\",\n" +
                                                           "        \"residentialAddress\": \"123 Main Street, Moscow, 123456\"\n" +
                                                           "      },\n" +
                                                           "      \"matchedCriteria\": [\n" +
                                                           "        \"lastName\",\n" +
                                                           "        \"firstName\"\n" +
                                                           "      ]\n" +
                                                           "    },\n" +
                                                           "    {\n" +
                                                           "      \"account\": {\n" +
                                                           "        \"id\": \"5ad3decf-d17a-46c7-a8cb-9a806c7553b5\",\n" +
                                                           "        \"bankId\": 5,\n" +
                                                           "        \"lastName\": \"Кузнецов\",\n" +
                                                           "        \"firstName\": \"Петр\",\n" +
                                                           "        \"middleName\": \"Петрович\",\n" +
                                                           "        \"birthdate\": \"1995-05-23\",\n" +
                                                           "        \"passportNumber\": \"1234 567890\",\n" +
                                                           "        \"placeOfBirth\": \"Moscow\",\n" +
                                                           "        \"phone\": \"79991234567\",\n" +
                                                           "        \"email\": \"example@gmail.com\",\n" +
                                                           "        \"registrationAddress\": \"123 Main Street, Moscow, 123456\",\n" +
                                                           "        \"residentialAddress\": \"123 Main Street, Moscow, 123456\"\n" +
                                                           "      },\n" +
                                                           "      \"matchedCriteria\": [\n" +
                                                           "        \"lastName\",\n" +
                                                           "        \"firstName\"\n" +
                                                           "      ]\n" +
                                                           "    }\n" +
                                                           "  ]\n" +
                                                           "}";
}

