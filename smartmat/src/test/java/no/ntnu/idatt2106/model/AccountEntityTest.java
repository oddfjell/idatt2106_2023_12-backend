package no.ntnu.idatt2106.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountEntityTest {

    AccountEntity account;

    @BeforeEach
    void setUp() {

        account = new AccountEntity();
        account.setUsername("daniel");
        account.setPassword("yoyo123");


    }

    @Test
    void getUsername() {
        assertEquals("daniel",account.getUsername());
        assertNotEquals("abhi",account.getUsername());
        assertNotNull(account.getUsername());
    }

    @Test
    void setUsername() {
        account.setUsername("ingrid");
        assertEquals("ingrid",account.getUsername());
        assertNotEquals("daniel",account.getUsername());
        assertNotNull(account.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("yoyo123",account.getPassword());
        assertNotEquals("hei123",account.getPassword());
        assertNotNull(account.getPassword());
    }

    @Test
    void setPassword() {
        account.setPassword("hei123");
        assertEquals("hei123",account.getPassword());
        assertNotEquals("yoyo123",account.getPassword());
        assertNotNull(account.getPassword());
    }
}