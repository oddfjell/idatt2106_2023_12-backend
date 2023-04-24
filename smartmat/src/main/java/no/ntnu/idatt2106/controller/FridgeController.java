package no.ntnu.idatt2106.controller;


import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.AddGroceryToAccountBody;
import no.ntnu.idatt2106.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
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
    public ResponseEntity<List<GroceryEntity>> getGroceriesByAccount(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(fridgeService.getAllGroceriesByAccount(account));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGroceryToAccount(@AuthenticationPrincipal AccountEntity account, @RequestBody AddGroceryToAccountBody addGroceryToAccountBody){

        try{
            fridgeService.addGroceryToAccount(account, addGroceryToAccountBody);
            return ResponseEntity.ok().build();
        }catch (AccountAlreadyHasGroceryException accountAlreadyHasGroceryException){
            fridgeService.updateGroceryCount(account,addGroceryToAccountBody);
            return ResponseEntity.ok("Updated grocery count");
        }catch (AccountDoesntExistException accountDoesntExistException){
            return ResponseEntity.badRequest().build();
        }
    }


}
