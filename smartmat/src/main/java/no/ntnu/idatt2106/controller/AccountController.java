package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/auth/account")
@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @CrossOrigin
    @GetMapping("/")
    public Iterable<AccountEntity> getAllAccounts() {

        return accountRepository.findAll();

    }

    @PostMapping("/editAccount")
    public ResponseEntity<?> editAccount(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAccount(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    // TODO MAKE USERS TO ACCOUNT
    //  DO NOT USE THESE UNTIL THEN
    //  On povons peut-etre utiliser an outre controller??
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }
}
