package no.ntnu.idatt2106.controller;


import lombok.Getter;
import no.ntnu.idatt2106.exceptions.RecipeUrlAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.service.RecipeService;
import no.ntnu.idatt2106.service.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
@RequestMapping(value = "/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeSuggestionService recipeSuggestionService;

    @GetMapping("/weekMenu/{servings}/{nDays}")
    public ResponseEntity<?> getWeekMenu(@AuthenticationPrincipal AccountEntity accountEntity, @PathVariable int servings, @PathVariable int nDays) {
        List<RecipeEntity> weekMenu =  recipeSuggestionService.getNRecipes(nDays, accountEntity, servings);
        return ResponseEntity.ok(weekMenu);
    }

    @GetMapping("/recipe/{servings}")
    public ResponseEntity<RecipeEntity> getRecipe(@AuthenticationPrincipal AccountEntity accountEntity, @PathVariable int servings) {
        RecipeEntity recipeEntitySuggest = recipeSuggestionService.getNRecipes(1, accountEntity, servings).get(0);
        return ResponseEntity.ok(recipeEntitySuggest);
    }

    @PostMapping("/newRecipe/{servings}")
    public ResponseEntity<RecipeEntity> getNewRecipe(@AuthenticationPrincipal AccountEntity accountEntity, @RequestBody List<RecipeEntity> recipeEntities, @PathVariable int servings) {
        RecipeEntity recipeEntitySuggest = recipeSuggestionService.getNRecipes(30, accountEntity, servings).stream().filter(r->!recipeEntities.contains(r)).findFirst().get();
        return ResponseEntity.ok(recipeEntitySuggest);
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

    @PostMapping("/saveRecipe")
    public ResponseEntity<?> saveRecipe(@AuthenticationPrincipal AccountEntity account, @RequestBody RecipeEntity recipe){
        recipeService.addRecipeToAccount(recipe,account);
        return ResponseEntity.ok("Recipe added to account");
    }

    @PostMapping("/saveRecipes")
    public ResponseEntity<?> saveRecipes(@AuthenticationPrincipal AccountEntity account, @RequestBody List<RecipeEntity> recipes){
        recipeService.addRecipesToAccount(recipes,account);
        return ResponseEntity.ok("Recipes added to account");
    }

    @GetMapping("/getSavedWeekMenu")
    public ResponseEntity<?> getSavedWeekMenu(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(recipeService.getRecipesByAccount(account));
    }

    @PostMapping("/replaceRecipe")
    public ResponseEntity<?> replaceRecipe(@AuthenticationPrincipal AccountEntity account, @RequestBody List<RecipeEntity> recipeEntities){
        try{
            recipeService.replaceRecipeWithRecipe(account,recipeEntities.get(0),recipeEntities.get(1));
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
