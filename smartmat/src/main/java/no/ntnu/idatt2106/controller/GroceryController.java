package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/grocery") //TODO auth?????
public class GroceryController {

    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private GroceryService groceryService;

    @GetMapping("/")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(fridgeRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody FridgeEntity product){
        boolean added = groceryService.addProduct(product);
        if(added){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<?> removeProduct(@RequestBody FridgeEntity product){
        boolean removed = groceryService.removeProduct(product);
        if(removed){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/throwProduct")
    public ResponseEntity<?> throwProduct(@RequestBody FridgeEntity product){
        boolean thrown = groceryService.throwProduct(product);
        if(thrown){
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
