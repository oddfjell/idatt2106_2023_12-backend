package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.UserAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;
    private JWTService jwtService;
    private EncryptionService encryptionService;


    public AccountService(AccountRepository accountRepository, JWTService jwtService, EncryptionService encryptionService) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
        this.encryptionService = encryptionService;
    }


    public List<AccountEntity> getAllUsers(){
        return accountRepository.findAll();
    }

    public void updateUsername(String username, AccountEntity account){
        accountRepository.updateUsername(username, account.getId());
    }

    public void updatePassword(String password, AccountEntity account){
        accountRepository.updatePassword(encryptionService.encryptPassword(password), account.getId());
    }

    public void removeAccount(AccountEntity account){
        accountRepository.removeAccountEntityById(account.getId());
    }


    public void addUser(AccountEntity account) throws UserAlreadyExistsException {
        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        account.setPassword(encryptionService.encryptPassword(account.getPassword()));
        accountRepository.save(account);
    }

    public String loginUser(AccountEntity account) {
        Optional<AccountEntity> user = accountRepository.findByUsernameIgnoreCase(account.getUsername());
        if(user.isPresent()){
            AccountEntity userEntity = user.get();
            if(encryptionService.verifyPassword(account.getPassword(),userEntity.getPassword())){
                return jwtService.generateJWT(account);
            }
        }
        return null;
    }

    public boolean registerAccount(AccountEntity account){
        Optional<AccountEntity> user = accountRepository.findByUsernameIgnoreCase(account.getUsername());
        if(!user.isPresent()){
            accountRepository.save(account);
            return true;
        }
        return false;
    }


}
