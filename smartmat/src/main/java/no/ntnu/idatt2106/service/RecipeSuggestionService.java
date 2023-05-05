package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for account related requests
 * AccountService contains methods that gets, changes, adds or deletes accounts
 */
@Service
public class RecipeSuggestionService {

    /**
     * FridgeRepository field injection
     */
    @Autowired
    private FridgeRepository fridgeRepository;
    /**
     * IngredientRepository field injection
     */
    @Autowired
    private IngredientRepository ingredientRepository;
    /**
     * test declaration
     */
    private boolean test;
    /**
     * csvPath declaration
     */
    private String csvPath = System.getProperty("user.dir")+"/src/main/resources/recipeEntities.csv";
    /**
     * scriptPath declaration
     */
    private final String scriptPath = System.getProperty("user.dir")+"/smartmat/src/main/scripts/recipe_scraper.py";
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RecipeSuggestionService.class);

    /**
     * Constructor
     * @param test boolean
     */
    public RecipeSuggestionService(boolean test) {
        if (test) {
            this.test=test;
            csvPath = System.getProperty("user.dir")+"/src/test/resources/recipesTestData.csv";
            logger.info("Got file for recipes");
        }
    }

    /**
     * Constructor
     */
    public RecipeSuggestionService() {
    }

    /**
     * Method to read the recipes provided from the csv file retrieved by the scraper. Then they are returned
     * in a list with length "servings" of RecipeEntity's
     * @param servings int
     * @return List<RecipeEntity>
     */
    public List<RecipeEntity> readRecipesFromScraper(int servings) {
        ArrayList<RecipeEntity> recipeEntities = new ArrayList<>();
        String csvLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvPath));
            while ((csvLine = br.readLine()) != null) {
                String[] readRecipe = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String url = readRecipe[0];
                String title = readRecipe[1];
                String image = readRecipe[2];
                String[] ingredients = new String[readRecipe.length-3];
                System.arraycopy(readRecipe, 3, ingredients, 0, readRecipe.length - 3);
                ingredients = Arrays.stream(ingredients).map(s -> s.replace("\"", "")).map(s -> s.replace(",",".")).toArray(String[]::new);
                recipeEntities.add(new RecipeEntity(url, title, ingredients, servings, image));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Returning recipes from scraper");

        if (test) { return recipeEntities; }

        for (RecipeEntity recipe: recipeEntities) {
            for (String i: recipe.getIngredients()) {
                if(ingredientRepository.findByNameIgnoreCaseAndRecipeUrl(i,recipe.getUrl()).isEmpty()){
                    IngredientEntity ingredient = new IngredientEntity();
                    ingredient.setName(i);
                    ingredient.setRecipe(recipe);
                    ingredientRepository.save(ingredient);
                }
            }
        }
        return recipeEntities;
    }

    /**
     * Method to rank the recipes to the week menu. This id done by checking which ingredients the account has and when
     * they expires
     * @param recipeEntities List<RecipeEntity>
     * @param priorityIngredients List<String>
     * @return List<RecipeEntity>
     */
    public List<RecipeEntity> rankRecipes(List<RecipeEntity> recipeEntities, List<String> priorityIngredients) {
        Pattern pattern;
        Matcher matcher;
        for (RecipeEntity recipeEntity : recipeEntities) {
            for (String ingredient: priorityIngredients) {
                pattern = Pattern.compile(ingredient, Pattern.CASE_INSENSITIVE);
                for (String recipeIngredient: recipeEntity.getIngredients()) {
                    matcher = pattern.matcher(recipeIngredient);
                    if (matcher.find()) {
                        recipeEntity.setValue();
                    }
                }
            }
        }
        logger.info("Returning ranked recipes");
        return recipeEntities;
    }

    /**
     * Method to sort the recipes
     * @param recipeEntities List<RecipeEntity>
     * @return List<RecipeEntity>
     */
    public List<RecipeEntity> sortRecipes(List<RecipeEntity> recipeEntities) {
         recipeEntities.sort((r1, r2) -> {
             return r2.getValue()- r1.getValue();
         });
         logger.info("Returning sorted recipes");
         return recipeEntities;
    }

    /**
     * Returns a list of n recipes by how many servings to the account
     * @param n int
     * @param accountEntity AccountEntity
     * @param servings int
     * @return List<RecipeEntity>
     */
    public List<RecipeEntity> getNRecipes(int n, AccountEntity accountEntity, int servings){
        List<String> groceries = fridgeRepository.findAllByAccountEntity(accountEntity).stream().map(FridgeEntity::getGroceryEntity).map(GroceryEntity::getName).toList();
        List<RecipeEntity> recipeEntities = this.sortRecipes(
                        this.rankRecipes(
                                this.readRecipesFromScraper(servings), groceries));
        logger.info("Returning {} recipes", n);
        return recipeEntities.subList(0, n);
    }

    /**
     * Method to run web scraper and retrieve the recipes
     * @return int
     * @throws IOException IOException
     * @throws InterruptedException InterruptedException
     */
    public int runScraper() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        logger.info("Running SCRAPER");
        return process.waitFor();
    }
}
