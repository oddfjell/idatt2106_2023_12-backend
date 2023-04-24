/**
package no.ntnu.idatt2106.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FridgeEntityTest {

    ShoppingListEntity shoppingListItem;
    AccountEntity account;

    @BeforeEach
    void setUp() {

        account = new AccountEntity();
        account.setUsername("daniel");
        account.setPassword("yoyo123");


        shoppingListItem = new ShoppingListEntity();
        shoppingListItem.setAccount(account);
        shoppingListItem.setBrand("gruppe12");
        shoppingListItem.setEan("299792458");
        shoppingListItem.setDescription("LOL");
        shoppingListItem.setPrice(420);
        shoppingListItem.setImageUrl("https://rickroll.com");
        shoppingListItem.setUrl("https://yo.com");
        shoppingListItem.setFullName("never gonna give you up");

    }

    @Test
    void getEan() {
        assertEquals("299792458",shoppingListItem.getEan());
        assertNotEquals("123",shoppingListItem.getEan());
        assertNotNull(shoppingListItem.getEan());
    }

    @Test
    void setEan() {
        shoppingListItem.setEan("123456789");
        assertEquals("123456789",shoppingListItem.getEan());
        assertNotEquals("299792458",shoppingListItem.getEan());
        assertNotNull(shoppingListItem.getEan());
    }

    @Test
    void getFullName() {
        assertEquals("never gonna give you up",shoppingListItem.getFullName());
        assertNotEquals("never gonna let you down",shoppingListItem.getFullName());
        assertNotNull(shoppingListItem.getFullName());
    }

    @Test
    void setFullName() {
        shoppingListItem.setFullName("never gonna run around");
        assertEquals("never gonna run around",shoppingListItem.getFullName());
        assertNotEquals("and desert you",shoppingListItem.getFullName());
        assertNotNull(shoppingListItem.getFullName());
    }

    @Test
    void getBrand() {
        assertEquals("gruppe12",shoppingListItem.getBrand());
        assertNotEquals("gruppe2",shoppingListItem.getBrand());
        assertNotNull(shoppingListItem.getBrand());
    }

    @Test
    void setBrand() {
        shoppingListItem.setBrand("gruppe2");
        assertEquals("gruppe2",shoppingListItem.getBrand());
        assertNotEquals("gruppe12",shoppingListItem.getBrand());
        assertNotNull(shoppingListItem.getBrand());
    }

    @Test
    void getUrl() {
        assertEquals("https://yo.com",shoppingListItem.getUrl());
        assertNotEquals("https://youtube.com",shoppingListItem.getUrl());
        assertNotNull(shoppingListItem.getUrl());
    }

    @Test
    void setUrl() {
        shoppingListItem.setUrl("https://youtube.com");
        assertEquals("https://youtube.com",shoppingListItem.getUrl());
        assertNotEquals("https://yo.com",shoppingListItem.getUrl());
        assertNotNull(shoppingListItem.getUrl());
    }

    @Test
    void getImageUrl() {
        assertEquals("https://rickroll.com",shoppingListItem.getImageUrl());
        assertNotEquals("https://twitch.tv",shoppingListItem.getImageUrl());
        assertNotNull(shoppingListItem.getImageUrl());
    }

    @Test
    void setImageUrl() {
        shoppingListItem.setImageUrl("https://image.com");
        assertEquals("https://image.com",shoppingListItem.getImageUrl());
        assertNotEquals("https://twitch.tv",shoppingListItem.getImageUrl());
        assertNotNull(shoppingListItem.getImageUrl());
    }

    @Test
    void getDescription() {
        assertEquals("LOL",shoppingListItem.getDescription());
        assertNotEquals("WOW",shoppingListItem.getDescription());
        assertNotNull(shoppingListItem.getDescription());
    }

    @Test
    void setDescription() {
        shoppingListItem.setDescription("OK");
        assertEquals("OK",shoppingListItem.getDescription());
        assertNotEquals("WOW",shoppingListItem.getDescription());
        assertNotNull(shoppingListItem.getDescription());
    }

    @Test
    void getPrice() {
        assertEquals(420,shoppingListItem.getPrice());
        assertNotEquals(42,shoppingListItem.getPrice());
    }

    @Test
    void setPrice() {
        shoppingListItem.setPrice(42);
        assertEquals(42,shoppingListItem.getPrice());
        assertNotEquals(420,shoppingListItem.getPrice());
    }

    @Test
    void getAccount() {
        assertEquals("daniel",shoppingListItem.getAccount().getUsername());
        assertEquals("yoyo123",shoppingListItem.getAccount().getPassword());
        assertNotNull(shoppingListItem.getAccount());
    }
}
 */