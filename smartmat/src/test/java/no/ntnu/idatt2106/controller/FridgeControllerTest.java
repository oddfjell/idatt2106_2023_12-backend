package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.service.AccountService;
import no.ntnu.idatt2106.service.FridgeService;
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
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FridgeControllerTest {


  @Autowired
  TestRestTemplate restTemplate;

  @LocalServerPort
  int randomServerPort;

  @Autowired
  FridgeService fridgeService;

  @Autowired
  AccountService accountService;

  private AccountEntity account;
  private String jwt;


  @BeforeEach
  void setUp() throws URISyntaxException {
    account = new AccountEntity();
    account.setUsername("TestUserTwo");
    account.setPassword("TestPassword");

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

    jwt = Objects.requireNonNull(result.getBody()).toString().substring(result.getBody().toString().indexOf("\"jwt\"") + 7, result.getBody().toString().length() - 2);
  }

  @AfterEach
  void tearDown() {
    accountService.removeAccount(account.getUsername());
  }

  @Test
  void getGroceriesByAccount() throws URISyntaxException {

    String baseURL = "http://localhost:"+ randomServerPort +"/fridge/groceries";
    URI uri = new URI(baseURL);

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Authorization", "Bearer " + jwt);

    ResponseEntity<?> result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), List.class);

    Assertions.assertNotNull(result);
    Assertions.assertTrue(Objects.requireNonNull(result.getBody()).toString().contains("["));


  }

  @Test
  void addGroceryToAccount() {
  }
}