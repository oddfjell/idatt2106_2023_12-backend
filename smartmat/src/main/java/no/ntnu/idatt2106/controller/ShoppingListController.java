package no.ntnu.idatt2106.controller;

import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest controller for all /shoppingList endpoints
 */
@RequestMapping(value = "/shoppingList")
@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
public class ShoppingListController {

    /**
     * ShoppingListService field injection
     */
    @Autowired
    private ShoppingListService shoppingListService;

    /**
     * Endpoint to get the shoppingList of an account
     * @param account AccountEntity
     * @return ResponseEntity<List<ShoppingListDTO>>
     */
    @GetMapping("/")
    public ResponseEntity<List<ShoppingListDTO>> getShoppingList(@AuthenticationPrincipal AccountEntity account) {
        return new ResponseEntity<>(shoppingListService.getShoppingList(account.getAccount_id()), HttpStatus.OK);
    }


    /**
     * Endpoint to save the changes to a shoppingList
     * @param account AccountEntity
     * @param listOfDTOs List<ShoppingListDTO>
     * @return ResponseEntity<Boolean>
     */
    @PutMapping("/save")
    public ResponseEntity<Boolean> updateAll(@AuthenticationPrincipal AccountEntity account,
                                             @RequestBody List<ShoppingListDTO> listOfDTOs) {
        boolean success = shoppingListService.save(account, listOfDTOs);

        if (success) {
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to add all the grocerys from the week menu to the shoppingList
     * @param account AccountEntity
     * @param recipeEntities List<RecipeEntity>
     * @return ResponseEntity<?>
     */
    @PostMapping("/addAllFromMenu")
    public ResponseEntity<?> addAllFromMenuToShoppingList(@AuthenticationPrincipal AccountEntity account,
                                                          @RequestBody List<RecipeEntity> recipeEntities) {
        List<ShoppingListDTO> dtos = new ArrayList<>();
        shoppingListService.getCorrectGroceriesFromRecipes(recipeEntities)
                .forEach(item -> {
                    ShoppingListDTO shoppingListDTO = new ShoppingListDTO(item);
                    shoppingListDTO.setCount(1);
                    shoppingListDTO.setFoundInStore(false);
                    dtos.add(shoppingListDTO);
                });
        if (shoppingListService.save(account, dtos)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Endpoint to buy the grocerys labeled as foundInStore
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @PostMapping("/buy")
    public ResponseEntity<?> buy(@AuthenticationPrincipal AccountEntity account) {
        try {
            shoppingListService.buyMarkedGroceries(account);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Endpoint for a limited profile to suggest grocerys
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return ResponseEntity<?>
     */
    @PostMapping("/suggest")
    public ResponseEntity<?> suggest(@AuthenticationPrincipal AccountEntity account, @RequestBody ShoppingListDTO shoppingListDTO){
        shoppingListService.moveSuggestionToList(account,shoppingListDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to remove a grocery from the shoppingList
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return ResponseEntity<?>
     */
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal AccountEntity account, @RequestBody ShoppingListDTO shoppingListDTO){
        shoppingListService.delete(account, shoppingListDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to get the suggestions from the limited profiles
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @GetMapping("/getSuggestions")
    public ResponseEntity<?> getSuggestions(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(shoppingListService.getSuggestionsByAccount(account));
    }
}
