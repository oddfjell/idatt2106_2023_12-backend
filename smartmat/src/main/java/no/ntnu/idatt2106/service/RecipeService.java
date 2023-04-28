package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.RecipeUrlAlreadyExistsException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.AccountRecipeEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.repository.AccountRecipeRepository;
import no.ntnu.idatt2106.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RecipeService {

    @Autowired
    private AccountRecipeRepository accountRecipeRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public void addRecipeUrl(RecipeEntity recipe) throws RecipeUrlAlreadyExistsException {
        if(recipeRepository.findByUrl(recipe.getUrl()).isPresent()){
            throw new RecipeUrlAlreadyExistsException();
        }
        recipeRepository.save(recipe);
    }

    public void addRecipeToAccount(RecipeEntity recipe, AccountEntity account){
        if(recipeRepository.findByUrl(recipe.getUrl()).isEmpty()){
            recipeRepository.save(recipe);
        }
        AccountRecipeEntity accountRecipeEntity = new AccountRecipeEntity();
        accountRecipeEntity.setRecipe(recipeRepository.findByUrl(recipe.getUrl()).get());
        accountRecipeEntity.setAccountEntity(account);
        accountRecipeRepository.save(accountRecipeEntity);
    }




}
