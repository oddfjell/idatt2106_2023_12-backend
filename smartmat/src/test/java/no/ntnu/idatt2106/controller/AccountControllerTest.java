package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ProfileEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AccountService accountService;

    @Autowired
    JWTService jwtService;

    @LocalServerPort
    int randomServerPort;

    private AccountEntity account;

    @BeforeEach
    void setUp() {
        account = new AccountEntity();
        account.setUsername("TestUserOne");
        account.setPassword("TestPassword");
    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account.getUsername());
    }

    @Test
    void testRegisterUser() throws Exception {

        String baseURL = "http://localhost:"+ randomServerPort +"/auth/account/registerAccount";
        URI uri = new URI(baseURL);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<AccountEntity> request = new HttpEntity<>(account,headers);

        ResponseEntity<?> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("User added", result.getBody());

        result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(409, result.getStatusCode().value());

    }

    @Test
    void testLoginUser() throws Exception{

        String baseURL = "http://localhost:"+ randomServerPort +"/auth/account/registerAccount";
        URI uri = new URI(baseURL);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<AccountEntity> request = new HttpEntity<>(account,headers);

        ResponseEntity<?> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("User added", result.getBody());


        baseURL = "http://localhost:"+ randomServerPort +"/auth/account/loginAccount";
        uri = new URI(baseURL);

        headers = new HttpHeaders();

        request = new HttpEntity<>(account,headers);

        result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertTrue(Objects.requireNonNull(result.getBody()).toString().contains("jwt"));

    }

    @Test
    void testDeleteAccount() throws Exception {

        // ADDING ACCOUNT
        String baseURL = "http://localhost:"+ randomServerPort +"/auth/account/registerAccount";
        URI uri = new URI(baseURL);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AccountEntity> request = new HttpEntity<>(account,headers);
        ResponseEntity<?> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("User added", result.getBody());;

        // DELETING ACCOUNT
        baseURL = "http://localhost:"+ randomServerPort +"/auth/account/remove";
        uri = new URI(baseURL);
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtService.generateJWT(account));
        request = new HttpEntity<>(account,headers);
        result = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void getProfiles() throws URISyntaxException {
        String baseURL = "http://localhost:"+ randomServerPort +"/auth/account/registerAccount";
        URI uri = new URI(baseURL);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<AccountEntity> request = new HttpEntity<>(account,headers);

        ResponseEntity<?> result = this.restTemplate.postForEntity(uri, request, String.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertEquals("User added", result.getBody());

        baseURL = "http://localhost:"+ randomServerPort +"/auth/account/profiles";
        uri = new URI(baseURL);


        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtService.generateJWT(account));

        request = new HttpEntity<>(headers);

        result = this.restTemplate.exchange(uri, HttpMethod.GET, request, List.class);

        Assertions.assertEquals(200, result.getStatusCode().value());
    }
}