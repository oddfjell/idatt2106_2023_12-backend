package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import no.ntnu.idatt2106.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/shoppingList") //TODO auth?????
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    // GET STORED SHOPPING LIST FOR AN ACCOUNT
    @GetMapping("/")
    public ResponseEntity<List<ShoppingListDTO>> getShoppingList(@AuthenticationPrincipal AccountEntity account){
        return new ResponseEntity<>(shoppingListService.getShoppingList(account.getAccount_id()), HttpStatus.TOO_EARLY);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToShoppingList(@RequestBody ShoppingListEntity product){
        boolean added = shoppingListService.addToShoppingList(product);
        if(added){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromShoppingList(@RequestBody ShoppingListEntity product){
        boolean removed = shoppingListService.removeFromShoppingList(product);
        if(removed){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //TODO NOTAT: decline blir remove
    @PutMapping("/accept")
    public ResponseEntity<?> acceptRequest(@RequestBody ShoppingListEntity product){//TODO ta imot en slags form for id
        /*
        shoppingListRepository.%bytt status til ordentlig objekt i lista (account)%
        SKJEKK FOR Å ANGI STATUS
        */
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }


    @PostMapping("/mark")
    public ResponseEntity<?> markGroceryAsFound(@AuthenticationPrincipal AccountEntity account, @RequestBody ShoppingListDTO groceryName){
        shoppingListService.updateFoundInStore(account,groceryName.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@AuthenticationPrincipal AccountEntity account) {
        try{
            shoppingListService.buyMarkedGroceries(account);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
