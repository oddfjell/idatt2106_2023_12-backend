package no.ntnu.idatt2106.controller;


import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.Recipe;
import no.ntnu.idatt2106.payload.RecipeSuggestionRequest;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.service.RecipeSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping(value = "/recipes")
@RestController
public class RecipeController {

    @Autowired
    private RecipeSuggestionService recipeSuggestionService;
    

    @GetMapping("/weekMenu")
    public ResponseEntity<?> getWeekMenu(@AuthenticationPrincipal AccountEntity accountEntity) {

        ArrayList<Recipe> weekMenu = (ArrayList<Recipe>) recipeSuggestionService.getNRecipes(7,
                recipeSuggestionService.sortRecipes(
                        recipeSuggestionService.rankRecipes(
                                recipeSuggestionService.readRecipesFromScraper(), recipeSuggestionRequest.getIngredients())));
        return null;
    }

    @GetMapping("/recipe")
    public ResponseEntity<?> getRecipe(@AuthenticationPrincipal AccountEntity accountEntity) {
        Recipe recipeSuggest = recipeSuggestionService.getNRecipes(1,
                recipeSuggestionService.sortRecipes(
                        recipeSuggestionService.rankRecipes(
                                recipeSuggestionService.readRecipesFromScraper(), recipeSuggestionRequest.getIngredients()))).get(0);
        return null;
    }

    @GetMapping("/newRecipe")
    public ResponseEntity<?> getNewRecipe(@AuthenticationPrincipal AccountEntity accountEntity) {
        Recipe recipeSuggest = recipeSuggestionService.getNRecipes(1,
                recipeSuggestionService.sortRecipes(
                        recipeSuggestionService.rankRecipes(
                                recipeSuggestionService.readRecipesFromScraper(), recipeSuggestionRequest.getIngredients()))).get(0);
        return null;
    }

    @GetMapping("/nRecipes")
    public ResponseEntity<?> getNRecipes() {
        return null;
    }
}
