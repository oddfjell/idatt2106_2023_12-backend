package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.exceptions.GroceryAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;
import no.ntnu.idatt2106.model.api.FridgeGroceryThrowBody;
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

    // ADDING ACCOUNT
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

    // LOGIN ACCOUNT
    baseURL = "http://localhost:"+ randomServerPort +"/auth/account/loginAccount";
    uri = new URI(baseURL);
    headers = new HttpHeaders();
    request = new HttpEntity<>(account,headers);
    result = this.restTemplate.postForEntity(uri, request, String.class);
    String jwt = Objects.requireNonNull(result.getBody()).toString().substring(result.getBody().toString().indexOf("\"jwt\"") + 7, result.getBody().toString().length() - 2);

    // GETS LIST OF GROCERIES
    baseURL = "http://localhost:"+ randomServerPort +"/fridge/groceries";
    uri = new URI(baseURL);
    MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<>();
    headers2.add("Authorization", "Bearer " + jwt);
    result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers2), List.class);
    Assertions.assertEquals(200,result.getStatusCode().value());
    Assertions.assertTrue(result.getBody().toString().contains("["));

    // DELETES ACCOUNT
    accountService.removeAccount(account.getUsername());
  }

  @Test
  void addGroceryToAccount() throws URISyntaxException, GroceryAlreadyExistsException {

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
    grocery.setExpiryDate(4);

    groceryService.addGrocery(grocery);

    FridgeGroceryBody groceryToAccountBody = new FridgeGroceryBody();
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

  @Test
  void removeGroceryFromAccountByAmount() throws GroceryAlreadyExistsException, URISyntaxException {

    AccountEntity account = new AccountEntity();
    account.setUsername("TestUserFridgeFour");
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
    category.setName("TestCategory2");
    category.setImage(null);

    categoryService.addCategory(category);

    GroceryEntity grocery = new GroceryEntity();
    grocery.setName("TestGrocery2");
    grocery.setCategory(category);
    grocery.setExpiryDate(4);

    groceryService.addGrocery(grocery);

    FridgeGroceryBody groceryToAccountBody = new FridgeGroceryBody();
    groceryToAccountBody.setName("TestGrocery2");
    groceryToAccountBody.setCount(1);
    groceryToAccountBody.setCategoryId(category.getCategory_id());

    baseURL = "http://localhost:"+ randomServerPort +"/fridge/add";
    uri = new URI(baseURL);

    MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<>();
    headers2.add("Authorization", "Bearer " + jwt);

    result = restTemplate.postForEntity(uri,new HttpEntity<>(groceryToAccountBody,headers2), ResponseEntity.class);
    Assertions.assertEquals(200,result.getStatusCode().value());

    baseURL = "http://localhost:"+ randomServerPort +"/fridge/remove";
    uri = new URI(baseURL);
    groceryToAccountBody.setCount(1);

    result = restTemplate.postForEntity(uri,new HttpEntity<>(groceryToAccountBody,headers2), String.class);
    Assertions.assertEquals(200,result.getStatusCode().value());

    result = restTemplate.postForEntity(uri,new HttpEntity<>(groceryToAccountBody,headers2), String.class);
    Assertions.assertEquals(400,result.getStatusCode().value());
    Assertions.assertEquals("Something went wrong. May be invalid count number",result.getBody().toString());

    fridgeService.removeGroceryFromAccount(account,grocery);
    groceryService.removeGrocery(grocery);
    categoryService.removeCategory(category);
    accountService.removeAccount(account.getUsername());
  }

  @Test
  void throwGroceryFromAccountByAmountTest() throws URISyntaxException, GroceryAlreadyExistsException {
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
    grocery.setExpiryDate(4);

    groceryService.addGrocery(grocery);

    FridgeGroceryBody groceryToAccountBody = new FridgeGroceryBody();
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