package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.RecipeUrlAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.AccountRecipeEntity;
import no.ntnu.idatt2106.model.IngredientEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.repository.AccountRecipeRepository;
import no.ntnu.idatt2106.repository.IngredientRepository;
import no.ntnu.idatt2106.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecipeService {

    @Autowired
    private AccountRecipeRepository accountRecipeRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    public void addRecipeUrl(RecipeEntity recipe) throws RecipeUrlAlreadyExistsException {
        if(recipeRepository.findByUrlAndServings(recipe.getUrl(),recipe.getServings()).isPresent()){
            logger.info("{} already exist", recipe.getTitle());
            throw new RecipeUrlAlreadyExistsException();
        }
        logger.info("Saving {}", recipe.getTitle());
        recipeRepository.save(recipe);
    }

    public void addRecipeToAccount(RecipeEntity recipe, AccountEntity account){
        if(recipeRepository.findByUrlAndServings(recipe.getUrl(),recipe.getServings()).isEmpty()){
            logger.info("Saving {} by url", recipe.getTitle());
            recipeRepository.save(recipe);
        }

        AccountRecipeEntity accountRecipeEntity = new AccountRecipeEntity();
        accountRecipeEntity.setRecipe(recipeRepository.findByUrlAndServings(recipe.getUrl(),recipe.getServings()).get());
        accountRecipeEntity.setAccountEntity(account);
        logger.info("Saving {}", recipe.getTitle());
        accountRecipeRepository.save(accountRecipeEntity);
    }

    public void addRecipesToAccount(List<RecipeEntity> recipes, AccountEntity account){

        accountRecipeRepository.removeAllByAccountEntity(account);
        for (RecipeEntity recipeEntity:recipes) {
            this.addRecipeToAccount(recipeEntity, account);
        }
    }

    public List<RecipeEntity> getRecipesByAccount(AccountEntity account){
        List<AccountRecipeEntity> accountRecipeEntityList = accountRecipeRepository.findAllByAccountEntity(account);
        List<RecipeEntity> recipeEntityList = new ArrayList<>();

        accountRecipeEntityList.forEach(accountRecipeEntity -> recipeEntityList.add(accountRecipeEntity.getRecipe()));
        recipeEntityList.forEach(recipe -> {
            List<IngredientEntity> ingredientList = ingredientRepository.findAllByRecipeUrlAndRecipeServings(recipe.getUrl(), recipe.getServings());
            List<String> stringList = new ArrayList<>();

            ingredientList.forEach(ingredientEntity -> stringList.add(ingredientEntity.getName()));
            recipe.setIngredients(stringList.toArray(new String[0]));
        });
        return recipeEntityList;
    }

    public void replaceRecipeWithRecipe(@AuthenticationPrincipal AccountEntity account, RecipeEntity fromRecipe, RecipeEntity toRecipe) throws Exception {
        Optional<RecipeEntity> optionalFromRecipe = recipeRepository.findByUrlAndServings(fromRecipe.getUrl(), fromRecipe.getServings());
        Optional<RecipeEntity> optionalToRecipe = recipeRepository.findByUrlAndServings(toRecipe.getUrl(), toRecipe.getServings());

        if(optionalFromRecipe.isPresent() && optionalToRecipe.isPresent()){
            accountRecipeRepository.replaceRecipe(account, optionalFromRecipe.get(), optionalToRecipe.get());
        }else{
            throw new Exception("FromRecipe:" + optionalFromRecipe.isPresent() + "|| ToRecipe:" + optionalToRecipe.isPresent());
        }
    }
}
