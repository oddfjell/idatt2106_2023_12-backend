package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.exceptions.ProfileAlreadyExistsInAccountException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.api.NewProfileBody;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProfileServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    ShoppingListRepository shoppingListRepository;

    private AccountEntity account;

    @Autowired
    private ProfileService profileService;

    @BeforeEach
    void setUp() throws AccountAlreadyExistsException, ProfileAlreadyExistsInAccountException {
        account = new AccountEntity();
        account.setUsername("testProfileService");
        account.setPassword("TestPassword");
        accountService.addAccount(account);
        profileService.addProfileToAccount(new NewProfileBody("asd", false, "asd"), account);

    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account);
    }

    @Test
    void testAddProfileToAccount() {
        assertEquals("asd", profileService.getAllProfilesByAccount(account).get(0).getUsername());
    }

    @Test
    void testLoginProfile() {
        assertNotNull(profileService.loginProfile(account, new NewProfileBody("asd", false, "asd")));
        assertNull(profileService.loginProfile(account, new NewProfileBody("asd", false, "fafaaff")));
        assertNull(profileService.loginProfile(account, new NewProfileBody("dsadsads", false, "fafaaff")));
    }

    @Test
    void testDeleteProfileFromAccount() {
        assertTrue(profileService.deleteProfileFromAccount(account, new NewProfileBody("asd", false, "asd")));
        assertFalse(profileService.deleteProfileFromAccount(account, new NewProfileBody("asd", false, "asd")));
    }
}
