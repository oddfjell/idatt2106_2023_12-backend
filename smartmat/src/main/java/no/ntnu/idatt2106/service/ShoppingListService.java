package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.dto.ShoppingListDTO;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.Recipe;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.AccountRepository;

import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;

import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GroceryRepository groceryRepository;

    @Autowired
    private FridgeService fridgeService;

    public List<ShoppingListDTO> getShoppingList(long id){
        return shoppingListRepository.getShoppingList(id);
    }


    public boolean addToShoppingList(long id, ShoppingListDTO shoppingListDTO){

        try {

            // Checks if count is positive + nonzero
            if (shoppingListDTO.getCount() < 1) {
                System.out.println("Count must be greater than 1.");
                return false;
            }

            // Finds correct user
            AccountEntity account = accountRepository.findById(id);
            if (account == null) {
                System.out.println("Account with id " + id + " does not exist in database.");
                return false;
            }

            // Finds correct grocery
            GroceryEntity grocery = groceryRepository.findGroceryEntitiesByNameIgnoreCase(shoppingListDTO.getName());
            if (grocery == null) {
                System.out.println("Grocery " + shoppingListDTO.getName() + " does not exist in database.");
                return false;
            }

            // Checks if grocery is already added to shopping list
            boolean groceryAlreadyExistOnShoppingList = shoppingListRepository.groceryAlreadyExist(id, shoppingListDTO.getName());
            if (groceryAlreadyExistOnShoppingList) {
                shoppingListRepository.updateCountIfExist(account, grocery, shoppingListDTO.getCount());
            } else {
                ShoppingListEntity groceryToBeAdded = new ShoppingListEntity(account, grocery, shoppingListDTO.getCount(), false);
                shoppingListRepository.save(groceryToBeAdded);
            }

            return true;

        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }


    }


    public boolean removeFromShoppingList(ShoppingListEntity product){
        //TODO DO NOT DELETE IF THE PRODUCT AMOUNT IS MORE THAN 1
        int before = shoppingListRepository.findAll().size();
        shoppingListRepository.delete(product);
        int after = shoppingListRepository.findAll().size();
        return before == (after + 1);
    }

    public void acceptRequest(ShoppingListEntity product){//TODO ta imot en slags form for id
        //product.setStatus = true;
    }

    public void updateFoundInStore(AccountEntity account, String groceryName) throws Exception {
        Optional<ShoppingListEntity> shoppingListEntityOptional = shoppingListRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(), groceryName);
        if(shoppingListEntityOptional.isEmpty()){
            throw new Exception();
        }

        Optional<GroceryEntity> groceryEntity = groceryRepository.findByNameIgnoreCase(groceryName);
        if(groceryEntity.isEmpty()){
            throw new Exception();
        }

        shoppingListRepository.updateFoundInStore(!shoppingListEntityOptional.get().isFoundInStore(),account,groceryEntity.get());

    }


    public void buyMarkedGroceries(AccountEntity account) throws Exception {

        List<ShoppingListEntity> shoppingListEntityList = shoppingListRepository.findAllByAccountEntityAndFoundInStoreTrue(account);

        for (ShoppingListEntity shoppingListEntity:shoppingListEntityList) {
            FridgeGroceryBody fridgeGroceryBody = new FridgeGroceryBody();
            fridgeGroceryBody.setName(shoppingListEntity.getGroceryEntity().getName());
            fridgeGroceryBody.setCount(shoppingListEntity.getCount());
            fridgeGroceryBody.setCategoryId(shoppingListEntity.getGroceryEntity().getCategory().getCategory_id());
            try {
                fridgeService.addGroceryToAccount(shoppingListEntity.getAccountEntity(),fridgeGroceryBody);
            } catch (AccountDoesntExistException e) {
                throw new Exception();
            } catch (AccountAlreadyHasGroceryException e) {
                fridgeService.updateGroceryCount(shoppingListEntity.getAccountEntity(), fridgeGroceryBody);
            }
        }

        shoppingListRepository.removeAllByAccountEntityAndFoundInStoreTrue(account);
    }

    public List<String> getCorrectGroceriesFromRecipes(List<Recipe> recipes) {
        HashSet<String> allGroceries = groceryRepository.findAll().stream().map(GroceryEntity::getName).map(String::toLowerCase).collect(Collectors.toCollection(HashSet::new));
        ArrayList<String> groceriesToAdd = new ArrayList<>();
        ingredientsListToIngredients(recipes.stream().map(Recipe::getIngredients)
                .flatMap(Arrays::stream).toList())
                .forEach(i -> groceriesToAdd.add(ingredientToGrocery(i,allGroceries)));

        return groceriesToAdd;
    }

    public List<String> ingredientsListToIngredients(List<String> ingredients){
        return ingredients.stream().map(i -> {
            String[] split = i.split(" ");
            String string = "";
            for (int j = 2; j < split.length; j++) {
                string+=" "+split[j];
            }
            return string;
        }).toList();
    }

    public String ingredientToGrocery(String ingredient, Set<String> allGroceries) {
            for (String t: ingredient.replace("\"", "").split(" ")) {
                if (allGroceries.contains(t)) {
                    return t;
                }
            }

        return ingredient;
    }
}
