package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShoppingListServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    JWTService jwtService;

    @Autowired
    ShoppingListRepository shoppingListRepository;

    private AccountEntity account;

    @Autowired
    private ShoppingListService shoppingListService;

    @BeforeEach
    void setUp() throws AccountAlreadyExistsException {
        account = new AccountEntity();
        account.setUsername("testShopplistService");
        account.setPassword("TestPassword");
        accountService.addAccount(account);
        shoppingListService.add(account, new ShoppingListDTO("Løk", 1, false, false));
    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account);
    }

    @Test
    void testAdd() {
        assertTrue(
                shoppingListService.add(
                        account, new ShoppingListDTO("Tomat", 1, false, false)));

    }

    @Test
    void testUpdateCount() {
        assertEquals(1,
                shoppingListRepository.findByAccountEntityAndGroceryEntityName(
                        account, "Løk"
                ).get().getCount()
        );
        assertTrue(
                shoppingListService.updateCount(
                        account, new ShoppingListDTO("Løk", 2, false, false)
                )
        );
        assertEquals(2,
                shoppingListRepository.findByAccountEntityAndGroceryEntityName(
                        account, "Løk"
                ).get().getCount()
        );
    }

    @Test
    void testExists() {
        assertTrue(
                shoppingListService.exist(
                        account, new ShoppingListDTO("Løk", 1, false, false)
                )
        );
        assertFalse(
                shoppingListService.exist(
                        account, new ShoppingListDTO("OOOSSSTTT", 1, false, false)
                )
        );
    }
}
