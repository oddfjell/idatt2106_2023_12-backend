package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;


@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AccountService accountService;

    @LocalServerPort
    int randomServerPort;

    @BeforeEach
    void setUp() {
        System.out.println("YO");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testRegisterUser() throws Exception {

        String baseURL = "http://localhost:"+ randomServerPort +"/auth/account/registerUser";
        URI uri = new URI(baseURL);

        AccountEntity account = new AccountEntity();
        account.setUsername("TestUserOne");
        account.setPassword("TestPassword");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<AccountEntity> request = new HttpEntity<>(account,headers);

        ResponseEntity<?> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
    }


}