# Some simple money transfer service API

Simple project task , provides RESTful API for money transfer operations

### Technologies
- Gradle
- Spark Framework
- Dagger 2 (CDI)
- jOOQ
- H2 database
- Log4j
- JUnit 4
- Retrofit 2 (API Client)

### Howto 
run application
```sh
gradle start
```

run test suit (will up service instance separately)
```sh
gradle test
```

### Available APIs

#### User API

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /users/{id} | get user by id | 
| GET | /users/byEmail/{email} | get user by email | 
| GET | /users | get all users | 
| POST | /users | create a new user | 
| PUT | /users/{id} | update user | 
| DELETE | /users/{id} | delete user by id | 

#### Account API (see com.yetanotherbank.api.client.AccountServiceApi)

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /accounts/{id} | get account by id | 
| GET | /accounts | get all accounts | 
| POST | /accounts | create a new account | 
| PUT | /accounts/{id} | update account | 
| DELETE | /accounts/{id} | delete account by id | 

#### Currency API (see com.yetanotherbank.api.client.CurrencyServiceApi)

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /currencies | get all supported currencies | 
| GET | /currency/{code} | get currency by code | 
| POST | /currency-course/retrieve | get currency course | 
| POST | /currency-course | update currency course | 
| DELETE | /currency-course/delete | delete currency course | 

#### Transactions API (see com.yetanotherbank.api.client.TransactionServiceApi)

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| POST | /transaction/deposit | deposit money to user account | 
| POST | /transaction/withdraw | withdraw money from user account | 
| POST | /transaction/transfer | transfer money from one user account to another with course transformation | 

