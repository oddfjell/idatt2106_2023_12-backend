package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.service.AccountService;
import no.ntnu.idatt2106.service.JWTService;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingListControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AccountService accountService;

    @Autowired
    JWTService jwtService;

    @LocalServerPort
    int randomServerPort;

    private AccountEntity account;
    private HttpEntity<AccountEntity> request;
    private HttpHeaders headers;

    @BeforeEach
    void setUp() throws URISyntaxException {
        account = new AccountEntity();
        account.setUsername("TestUserOne");
        account.setPassword("TestPassword");
        String baseURL = "http://localhost:"+ randomServerPort +"/auth/account/registerAccount";
        URI uri = new URI(baseURL);
        headers = new HttpHeaders();
        request = new HttpEntity<>(account,headers);
        this.restTemplate.postForEntity(uri, request, String.class);

        this.headers = new HttpHeaders();
        this.headers.add("Authorization", "Bearer " + jwtService.generateJWT(account));

        this.request = new HttpEntity<>(account, headers);

    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account.getUsername());
    }

    @Test
    void getShoppingList() throws URISyntaxException {
        String baseURL = "http://localhost:"+ randomServerPort +"/shoppingList/";
        URI uri = new URI(baseURL);
        ResponseEntity<?> result = this.restTemplate.exchange(uri, HttpMethod.GET, request, List.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void buyShoppingList() throws URISyntaxException {
        String baseURL = "http://localhost:"+ randomServerPort +"/shoppingList/buy";
        URI uri = new URI(baseURL);
        ResponseEntity<?> result = this.restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }
}
