package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import no.ntnu.idatt2106.service.GroceryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroceryControllerTest {


    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    GroceryService groceryService;

    @LocalServerPort
    int randomServerPort;

    @Test
    void getAllProducts() throws URISyntaxException {
        String baseURL = "http://localhost:"+ randomServerPort +"/grocery/";
        URI uri = new URI(baseURL);

        ResponseEntity<?> result = restTemplate.getForEntity(uri, List.class);

        Assertions.assertEquals(200,result.getStatusCode().value());
    }



}