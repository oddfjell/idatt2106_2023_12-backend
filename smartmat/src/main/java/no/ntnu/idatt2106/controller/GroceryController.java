package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.service.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Rest controller for all /grocery endpoints
 */
@RequestMapping(value = "/grocery")
@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
public class GroceryController {

    /**
     * GroceryService field injection
     */
    @Autowired
    private GroceryService groceryService;

    /**
     * Endpoint that returns all the grocery's
     * @return ResponseEntity<List<GroceryEntity>>
     */
    @GetMapping("/")
    public ResponseEntity<List<GroceryEntity>> getProducts(){
        return ResponseEntity.ok(groceryService.getAllGroceries());
    }

    /**
     * Endpoint that returns all the grocery's in a certain category
     * @param id String
     * @return ResponseEntity<List<GroceryEntity>>
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<GroceryEntity>> getProductsByCategory(@PathVariable String id){
        return ResponseEntity.ok(groceryService.getAllGroceriesByCategoryId(Long.parseLong(id)));
    }

    /**
     * Endpoint that populates the database with grocery's
     * @return ResponseEntity<?>
     */
    @PostMapping("/populate")
    public ResponseEntity<?> populateDB(){
        groceryService.populateDbGroceries();
        return ResponseEntity.ok("amen");
    }
}
