/**
package no.ntnu.idatt2106.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListEntityTest {

    ShoppingListEntity shoppingItem;
    AccountEntity account;
    
    @BeforeEach
    void setUp() {


        account = new AccountEntity();
        account.setUsername("daniel");
        account.setPassword("yoyo123");
        
        shoppingItem = new ShoppingListEntity();
        shoppingItem.setAccount(account);
        shoppingItem.setBrand("gruppe12");
        shoppingItem.setEan("299792458");
        shoppingItem.setDescription("LOL");
        shoppingItem.setPrice(420);
        shoppingItem.setImageUrl("https://rickroll.com");
        shoppingItem.setUrl("https://yo.com");
        shoppingItem.setFullName("never gonna give you up");
        
    }

    @Test
    void getEan() {
        assertEquals("299792458",shoppingItem.getEan());
        assertNotEquals("123",shoppingItem.getEan());
        assertNotNull(shoppingItem.getEan());
    }

    @Test
    void setEan() {
        shoppingItem.setEan("123456789");
        assertEquals("123456789",shoppingItem.getEan());
        assertNotEquals("299792458",shoppingItem.getEan());
        assertNotNull(shoppingItem.getEan());
    }

    @Test
    void getFullName() {
        assertEquals("never gonna give you up",shoppingItem.getFullName());
        assertNotEquals("never gonna let you down",shoppingItem.getFullName());
        assertNotNull(shoppingItem.getFullName());
    }

    @Test
    void setFullName() {
        shoppingItem.setFullName("never gonna run around");
        assertEquals("never gonna run around",shoppingItem.getFullName());
        assertNotEquals("and desert you",shoppingItem.getFullName());
        assertNotNull(shoppingItem.getFullName());
    }

    @Test
    void getBrand() {
        assertEquals("gruppe12",shoppingItem.getBrand());
        assertNotEquals("gruppe2",shoppingItem.getBrand());
        assertNotNull(shoppingItem.getBrand());
    }

    @Test
    void setBrand() {
        shoppingItem.setBrand("gruppe2");
        assertEquals("gruppe2",shoppingItem.getBrand());
        assertNotEquals("gruppe12",shoppingItem.getBrand());
        assertNotNull(shoppingItem.getBrand());
    }

    @Test
    void getUrl() {
        assertEquals("https://yo.com",shoppingItem.getUrl());
        assertNotEquals("https://youtube.com",shoppingItem.getUrl());
        assertNotNull(shoppingItem.getUrl());
    }

    @Test
    void setUrl() {
        shoppingItem.setUrl("https://youtube.com");
        assertEquals("https://youtube.com",shoppingItem.getUrl());
        assertNotEquals("https://yo.com",shoppingItem.getUrl());
        assertNotNull(shoppingItem.getUrl());
    }

    @Test
    void getImageUrl() {
        assertEquals("https://rickroll.com",shoppingItem.getImageUrl());
        assertNotEquals("https://twitch.tv",shoppingItem.getImageUrl());
        assertNotNull(shoppingItem.getImageUrl());
    }

    @Test
    void setImageUrl() {
        shoppingItem.setImageUrl("https://image.com");
        assertEquals("https://image.com",shoppingItem.getImageUrl());
        assertNotEquals("https://twitch.tv",shoppingItem.getImageUrl());
        assertNotNull(shoppingItem.getImageUrl());
    }

    @Test
    void getDescription() {
        assertEquals("LOL",shoppingItem.getDescription());
        assertNotEquals("WOW",shoppingItem.getDescription());
        assertNotNull(shoppingItem.getDescription());
    }

    @Test
    void setDescription() {
        shoppingItem.setDescription("OK");
        assertEquals("OK",shoppingItem.getDescription());
        assertNotEquals("WOW",shoppingItem.getDescription());
        assertNotNull(shoppingItem.getDescription());
    }

    @Test
    void getPrice() {
        assertEquals(420,shoppingItem.getPrice());
        assertNotEquals(42,shoppingItem.getPrice());
    }

    @Test
    void setPrice() {
        shoppingItem.setPrice(42);
        assertEquals(42,shoppingItem.getPrice());
        assertNotEquals(420,shoppingItem.getPrice());
    }

    @Test
    void getAccount() {
        assertEquals("daniel",shoppingItem.getAccount().getUsername());
        assertEquals("yoyo123",shoppingItem.getAccount().getPassword());
        assertNotNull(shoppingItem.getAccount());
    }
    
}
 */