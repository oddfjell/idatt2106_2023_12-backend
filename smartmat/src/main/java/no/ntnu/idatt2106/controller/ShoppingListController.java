package no.ntnu.idatt2106.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/shoppingList")
@RestController
public class ShoppingListController {

    @PostMapping("/")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToShoppingList(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromShoppingList(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }
}
