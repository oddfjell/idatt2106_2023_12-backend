package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/shoppingList")
@RestController
public class ShoppingListController {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @PostMapping("/")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(shoppingListRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToShoppingList(){
        /*
        %shoppingListRepository.add(account)%
        SKJEKK FOR Å ANGI STATUS
        */
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromShoppingList(){
        /*
        %shoppingListRepository.delete(account)%
        SKJEKK FOR Å ANGI STATUS
        */
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptRequest(){//TODO ta imot en slags form for id
        /*
        shoppingListRepository.%bytt status til ordentlig objekt i lista (account)%
        SKJEKK FOR Å ANGI STATUS
        */
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }


}
