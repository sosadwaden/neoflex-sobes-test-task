# Тестовое задание 
Тестовое задание для собеседования в Neoflex, 2024
Необходимо разработать веб-сервис для работы с учетными записями клиентов со следующей функциональностью:

### Функциональность

1. **Создание учетной записи**
2. **Чтение учетной записи по id**
3. **Поиск учетной записи по полям**:
    - Фамилия
    - Имя
    - Отчество
    - Телефон
    - Емейл

   Поиск осуществляется только при указании хотя бы одного поля.

### Поля учетной записи клиента

- **id**
- **bank_id** (идентификатор клиента в банке)
- **фамилия**
- **имя**
- **отчество**
- **дата рождения**
- **номер паспорта** (вместе с серией в формате ХХХХ ХХХХХХ)
- **место рождения**
- **телефон** (в формате 7ХХХХХХХХХХ)
- **емейл**
- **адрес регистрации**
- **адрес проживания**

### Правила валидации

Клиент может быть создан из разных приложений. В зависимости от приложения отличается логика валидации полей при создании учетной записи. Приложение определяется по обязательному для указания HTTP-заголовку `x-Source`.

### Список значений HTTP-заголовка и правила валидации полей:

- **mail**: только имя и емейл обязательные
- **mobile**: только номер телефона обязательный
- **bank**: bank_id, фамилия, имя, отчество, дата рождения, номер паспорта обязательные
- **gosuslugi**: все поля кроме емейла и адреса проживания обязательные

### Дополнительные требования

- Код должен быть приспособлен для появления новых приложений со своими правилами валидации.
- Основная бизнес-логика должна быть покрыта тестами.
- Стек технологий: Java 11+, Spring Boot, Spring Data JPA, PostgreSQL, Maven
- Должен быть `docker-compose` для подготовки окружения для локального запуска сервиса.

Выполненное задание выложить на GitHub или GitLab.

## Используемые технологии
+ **Java 17** <br/>
+ **Maven** <br/>
+ **Spring Boot** <br/>
+ **Spring MVC** <br/>
+ **Spring Data Jpa** <br/>
+ **PostgreSQL** </br>
+ **jUnit** <br/>
+ **MockMvc** <br/>
+ **Swagger** <br/>
+ **Docker**
## API
Создать аккаунт: </br>
![sobesCreateAccount.png](src%2Fmain%2Fresources%2Fimages%2FsobesCreateAccount.png)
Получить аккаунт по id: </br>
![sobesGetAccountById.png](src%2Fmain%2Fresources%2Fimages%2FsobesGetAccountById.png)
Получить аккаунт по критериям: </br>
![sobesSearch.png](src%2Fmain%2Fresources%2Fimages%2FsobesSearch.png)
Документация доступна по адресу http://localhost:1111/swagger-ui/index.html </br>
![sobesSwagger.png](src%2Fmain%2Fresources%2Fimages%2FsobesSwagger.png)
