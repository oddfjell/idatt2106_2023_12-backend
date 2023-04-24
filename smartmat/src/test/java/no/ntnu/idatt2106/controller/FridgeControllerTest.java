package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.AddGroceryToAccountBody;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.service.AccountService;
import no.ntnu.idatt2106.service.CategoryService;
import no.ntnu.idatt2106.service.FridgeService;
import no.ntnu.idatt2106.service.GroceryService;
import org.junit.jupiter.api.*;
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
  GroceryService groceryService;
  @Autowired
  AccountService accountService;
  @Autowired
  CategoryService categoryService;



  @BeforeEach
  void setUp() throws URISyntaxException {

  }

  @AfterEach
  void tearDown() {

  }

  @Test
  void getGroceriesByAccount() throws URISyntaxException {

    AccountEntity account = new AccountEntity();
    account.setUsername("TestUserFridgeOne");
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

    String jwt = Objects.requireNonNull(result.getBody()).toString().substring(result.getBody().toString().indexOf("\"jwt\"") + 7, result.getBody().toString().length() - 2);



    baseURL = "http://localhost:"+ randomServerPort +"/fridge/groceries";
    uri = new URI(baseURL);

    MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<>();
    headers2.add("Authorization", "Bearer " + jwt);

    result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers2), List.class);

    accountService.removeAccount(account.getUsername());

    Assertions.assertEquals(200,result.getStatusCode().value());
    Assertions.assertTrue(result.getBody().toString().contains("["));


  }

  @Test
  void addGroceryToAccount() throws URISyntaxException {

    AccountEntity account = new AccountEntity();
    account.setUsername("TestUserFridgeTwo");
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

    String jwt = Objects.requireNonNull(result.getBody()).toString().substring(result.getBody().toString().indexOf("\"jwt\"") + 7, result.getBody().toString().length() - 2);




    CategoryEntity category = new CategoryEntity();
    category.setName("TestCategory");
    category.setImage(null);

    categoryService.addCategory(category);

    GroceryEntity grocery = new GroceryEntity();
    grocery.setName("TestGrocery");
    grocery.setCategory(category);

    groceryService.addGrocery(grocery);

    AddGroceryToAccountBody groceryToAccountBody = new AddGroceryToAccountBody();
    groceryToAccountBody.setName("TestGrocery");
    groceryToAccountBody.setCount(4);
    groceryToAccountBody.setCategoryId(category.getCategory_id());

    baseURL = "http://localhost:"+ randomServerPort +"/fridge/add";
    uri = new URI(baseURL);

    MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<>();
    headers2.add("Authorization", "Bearer " + jwt);

    result = restTemplate.postForEntity(uri,new HttpEntity<>(groceryToAccountBody,headers2), ResponseEntity.class);

    Assertions.assertEquals(200,result.getStatusCode().value());

    result = restTemplate.postForEntity(uri,new HttpEntity<>(groceryToAccountBody,headers2), String.class);

    Assertions.assertEquals(200,result.getStatusCode().value());
    Assertions.assertEquals("Updated grocery count",result.getBody().toString());

    fridgeService.removeGroceryFromAccount(account,grocery);
    groceryService.removeGrocery(grocery);
    categoryService.removeCategory(category);
    accountService.removeAccount(account.getUsername());

  }
}