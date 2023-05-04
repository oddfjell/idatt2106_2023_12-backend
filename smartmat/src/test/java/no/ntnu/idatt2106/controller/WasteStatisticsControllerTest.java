package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.SmartmatApplication;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {SmartmatApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WasteStatisticsControllerTest {


    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        
    }

    @Test
    void getTotalWaste() {

    }
}
