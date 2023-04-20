package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/login")
@RestController
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/")
    public ResponseEntity<?> login(@RequestBody AccountEntity account){
        /*
        AccountEntity loginRequest = accountRepository.%get account by username%;
        if (loginRequest != null){
            if(loginRequest.getPassword == account.getPassword()){
                return new ResponseEntity<>(%generer en token her%, "HttpStatus.ACCEPTED);}
            }
        } else return new ResponseEntity<>("Gal Informasjon", "HttpStatus.BAD_REQUEST);
        */
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

}
