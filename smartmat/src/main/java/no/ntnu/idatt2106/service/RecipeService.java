package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.RecipeUrlAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.AccountRecipeEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.repository.AccountRecipeRepository;
import no.ntnu.idatt2106.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RecipeService {

    @Autowired
    private AccountRecipeRepository accountRecipeRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    public void addRecipeUrl(RecipeEntity recipe) throws RecipeUrlAlreadyExistsException {
        if(recipeRepository.findByUrl(recipe.getUrl()).isPresent()){
            logger.info("{} already exist", recipe.getTitle());
            throw new RecipeUrlAlreadyExistsException();
        }
        logger.info("Saving {}", recipe.getTitle());
        recipeRepository.save(recipe);
    }

    public void addRecipeToAccount(RecipeEntity recipe, AccountEntity account){
        if(recipeRepository.findByUrl(recipe.getUrl()).isEmpty()){
            logger.info("Saving {} by url", recipe.getTitle());
            recipeRepository.save(recipe);
        }
        AccountRecipeEntity accountRecipeEntity = new AccountRecipeEntity();
        accountRecipeEntity.setRecipe(recipeRepository.findByUrl(recipe.getUrl()).get());
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
        return recipeEntityList;
    }
}
