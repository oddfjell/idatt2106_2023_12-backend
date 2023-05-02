package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.exceptions.ProfileAlreadyExistsInAccountException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ProfileEntity;
import no.ntnu.idatt2106.model.api.LoginResponseBody;
import no.ntnu.idatt2106.model.api.NewProfileBody;
import no.ntnu.idatt2106.service.AccountService;
import no.ntnu.idatt2106.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/auth/account")
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<Iterable<AccountEntity>> getAllAccounts(@AuthenticationPrincipal AccountEntity account) {
        if(account.getUsername().equals("daniel")){
            return ResponseEntity.ok(accountService.getAllUsers());
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/editAccount")
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
        try{
            accountService.addUser(account);
            return ResponseEntity.ok().body("User added");
        }catch (AccountAlreadyExistsException userAlreadyExistsException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PostMapping("/loginAccount")
    public ResponseEntity<LoginResponseBody> loginAccount(@RequestBody AccountEntity account){
        LoginResponseBody loginResponse = accountService.loginUser(account);
        if(loginResponse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.ok(loginResponse);
        }
    }


    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileEntity>> getProfilesInAccount(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(profileService.getAllProfilesByAccount(account));
    }

    @PostMapping("/registerProfile")
    public ResponseEntity<?> registerUser(@AuthenticationPrincipal AccountEntity account, @RequestBody NewProfileBody profileBody){
        try {
            profileService.addProfileToAccount(profileBody, account);
            return ResponseEntity.ok().build();
        } catch (ProfileAlreadyExistsInAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Profile username already exists");
        }
    }

    @PostMapping("/profileLogin")
    public ResponseEntity<ProfileEntity> loginProfile(@AuthenticationPrincipal AccountEntity account, @RequestBody NewProfileBody profileBody){
        ProfileEntity profile = profileService.loginProfile(account,profileBody);
        if(profile != null){
            return ResponseEntity.ok(profile);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

}
