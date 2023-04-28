package no.ntnu.idatt2106.controller;


import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;
import no.ntnu.idatt2106.model.api.FridgeGroceryThrowBody;
import no.ntnu.idatt2106.model.api.FridgeResponseBody;
import no.ntnu.idatt2106.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping(value = "/fridge")
public class FridgeController {

    @Autowired
    FridgeService fridgeService;

    @GetMapping("/groceries")
    public ResponseEntity<List<FridgeResponseBody>> getGroceriesByAccount(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(fridgeService.getAllGroceriesByAccount(account));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGroceryToAccount(@AuthenticationPrincipal AccountEntity account, @RequestBody FridgeGroceryBody fridgeGroceryBody){

        try{
            fridgeService.addGroceryToAccount(account, fridgeGroceryBody);
            return ResponseEntity.ok().build();
        }catch (AccountAlreadyHasGroceryException accountAlreadyHasGroceryException){
            fridgeService.updateGroceryCount(account, fridgeGroceryBody);
            return ResponseEntity.ok("Updated grocery count");
        }catch (AccountDoesntExistException accountDoesntExistException){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeGroceryFromAccountByAmount(@AuthenticationPrincipal AccountEntity account, @RequestBody FridgeGroceryBody fridgeGroceryBody){

        try {
            fridgeService.removeGroceryFromAccountByAmount(account, fridgeGroceryBody);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong. May be invalid count number");
        }
    }

    @PostMapping("/throw")
    public ResponseEntity<?> throwGroceryFromAccountByAmount(@AuthenticationPrincipal AccountEntity account, @RequestBody FridgeGroceryThrowBody fridgeGroceryThrowBody) throws Exception {
        try{
            fridgeService.throwGroceryFromFridgeToWaste(account, fridgeGroceryThrowBody);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong. May be invalid count number");
        }
    }
}
