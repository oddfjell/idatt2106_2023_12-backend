package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.exceptions.UserAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth/account")
@RestController
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @CrossOrigin
    @GetMapping("/")
    public Iterable<AccountEntity> getAllAccounts() {
        return accountService.getAllUsers();
    }

    @PostMapping("/editAccount")
    public ResponseEntity<?> editAccount(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAccount(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @PostMapping("/registeruser")
    public ResponseEntity<?> registerUser(@RequestBody AccountEntity account){
        try{
            accountService.addUser(account);
            return ResponseEntity.ok().body("User added");
        }catch (UserAlreadyExistsException userAlreadyExistsException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AccountEntity account){
        String loginResponse = accountService.loginUser(account);
        if(loginResponse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.ok(loginResponse);
        }
    }

}
