package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.exceptions.UserAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Iterable<AccountEntity>> getAllAccounts(@AuthenticationPrincipal AccountEntity account) {
        if(account.getUsername().equals("daniel")){
            return ResponseEntity.ok(accountService.getAllUsers());
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @PostMapping("/editAccount")
    public ResponseEntity<?> editAccount(@AuthenticationPrincipal AccountEntity account, @RequestParam("username") String username,
        @RequestParam("password") String password){

        if(!username.isEmpty()){
            accountService.updateUsername(username,account);
        }

        if(!password.isEmpty()){
            accountService.updatePassword(password, account);
        }
        return ResponseEntity.ok("Account updated");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAccount(@AuthenticationPrincipal AccountEntity account){
        accountService.removeAccount(account);
        return ResponseEntity.ok("Account removed");
    }

    @PostMapping("/registerAccount")
    public ResponseEntity<?> registerAccount(@RequestBody AccountEntity account){
        boolean madeNewAccount = accountService.registerAccount(account);
        if(!madeNewAccount){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    // TODO MAKE USERS TO AN ACCOUNT
    @PostMapping("/registerUser")
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
