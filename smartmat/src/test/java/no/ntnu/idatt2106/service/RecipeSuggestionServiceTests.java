package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        assertEquals(30, recipeSuggestionService.readRecipesFromScraper().size());
    }

    @Test
    void testRankingSortingRecipes() {
        ArrayList<String> groceries = new ArrayList<>(Arrays.asList("potet", "olje", "torskefilet", "egg", "hvetemel", "maisenna", "bakepulver"));
        assertEquals(groceries.size(), recipeSuggestionService.sortRecipes(recipeSuggestionService.rankRecipes(
                recipeSuggestionService.readRecipesFromScraper(), groceries
        )).get(0).getValue());
    }

}
