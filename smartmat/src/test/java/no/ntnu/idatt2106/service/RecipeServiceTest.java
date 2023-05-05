package no.ntnu.idatt2106.service;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.exceptions.AccountAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    ShoppingListRepository shoppingListRepository;

    @Autowired
    RecipeSuggestionService recipeSuggestionService;

    private AccountEntity account;

    @Autowired
    private RecipeService recipeService;

    private List<RecipeEntity> recipes;

    @BeforeEach
    void setUp() throws AccountAlreadyExistsException {
        account = new AccountEntity();
        account.setUsername("testRecipeService");
        account.setPassword("TestPassword");
        accountService.addAccount(account);
        recipes = recipeSuggestionService.readRecipesFromScraper(4);
    }

    @AfterEach
    void tearDown() {
        accountService.removeAccount(account);
    }

    @Test
    void testAddGetRecipesToAccount() {
        recipeService.addRecipesToAccount(recipes, account);
        assertEquals(recipes.get(0), recipeService.getRecipesByAccount(account).get(0));
    }

    @Test
    void testReplaceRecipeWithRecipe() throws Exception {
        recipeService.addRecipesToAccount(recipes, account);
        RecipeEntity oldRecipe = recipeService.getRecipesByAccount(account).get(0);
        RecipeEntity newRecipe = recipeService.getRecipesByAccount(account).get(1);

        recipeService.replaceRecipeWithRecipe(account, oldRecipe, newRecipe);
        assertEquals(newRecipe, recipeService.getRecipesByAccount(account).get(0));
    }
}
