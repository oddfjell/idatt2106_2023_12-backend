package no.ntnu.idatt2106.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FridgeEntityTest {

    FridgeEntity fridgeItem;
    AccountEntity account;

    @BeforeEach
    void setUp() {

        account = new AccountEntity();
        account.setUsername("daniel");
        account.setPassword("yoyo123");


        fridgeItem = new FridgeEntity();
        fridgeItem.setAccount(account);
        fridgeItem.setBrand("gruppe12");
        fridgeItem.setEan("299792458");
        fridgeItem.setDescription("LOL");
        fridgeItem.setPrice(420);
        fridgeItem.setImageUrl("https://rickroll.com");
        fridgeItem.setUrl("https://yo.com");
        fridgeItem.setFullName("never gonna give you up");

    }

    @Test
    void getEan() {
        assertEquals("299792458",fridgeItem.getEan());
        assertNotEquals("123",fridgeItem.getEan());
        assertNotNull(fridgeItem.getEan());
    }

    @Test
    void setEan() {
        fridgeItem.setEan("123456789");
        assertEquals("123456789",fridgeItem.getEan());
        assertNotEquals("299792458",fridgeItem.getEan());
        assertNotNull(fridgeItem.getEan());
    }

    @Test
    void getFullName() {
        assertEquals("never gonna give you up",fridgeItem.getFullName());
        assertNotEquals("never gonna let you down",fridgeItem.getFullName());
        assertNotNull(fridgeItem.getFullName());
    }

    @Test
    void setFullName() {
        fridgeItem.setFullName("never gonna run around");
        assertEquals("never gonna run around",fridgeItem.getFullName());
        assertNotEquals("and desert you",fridgeItem.getFullName());
        assertNotNull(fridgeItem.getFullName());
    }

    @Test
    void getBrand() {
        assertEquals("gruppe12",fridgeItem.getBrand());
        assertNotEquals("gruppe2",fridgeItem.getBrand());
        assertNotNull(fridgeItem.getBrand());
    }

    @Test
    void setBrand() {
        fridgeItem.setBrand("gruppe2");
        assertEquals("gruppe2",fridgeItem.getBrand());
        assertNotEquals("gruppe12",fridgeItem.getBrand());
        assertNotNull(fridgeItem.getBrand());
    }

    @Test
    void getUrl() {
        assertEquals("https://yo.com",fridgeItem.getUrl());
        assertNotEquals("https://youtube.com",fridgeItem.getUrl());
        assertNotNull(fridgeItem.getUrl());
    }

    @Test
    void setUrl() {
        fridgeItem.setUrl("https://youtube.com");
        assertEquals("https://youtube.com",fridgeItem.getUrl());
        assertNotEquals("https://yo.com",fridgeItem.getUrl());
        assertNotNull(fridgeItem.getUrl());
    }

    @Test
    void getImageUrl() {
        assertEquals("https://rickroll.com",fridgeItem.getImageUrl());
        assertNotEquals("https://twitch.tv",fridgeItem.getImageUrl());
        assertNotNull(fridgeItem.getImageUrl());
    }

    @Test
    void setImageUrl() {
        fridgeItem.setImageUrl("https://image.com");
        assertEquals("https://image.com",fridgeItem.getImageUrl());
        assertNotEquals("https://twitch.tv",fridgeItem.getImageUrl());
        assertNotNull(fridgeItem.getImageUrl());
    }

    @Test
    void getDescription() {
        assertEquals("LOL",fridgeItem.getDescription());
        assertNotEquals("WOW",fridgeItem.getDescription());
        assertNotNull(fridgeItem.getDescription());
    }

    @Test
    void setDescription() {
        fridgeItem.setDescription("OK");
        assertEquals("OK",fridgeItem.getDescription());
        assertNotEquals("WOW",fridgeItem.getDescription());
        assertNotNull(fridgeItem.getDescription());
    }

    @Test
    void getPrice() {
        assertEquals(420,fridgeItem.getPrice());
        assertNotEquals(42,fridgeItem.getPrice());
    }

    @Test
    void setPrice() {
        fridgeItem.setPrice(42);
        assertEquals(42,fridgeItem.getPrice());
        assertNotEquals(420,fridgeItem.getPrice());
    }

    @Test
    void getAccount() {
        assertEquals("daniel",fridgeItem.getAccount().getUsername());
        assertEquals("yoyo123",fridgeItem.getAccount().getPassword());
        assertNotNull(fridgeItem.getAccount());
    }
}