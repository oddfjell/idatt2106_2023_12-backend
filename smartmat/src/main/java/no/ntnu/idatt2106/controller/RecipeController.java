package no.ntnu.idatt2106.controller;


import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.Recipe;
import no.ntnu.idatt2106.payload.RecipeSuggestionRequest;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.service.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@RequestMapping(value = "/recipes")
public class RecipeController {

    @Autowired
    private RecipeSuggestionService recipeSuggestionService;

    @GetMapping("/weekMenu")
    public ResponseEntity<?> getWeekMenu(@AuthenticationPrincipal AccountEntity accountEntity) {
        List<Recipe> weekMenu =  recipeSuggestionService.getNRecipes(7, accountEntity);
        return ResponseEntity.ok(weekMenu);
    }

    @GetMapping("/recipe")
    public ResponseEntity<Recipe> getRecipe(@AuthenticationPrincipal AccountEntity accountEntity) {
        Recipe recipeSuggest = recipeSuggestionService.getNRecipes(1, accountEntity).get(0);
        return ResponseEntity.ok(recipeSuggest);
    }

    @PostMapping("/newRecipe")
    public ResponseEntity<Recipe> getNewRecipe(@AuthenticationPrincipal AccountEntity accountEntity, @RequestBody List<Recipe> recipes) {
        Recipe recipeSuggest = recipeSuggestionService.getNRecipes(30, accountEntity).stream().filter(r->!recipes.contains(r)).findFirst().get();
        return ResponseEntity.ok(recipeSuggest);
    }

    @GetMapping("/nRecipes")
    public ResponseEntity<?> getNRecipes() {
        return null;
    }

    @PutMapping
    public ResponseEntity<?> scrapeRecipes() throws IOException, InterruptedException {
        int exitVal = recipeSuggestionService.runScraper();
        if (exitVal != 0){
            return (ResponseEntity<?>) ResponseEntity.internalServerError();
        }
        return ResponseEntity.ok(exitVal);
    }
}
