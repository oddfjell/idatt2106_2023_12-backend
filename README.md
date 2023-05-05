# SmartMat Backend

This serverside application provides stable and secure endpoints for a food reduction based application. The backend controls basic functionality such as account management in addition to management of food waste statistics, your family fridge and planning a weekly menu.

## Table of Contents

- [SmartMat Backend](#project-title)
  - [Table of Contents](#table-of-contents)
  - [Installation](#installation)
  - [Usage](#usage)
    - [Endpoints](#endpoints)
    - [Test](#test)
  - [License](#license)

## Installation
Make sure you have both maven and git installed on your local computer before running the commands below

```bash
git clone https://gitlab.stud.idi.ntnu.no/idatt2106-v23-12/idatt2106_2023_12-backend.git
cd idatt2106_2023_12-backend/smartmat
mvn clean
```

## Usage

To start the backend application run (make sure to run from inside the `/smartmat` directory):
```bash
cd idatt2106_2023_12-backend/smartmat
mvn spring-boot:run
```

This boots up spring-boot and connects the backend application to a remote database where the repositories are created automatically. The applications is already configured to connect to a remote NTNU database

### Test
Run the command below to run tests
```bash
cd idatt2106_2023_12-backend/smartmat
mvn test
```


### Endpoints

The endpoints can be easily accessed with HTTP requests such as GET or POST. Here are some examples of endpoints that can be accessed

| METHOD | ENDPOINT                             | DESCRIPTION                                        |
|--------|--------------------------------------|----------------------------------------------------|
| POST   | /auth/account/loginAccount           | Endpoint for logging into an account               |
| GET    | /recipes/weekMenu/{servings}/{nDays} | Retrieve a automatically generated list of recipes |
| PUT    | /shoppingList/save                   | Update account shopping list information           |



## License

This project is licensed under the [MIT License](LICENSE).



