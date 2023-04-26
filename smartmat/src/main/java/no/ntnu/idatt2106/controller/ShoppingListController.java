package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.Recipe;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequestMapping(value = "/shoppingList") //TODO auth?????
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    // GET STORED SHOPPING LIST FOR AN ACCOUNT
    @GetMapping("/")
    public ResponseEntity<List<ShoppingListDTO>> getShoppingList(@AuthenticationPrincipal AccountEntity account){
        return new ResponseEntity<>(shoppingListService.getShoppingList(account.getAccount_id()), HttpStatus.OK);
    }

    // TODO: EDIT ENDPOINT FOR JUST ADDING (DONT INCLUDE COUNT, SHOULD BE ITS OWN METHOD 'updateCount')

    // ADD GROCERY TO SHOPPING LIST
    @PostMapping("/add")
    public ResponseEntity<Boolean> addToShoppingList(@AuthenticationPrincipal AccountEntity account,
                                  @RequestBody ShoppingListDTO shoppingListDTO){
        boolean added = shoppingListService.addToShoppingList(account.getAccount_id(), shoppingListDTO);

        if (added) {
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/addAllFromMenu")
    public ResponseEntity<?> addAllFromMenuToShoppingList(@AuthenticationPrincipal AccountEntity account,
                                                          @RequestBody List<Recipe> recipes) {
        System.err.println("HALLOO");
        AtomicInteger countNotFound = new AtomicInteger();
        shoppingListService.getCorrectGroceriesFromRecipes(recipes)
                .forEach(item -> {
                    ShoppingListDTO shoppingListDTO = new ShoppingListDTO(item);
                    shoppingListDTO.setCount(1);
                    shoppingListDTO.setFoundInStore(false);
                    if (!shoppingListService.addToShoppingList(account.getAccount_id(), shoppingListDTO)) {
                        countNotFound.getAndIncrement();
                    }
                });
        return ResponseEntity.ok("Could not find " + countNotFound + " items");
    }

    // REMOVE GROCERY FROM SHOPPING LIST
    @DeleteMapping("/remove")
    public ResponseEntity<Boolean> removeFromShoppingList(@AuthenticationPrincipal AccountEntity account,
                                                          @RequestBody ShoppingListDTO shoppingListDTO){
        boolean removed = shoppingListService.removeFromShoppingList(account.getAccount_id(), shoppingListDTO);

        if(removed){
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
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
        try {
            shoppingListService.updateFoundInStore(account,groceryName.getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
