package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;
import no.ntnu.idatt2106.model.api.FridgeGroceryThrowBody;
import no.ntnu.idatt2106.model.api.FridgeResponseBody;
import no.ntnu.idatt2106.service.FridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest controller for all /fridge endpoints
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
@RequestMapping(value = "/fridge")
public class FridgeController {

    /**
     * FridgeService field injection
     */
    @Autowired
    FridgeService fridgeService;

    /**
     * Endpoint that returns all the grocery's belonging to one account
     * @param account AccountEntity
     * @return ResponseEntity<List<FridgeResponseBody>>
     */
    @GetMapping("/groceries")
    public ResponseEntity<List<FridgeResponseBody>> getGroceriesByAccount(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(fridgeService.getAllGroceriesByAccount(account));
    }

    /**
     * Endpoint that adds a grocery to the fridge of one account
     * @param account AccountEntity
     * @param fridgeGroceryBody FridgeGroceryBody
     * @return ResponseEntity<?>
     */
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

    /**
     * Endpoint that removes a grocery from the fridge of one account
     * @param account AccountEntity
     * @param fridgeGroceryBody FridgeGroceryBody
     * @return ResponseEntity<?>
     */
    @PostMapping("/remove")
    public ResponseEntity<?> removeGroceryFromAccountByAmount(@AuthenticationPrincipal AccountEntity account, @RequestBody FridgeGroceryBody fridgeGroceryBody){

        try {
            fridgeService.removeGroceryFromAccountByAmount(account, fridgeGroceryBody);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong. May be invalid count number");
        }
    }

    /**
     * Endpoint that throws a certain percent of a grocery from the fridge of one account
     * @param account AccountEntity
     * @param fridgeGroceryThrowBody FridgeGroceryThrowBody
     * @return ResponseEntity<?>
     * @throws Exception Exception
     */
    @PostMapping("/throw")
    public ResponseEntity<?> throwGroceryFromAccountByAmount(@AuthenticationPrincipal AccountEntity account, @RequestBody FridgeGroceryThrowBody fridgeGroceryThrowBody) throws Exception {
        try{
            fridgeGroceryThrowBody.setThrowDate();
            fridgeService.throwGroceryFromFridgeToWaste(account, fridgeGroceryThrowBody);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong. May be invalid count number");
        }
    }
}
