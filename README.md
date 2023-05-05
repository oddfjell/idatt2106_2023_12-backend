# SmartMat Backend

This serverside application provides stable and secure endpoints for a food reduction based application. The backend controls basic functionality such as account management in addition to management of food waste statistics, your family fridge and planning a weekly menu.

## Table of Contents

- [SmartMat Backend](#project-title)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Usage](#usage)
    - [Configuration](#configuration)
    - [Endpoints](#endpoints)
  - [License](#license)

## Installation
Make sure you have both maven and git installed on your local computer. After cloning the project, you should be able to run the command below to ensure everything is ready

```bash
mvn clean
```

## Usage

To start the backend application run:
```bash
mvn spring-boot:run
```

This boots up spring-boot and connects the backend application to a remote database where the repositories are created automatically.

### Configuration

The configuration of the application can be found in the 
`src/main/resources/application.properties` file

```properties
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

encryption.salt.rounds=5

jwt.algorithm.key=VerySecureAlgorithmKey
jwt.issuer=YOUR_ISSUER
jwt.expiryInSeconds=259200
```
The configuration may be configured to your own database. Replace the variables respectivley with your own information. The bottom half of the configuration is for configuring the encryption algorithm of the passwords stored in the database. Here you are able to control the number of salt rounds and when the token should expire.

### Endpoints

The endpoints can be easily accessed with HTTP requests such as GET or POST. Here are some examples of endpoints that can be accessed

| METHOD | ENDPOINT                             | DESCRIPTION                                        |
|--------|--------------------------------------|----------------------------------------------------|
| POST   | /auth/account/loginAccount           | Endpoint for logging into an account               |
| GET    | /recipes/weekMenu/{servings}/{nDays} | Retrieve a automatically generated list of recipes |
| PUT    | /shoppingList/save                   | Update account shopping list information           |



## License

This project is licensed under the [MIT License](LICENSE).



