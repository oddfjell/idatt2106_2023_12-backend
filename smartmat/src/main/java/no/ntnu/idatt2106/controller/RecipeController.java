package no.ntnu.idatt2106.controller;

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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Rest controller for all /recipes endpoints
 */
@RestController
@CrossOrigin(origins = {"http://localhost:5173/","http://localhost:4173/"}, allowCredentials = "true")
@RequestMapping(value = "/recipes")
public class RecipeController {

    /**
     * RecipeService field injection
     */
    @Autowired
    private RecipeService recipeService;
    /**
     * RecipeSuggestionService field injection
     */
    @Autowired
    private RecipeSuggestionService recipeSuggestionService;

    /**
     * Endpoint that makes and returns a week menu with n recipes with x servings
     * @param accountEntity AccountEntity
     * @param servings int
     * @param nDays int
     * @return ResponseEntity<?>
     */
    @GetMapping("/weekMenu/{servings}/{nDays}")
    public ResponseEntity<?> getWeekMenu(@AuthenticationPrincipal AccountEntity accountEntity, @PathVariable int servings, @PathVariable int nDays) {
        List<RecipeEntity> weekMenu =  recipeSuggestionService.getNRecipes(nDays, accountEntity, servings);
        return ResponseEntity.ok(weekMenu);
    }

    /**
     * Endpoint that returns 1 recipe with x servings
     * @param accountEntity AccountEntity
     * @param servings int
     * @return ResponseEntity<RecipeEntity>
     */
    @GetMapping("/recipe/{servings}")
    public ResponseEntity<RecipeEntity> getRecipe(@AuthenticationPrincipal AccountEntity accountEntity, @PathVariable int servings) {
        RecipeEntity recipeEntitySuggest = recipeSuggestionService.getNRecipes(1, accountEntity, servings).get(0);
        return ResponseEntity.ok(recipeEntitySuggest);
    }

    /**
     * Endpoint to change the current recipe
     * @param accountEntity AccountEntity
     * @param recipeEntities List<RecipeEntity>
     * @param servings int
     * @return ResponseEntity<RecipeEntity>
     */
    @PostMapping("/newRecipe/{servings}")
    public ResponseEntity<RecipeEntity> getNewRecipe(@AuthenticationPrincipal AccountEntity accountEntity, @RequestBody List<RecipeEntity> recipeEntities, @PathVariable int servings) {
        RecipeEntity recipeEntitySuggest = recipeSuggestionService.getNRecipes(30, accountEntity, servings).stream().filter(r->{
            AtomicBoolean found = new AtomicBoolean(true);
            recipeEntities.forEach(listR->{
                if(r.getUrl().equals(listR.getUrl())){
                    System.out.println(r);
                    found.set(false);
                }
            });
            return found.get();
        }).findFirst().get();
        return ResponseEntity.ok(recipeEntitySuggest);
    }

    /**
     * Endpoint to get recipes from web scraper
     * @return ResponseEntity<?>
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    @PutMapping
    public ResponseEntity<?> scrapeRecipes() throws IOException, InterruptedException {
        int exitVal = recipeSuggestionService.runScraper();
        if (exitVal != 0){
            return (ResponseEntity<?>) ResponseEntity.internalServerError();
        }
        return ResponseEntity.ok(exitVal);
    }

    /**
     * Method to save a recipe to an account
     * @param account AccountEntity
     * @param recipe RecipeEntity
     * @return ResponseEntity<?>
     */
    @PostMapping("/saveRecipe")
    public ResponseEntity<?> saveRecipe(@AuthenticationPrincipal AccountEntity account, @RequestBody RecipeEntity recipe){
        recipeService.addRecipeToAccount(recipe,account);
        return ResponseEntity.ok("Recipe added to account");
    }

    /**
     * Method to save recipes to an account
     * @param account AccountEntity
     * @param recipes List<RecipeEntity>
     * @return ResponseEntity<?>
     */
    @PostMapping("/saveRecipes")
    public ResponseEntity<?> saveRecipes(@AuthenticationPrincipal AccountEntity account, @RequestBody List<RecipeEntity> recipes){
        recipeService.addRecipesToAccount(recipes,account);
        return ResponseEntity.ok("Recipes added to account");
    }

    /**
     * Method to save a week meny to an account
     * @param account AccountEntity
     * @return ResponseEntity<?>
     */
    @GetMapping("/getSavedWeekMenu")
    public ResponseEntity<?> getSavedWeekMenu(@AuthenticationPrincipal AccountEntity account){
        return ResponseEntity.ok(recipeService.getRecipesByAccount(account));
    }

    /**
     * Method to replace a recipe with a new
     * @param account AccountEntity
     * @param recipeEntities List<RecipeEntity>
     * @return ResponseEntity<?>
     */
    @PostMapping("/replaceRecipe")
    public ResponseEntity<?> replaceRecipe(@AuthenticationPrincipal AccountEntity account, @RequestBody List<RecipeEntity> recipeEntities){
        try{
            System.out.println("-------------------------------");
            System.out.println(recipeEntities);
            System.out.println("-------------------------------");
            recipeService.replaceRecipeWithRecipe(account,recipeEntities.get(0),recipeEntities.get(1));
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
