package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import no.ntnu.idatt2106.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/shoppingList") //TODO auth?????
@RestController
public class ShoppingListController {

    @Autowired
    private ShoppingListRepository shoppingListRepository;
    @Autowired
    private ShoppingListService shoppingListService;

    @GetMapping("/")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(shoppingListRepository.findAll(), HttpStatus.OK);
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
}
