package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.api.LoginResponseBody;
import no.ntnu.idatt2106.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for account related requests
 * AccountService contains methods that gets, changes, adds or deletes accounts
 */
@Service
@Transactional
public class AccountService {

    /**
     * AccountRepository field injection
     */
    @Autowired
    private AccountRepository accountRepository;
    /**
     * JWTService field injection
     */
    @Autowired
    private JWTService jwtService;
    /**
     * EncryptionService field injection
     */
    @Autowired
    private EncryptionService encryptionService;
    /**
     * ProfileRepository field injection
     */
    @Autowired
    private ProfileRepository profileRepository;
    /**
     * ShoppingListRepository field injection
     */
    @Autowired
    private ShoppingListRepository shoppingListRepository;
    /**
     * FridgeRepository field injection
     */
    @Autowired
    private FridgeRepository fridgeRepository;
    /**
     * WasteRepository field injection
     */
    @Autowired
    private WasteRepository wasteRepository;
    /**
     * AccountRecipeRepository field injection
     */
    @Autowired
    private AccountRecipeRepository accountRecipeRepository;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    /**
     * Method that returns all the accounts from the database
     * @return List<AccountEntity>
     */
    public List<AccountEntity> getAllAccounts(){
        logger.info("Returning all of the accounts");
        return accountRepository.findAll();
    }

    /**
     * Method that updates the username of an account
     * @param username String
     * @param account AccountEntity
     */
    public void updateUsername(String username, AccountEntity account){
        logger.info("Updating the username:{} to be:{}", account.getUsername(), username);
        accountRepository.updateUsername(username, account.getAccount_id());
    }

    /**
     * Method that updates the password of an account
     * @param password String
     * @param account AccountEntity
     */
    public void updatePassword(String password, AccountEntity account){
        logger.info("Updating password for {}", account.getUsername());
        accountRepository.updatePassword(encryptionService.encryptPassword(password), account.getAccount_id());
    }

    /**
     * Method for removing an account. Since the account_id is stored in the database in the children of the account, they
     * have to be removed first. The profiles, shoppingList items, fridge items and waste items attached to the account
     * are being removed before the account.
     * @param account AccountEntity
     */
    public void removeAccount(AccountEntity account){
        logger.info("Removing {}s account  by ACCOUNT and all the other data attached to it", account.getUsername());

        profileRepository.deleteByAccount(account);
        shoppingListRepository.deleteByAccount(account);
        fridgeRepository.deleteByAccount(account);
        wasteRepository.deleteByAccount(account);
        accountRecipeRepository.deleteByAccount(account);

        accountRepository.removeAccountEntityById(account.getAccount_id());
    }

    /**
     * Method for removing an account. Since the account_id is stored in the database in the children of the account, they
     * have to be removed first. The profiles, shoppingList items, fridge items and waste items attached to the account
     * are being removed before the account.
     * @param username String
     */
    public void removeAccount(String username){
        logger.info("Removing {} account", username);
        accountRepository.removeAccountEntityByUsername(username);
    }

    /**
     * Method to register a new account
     * @param account AccountEntity
     * @throws AccountAlreadyExistsException AccountAlreadyExistsException
     */
    public void addAccount(AccountEntity account) throws AccountAlreadyExistsException {
        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isPresent()){
            logger.info("Account with username {} already exists", account.getUsername());
            throw new AccountAlreadyExistsException();
        }
        account.setPassword(encryptionService.encryptPassword(account.getPassword()));
        logger.info("Saving the new account {}", account.getUsername());
        accountRepository.save(account);
    }

    /**
     * Method to login into an existing account. The method gets the account from the database and checks if the password
     * was correct. Then it returns the username and a JWTToken
     * @param account AccountEntity
     * @return LoginResponseBody
     */
    public LoginResponseBody loginAccount(AccountEntity account) {
        Optional<AccountEntity> foundAccount = accountRepository.findByUsernameIgnoreCase(account.getUsername());
        if(foundAccount.isPresent()){
            AccountEntity accountEntity = foundAccount.get();
            if(encryptionService.verifyPassword(account.getPassword(),accountEntity.getPassword())){
                LoginResponseBody loginResponseBody = new LoginResponseBody();
                loginResponseBody.setUsername(accountEntity.getUsername());
                loginResponseBody.setJwt(jwtService.generateJWT(account));
                logger.info("Account {} logged in", loginResponseBody.getUsername());
                return loginResponseBody;
            }
        }
        logger.info("Account with username {} do not exist", account.getUsername());
        return null;
    }
}
