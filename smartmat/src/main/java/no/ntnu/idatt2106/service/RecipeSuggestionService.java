package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.repository.FridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RecipeSuggestionService {

    @Autowired
    private FridgeRepository fridgeRepository;

    private String csvPath = System.getProperty("user.dir")+"/src/main/resources/recipes.csv";
    private final String scriptPath = System.getProperty("user.dir")+"/smartmat/src/main/scripts/recipe_scraper.py";

    public RecipeSuggestionService(boolean test) {
        if (test) {
            csvPath = System.getProperty("user.dir")+"/src/test/resources/recipesTestData.csv";
        }
    }

    public RecipeSuggestionService() {
    }

    public List<RecipeEntity> readRecipesFromScraper() {
        ArrayList<RecipeEntity> recipeEntities = new ArrayList<>();
        String csvLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader(csvPath));
            while ((csvLine = br.readLine()) != null) {
                String[] readRecipe = csvLine.split(",");
                String url = readRecipe[0];
                String title = readRecipe[1];
                String[] ingredients = new String[readRecipe.length-2];
                System.arraycopy(readRecipe, 2, ingredients, 0, readRecipe.length - 2);
                recipeEntities.add(new RecipeEntity(url, title, ingredients));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeEntities;
    }

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
        return recipeEntities;
    }

    public List<RecipeEntity> sortRecipes(List<RecipeEntity> recipeEntities) {
         recipeEntities.sort((r1, r2) -> {
             return r2.getValue()- r1.getValue();
         });
         return recipeEntities;
    }

    public List<RecipeEntity> getNRecipes(int n, AccountEntity accountEntity){
        List<String> groceries = fridgeRepository.findAllByAccountEntity(accountEntity).stream().map(FridgeEntity::getGroceryEntity).map(GroceryEntity::getName).toList();
        List<RecipeEntity> recipeEntities = this.sortRecipes(
                        this.rankRecipes(
                                this.readRecipesFromScraper(), groceries));
        return recipeEntities.subList(0, n);
    }

    public int runScraper() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        return process.waitFor();
    }

}
