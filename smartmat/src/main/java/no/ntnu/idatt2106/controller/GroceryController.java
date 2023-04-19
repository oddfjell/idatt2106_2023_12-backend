package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/grocery") //TODO auth?????
public class GroceryController {

    @Autowired
    private FridgeRepository fridgeRepository;

    @PostMapping("/")
    public ResponseEntity<?> getProducts(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @PostMapping("/editProduct")
    public ResponseEntity<?> editProduct(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<?> removeProduct(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }

    @DeleteMapping("/throwProduct")
    public ResponseEntity<?> throwProduct(){
        return new ResponseEntity<>(HttpStatus.TOO_EARLY);
    }
}
