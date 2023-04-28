package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/grocery")
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class GroceryController {

    @Autowired
    private GroceryService groceryService;

    @GetMapping("/")
    public ResponseEntity<List<GroceryEntity>> getProducts(){
        return ResponseEntity.ok(groceryService.getAllGroceries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<GroceryEntity>> getProductsByCategory(@PathVariable String id){
        return ResponseEntity.ok(groceryService.getAllGroceriesByCategoryId(Long.parseLong(id)));
    }

    @PostMapping("/populate")
    public ResponseEntity<?> populateDB(){
        groceryService.populateDbGroceries();
        return ResponseEntity.ok("amen");
    }

}
