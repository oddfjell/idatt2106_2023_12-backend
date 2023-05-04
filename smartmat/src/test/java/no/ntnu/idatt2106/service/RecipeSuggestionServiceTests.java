package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.RecipeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeSuggestionServiceTests {

    private RecipeSuggestionService recipeSuggestionService;

    @BeforeEach
    void setUp() {
        recipeSuggestionService = new RecipeSuggestionService(true);
    }

    @Test
    void testReadFromCsv() {
        assertEquals(30, recipeSuggestionService.readRecipesFromScraper(4).size());
    }

    @Test
    void testRankingSortingRecipes() {
        ArrayList<String> groceries = new ArrayList<>(Arrays.asList("potet", "torskefilet", "egg", "hvetemel", "maisenna", "bakepulver"));
        assertEquals(groceries.size(), recipeSuggestionService.sortRecipes(recipeSuggestionService.rankRecipes(
                recipeSuggestionService.readRecipesFromScraper(4), groceries
        )).get(0).getValue());
    }

    @Test
    void testChangingServings() {
        ArrayList<RecipeEntity> recipes = (ArrayList<RecipeEntity>) recipeSuggestionService.readRecipesFromScraper(4);
        ArrayList<RecipeEntity> recipesDiffServing = (ArrayList<RecipeEntity>) recipeSuggestionService.readRecipesFromScraper(5);
        assertNotEquals(recipes.get(0).getIngredients(), recipesDiffServing.get(0).getIngredients());

    }
}
