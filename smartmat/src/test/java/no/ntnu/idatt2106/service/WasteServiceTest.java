package no.ntnu.idatt2106.service;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.dto.TotalWastePerDateDTO;
import no.ntnu.idatt2106.dto.WastePerCategoryDTO;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WasteServiceTest {

    @Autowired
    AccountService accountService;

    private AccountEntity account;

    @Autowired
    private WasteService wasteService;

    @BeforeEach
    void setUp() throws AccountAlreadyExistsException {
        account = new AccountEntity();
        account.setUsername("testWasteService");
        account.setPassword("TestPassword");
        accountService.addAccount(account);
    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account);
    }

    @Test
    void testGetMoneyLost() {
        assertEquals(0, wasteService.getMoneyLost(account.getAccount_id()));
    }

    @Test
    void testGetMoneyLostByCategoryBoth() {
        assertEquals(0, wasteService.getMoneyLostPerCategory(account.getAccount_id()).stream().map(WastePerCategoryDTO::getMoney_lost).reduce((double) 0, Double::sum));
        assertEquals(0, wasteService.getMoneyLostByCategory(account.getAccount_id(), 1));
    }

    @Test
    void testGetTotalWastePerDateByMonth() {
        assertEquals(0, wasteService.getTotalWastePerDateByMonth(account, 1).stream().map(TotalWastePerDateDTO::getMoney_lost).reduce((double) 0, Double::sum));

    }
}
