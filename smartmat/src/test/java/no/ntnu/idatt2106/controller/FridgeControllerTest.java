package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.service.FridgeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FridgeControllerTest {


  @Autowired
  TestRestTemplate restTemplate;

  @LocalServerPort
  int randomServerPort;

  @Autowired
  FridgeService fridgeService;


  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getGroceriesByAccount() {
  }

  @Test
  void addGroceryToAccount() {
  }
}