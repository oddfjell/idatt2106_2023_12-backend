package no.ntnu.idatt2106.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WasteEntityTest {

    WasteEntity waste;
    AccountEntity account;

    @BeforeEach
    void setUp() {

        account = new AccountEntity();
        account.setUsername("daniel");
        account.setPassword("yoyo123");

        waste = new WasteEntity();
        waste.setAccount(account);
        waste.setEan("299792458");
        waste.setPrice(420);
        waste.setFullName("never gonna give you up");
        waste.setWeight(1000);

    }
    @Test
    void getEan() {
        assertEquals("299792458",waste.getEan());
        assertNotEquals("123",waste.getEan());
        assertNotNull(waste.getEan());
    }

    @Test
    void setEan() {
        waste.setEan("123456789");
        assertEquals("123456789",waste.getEan());
        assertNotEquals("299792458",waste.getEan());
        assertNotNull(waste.getEan());
    }

    @Test
    void getFullName() {
        assertEquals("never gonna give you up",waste.getFullName());
        assertNotEquals("never gonna let you down",waste.getFullName());
        assertNotNull(waste.getFullName());
    }

    @Test
    void setFullName() {
        waste.setFullName("never gonna run around");
        assertEquals("never gonna run around",waste.getFullName());
        assertNotEquals("and desert you",waste.getFullName());
        assertNotNull(waste.getFullName());

    }

    @Test
    void getPrice() {
        assertEquals(420,waste.getPrice());
        assertNotEquals(42,waste.getPrice());
    }

    @Test
    void setPrice() {
        waste.setPrice(42);
        assertEquals(42,waste.getPrice());
        assertNotEquals(420,waste.getPrice());
    }

    @Test
    void getWeight() {
        assertEquals(1000,waste.getWeight());
    }

    @Test
    void setWeight() {
        waste.setWeight(999);
        assertEquals(999,waste.getWeight());
    }

    @Test
    void getAccount() {
        assertEquals("daniel",waste.getAccount().getUsername());
        assertEquals("yoyo123",waste.getAccount().getPassword());
        assertNotNull(waste.getAccount());
    }
}