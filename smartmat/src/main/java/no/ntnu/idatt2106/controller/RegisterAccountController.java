package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/register")
@RestController
public class RegisterAccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/")
    public ResponseEntity<?> registerAccount(@RequestBody AccountEntity account){
        boolean madeNewAccount = accountService.registerAccount(account);
        if(!madeNewAccount){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}
