package no.ntnu.idatt2106.service;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FridgeServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    ShoppingListRepository shoppingListRepository;

    private AccountEntity account;

    @Autowired
    private FridgeService fridgeService;

    @BeforeEach
    void setUp() throws AccountAlreadyExistsException, AccountDoesntExistException, AccountAlreadyHasGroceryException {
        account = new AccountEntity();
        account.setUsername("testFridgeService");
        account.setPassword("TestPassword");
        accountService.addAccount(account);
        fridgeService.addGroceryToAccount(account, new FridgeGroceryBody("Løk", 1L, 1));
    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account);
    }

    @Test
    void testGetAllGroceriesAccount() {
        assertFalse(fridgeService.getAllGroceriesByAccount(account).isEmpty());
    }

    @Test
    void testUpdateGroceryCount() {
        fridgeService.updateGroceryCount(account, new FridgeGroceryBody("Løk", 1L, 1));
        assertEquals(2, fridgeService.getAllGroceriesByAccount(account).get(0).getCount());
    }

    @Test
    void testRemoveGroceryFromAccount() throws Exception {
        fridgeService.updateGroceryCount(account, new FridgeGroceryBody("Løk", 1L, 1));
        fridgeService.removeGroceryFromAccountByAmount(account, new FridgeGroceryBody("Løk", 1L, 1));
        assertEquals(1, fridgeService.getAllGroceriesByAccount(account).get(0).getCount());
        fridgeService.removeGroceryFromAccountByAmount(account, new FridgeGroceryBody("Løk", 1L, 1));
        assertTrue(fridgeService.getAllGroceriesByAccount(account).isEmpty());
    }
}
