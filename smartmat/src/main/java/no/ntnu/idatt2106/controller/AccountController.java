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

/**
 * Rest controller for all /auth/account endpoints
 */
@RequestMapping(value = "/auth/account")
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
@RestController
public class AccountController {

    /**
     * AccountService field injection
     */
    @Autowired
    private AccountService accountService;

    /**
     * ProfileService field injection
     */
    @Autowired
    private ProfileService profileService;

    /**
     * Endpoint that returns all the accounts from the database
     * @param account AccountEntity
     * @return ResponseEntity<Iterable<AccountEntity>>
     */
    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<Iterable<AccountEntity>> getAllAccounts(@AuthenticationPrincipal AccountEntity account) {
        Iterable<AccountEntity> accounts = accountService.getAllAccounts();
        if (accounts != null) {
            return ResponseEntity.ok(accounts);
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Endpoint that can edit an account password or username in the database
     * @param account AccountEntity
     * @param username String
     * @param password String
     * @return ResponseEntity<?>
     */
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

    /**
     * Endpoint that removes an account from the database
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeAccount(@AuthenticationPrincipal AccountEntity account){
        accountService.removeAccount(account);
        return ResponseEntity.ok("Account removed");
    }

    /**
     * Endpoint that registers/saves an account to the database
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @PostMapping("/registerAccount")
    public ResponseEntity<?> registerAccount(@RequestBody AccountEntity account){
        try{
            accountService.addAccount(account);
            return ResponseEntity.ok().body("User added");
        }catch (AccountAlreadyExistsException userAlreadyExistsException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    /**
     * Endpoint that checks if an account is valid and returns a token to login
     * @param account AccountEntity
     * @return ResponseEntity<LoginResponseBody>
     */
    @PostMapping("/loginAccount")
    public ResponseEntity<LoginResponseBody> loginAccount(@RequestBody AccountEntity account){
        LoginResponseBody loginResponse = accountService.loginAccount(account);
        if(loginResponse == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            return ResponseEntity.ok(loginResponse);
        }
    }

    /**
     * Endpoint that returns all the profiles from a certain account the database
     * @param account AccountEntity
     * @return ResponseEntity<List<ProfileEntity>>
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileEntity>> getProfilesInAccount(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(profileService.getAllProfilesByAccount(account));
    }

    /**
     * Endpoint that register/saves a profile to an account
     * @param account AccountEntity
     * @param profileBody NewProfileBody
     * @return ResponseEntity<?>
     */
    @PostMapping("/registerProfile")
    public ResponseEntity<?> registerUser(@AuthenticationPrincipal AccountEntity account, @RequestBody NewProfileBody profileBody){
        try {
            profileService.addProfileToAccount(profileBody, account);
            return ResponseEntity.ok().build();
        } catch (ProfileAlreadyExistsInAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Profile username already exists");
        }
    }

    /**
     * Endpoint that checks if the PIN is correct and then let the profile login
     * @param account AccountEntity
     * @param profileBody NewProfileBody
     * @return ResponseEntity<ProfileEntity>
     */
    @PostMapping("/profileLogin")
    public ResponseEntity<ProfileEntity> loginProfile(@AuthenticationPrincipal AccountEntity account, @RequestBody NewProfileBody profileBody){
        ProfileEntity profile = profileService.loginProfile(account,profileBody);
        if(profile != null){
            return ResponseEntity.ok(profile);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to delete a profile from an account
     * @param account AccountEntity
     * @param profileBody NewProfileBody
     * @return ResponseEntity<?>
     */
    @PostMapping("/deleteProfile")
    public ResponseEntity<?> deleteProfile(@AuthenticationPrincipal AccountEntity account, @RequestBody NewProfileBody profileBody){
        if(profileService.deleteProfileFromAccount(account,profileBody)){
            return ResponseEntity.ok("Profile deleted");
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

}
