package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.model.AccountEntity;

import no.ntnu.idatt2106.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/shoppingList")
@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    // GET STORED SHOPPING LIST FOR AN ACCOUNT
    @GetMapping("/")
    public ResponseEntity<List<ShoppingListDTO>> getShoppingList(@AuthenticationPrincipal AccountEntity account) {
        return new ResponseEntity<>(shoppingListService.getShoppingList(account.getAccount_id()), HttpStatus.OK);
    }

    // SAVE CHANGES IN SHOPPING LIST
    @PutMapping("/save")
    public ResponseEntity<Boolean> updateAll(@AuthenticationPrincipal AccountEntity account,
                                             @RequestBody List<ShoppingListDTO> listOfDTOs) {
        boolean success = shoppingListService.save(account, listOfDTOs);

        if (success) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // BUY MARKED GROCERIES
    @PostMapping("/buy")
    public ResponseEntity<?> buy(@AuthenticationPrincipal AccountEntity account) {
        try {
            shoppingListService.buyMarkedGroceries(account);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
