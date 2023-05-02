package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
//import no.ntnu.idatt2106.logger.ColourLogger;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.api.LoginResponseBody;
import no.ntnu.idatt2106.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private EncryptionService encryptionService;

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    //private static final ColourLogger log = new ColourLogger();

    public List<AccountEntity> getAllUsers(){
        return accountRepository.findAll();
    }

    public void updateUsername(String username, AccountEntity account){
        logger.info("Updating the username:{} to be:{}", account.getUsername(), username);
        accountRepository.updateUsername(username, account.getAccount_id());
    }

    public void updatePassword(String password, AccountEntity account){
        logger.info("Updating password for {}", account.getUsername());
        accountRepository.updatePassword(encryptionService.encryptPassword(password), account.getAccount_id());
    }

    public void removeAccount(AccountEntity account){
        logger.info("Removing {}s account by ACCOUNT", account.getUsername());
        accountRepository.removeAccountEntityById(account.getAccount_id());
    }

    public void removeAccount(String username){
        logger.info("Removing {}s account by USERNAME", username);
        accountRepository.removeAccountEntityByUsername(username);
    }


    public void addUser(AccountEntity account) throws AccountAlreadyExistsException {
        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isPresent()){
            logger.info("Account with username {} already exists", account.getUsername());
            throw new AccountAlreadyExistsException();
        }
        account.setPassword(encryptionService.encryptPassword(account.getPassword()));
        logger.info("Saving the new account {}", account.getUsername());
        accountRepository.save(account);
    }

    public LoginResponseBody loginUser(AccountEntity account) {
        Optional<AccountEntity> user = accountRepository.findByUsernameIgnoreCase(account.getUsername());
        if(user.isPresent()){
            AccountEntity userEntity = user.get();
            if(encryptionService.verifyPassword(account.getPassword(),userEntity.getPassword())){
                LoginResponseBody loginResponseBody = new LoginResponseBody();
                loginResponseBody.setUsername(userEntity.getUsername());
                loginResponseBody.setJwt(jwtService.generateJWT(account));
                logger.info("Account {} logged in", loginResponseBody.getUsername());
                //log.logInfo("Account " + loginResponseBody.getUsername() + " logged in");
                return loginResponseBody;
            }
        }
        logger.info("Account with username {} do not exist", account.getUsername());
        return null;
    }

    //TODO remove
    /*public boolean registerAccount(AccountEntity account){
        Optional<AccountEntity> user = accountRepository.findByUsernameIgnoreCase(account.getUsername());
        if(!user.isPresent()){
            accountRepository.save(account);
            return true;
        }
        return false;
    }*/


}
