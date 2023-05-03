package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.dto.ShoppingListDTO;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.AccountRepository;

import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;

import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ShoppingListService.class);


    public List<ShoppingListDTO> getShoppingList(long id) {
        return shoppingListRepository.getShoppingList(id);
    }

    // Logic for adding to db
    public boolean add(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            ShoppingListEntity groceryToBeAdded = new ShoppingListEntity(account, grocery, shoppingListDTO.getCount(), shoppingListDTO.isFoundInStore(), shoppingListDTO.isSuggestion());
            shoppingListRepository.save(groceryToBeAdded);
            logger.info("Adding {} to {}s shopping list", shoppingListDTO.getName(), account.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            //System.out.println(e.getMessage());
            return false;
        }
    }


    // Logic for updating count
    public boolean updateCount(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.updateCount(account, grocery, shoppingListDTO.getCount());
            logger.info("Updating count of {} to {} in {}s shopping list", grocery.getName(), shoppingListDTO.getCount(), account.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            //System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for updating foundInStore
    public boolean updateShoppingListEntity(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.updateFoundInStore(shoppingListDTO.isFoundInStore(), account, grocery);
            logger.info("{}s foundInSTore and suggestion value in {}s list is now {} and {}", grocery.getName(), account.getUsername(), shoppingListDTO.isFoundInStore(),shoppingListDTO.isSuggestion());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            //System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for checking existence in db
    public boolean exist(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            logger.info("Is {} already in the shopping list to {}", shoppingListDTO.getName(), account.getUsername());
            return shoppingListRepository.groceryExist(account.getAccount_id(), shoppingListDTO.getName());
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
           // System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for deleting from db
    public boolean delete(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.removeByAccountEntityIdAndGroceryEntityId(account.getAccount_id(), grocery.getGrocery_id());
            logger.info("Deleting {} from {}s shopping list", shoppingListDTO.getName(), account.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            //System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for finding correct grocery based on name
    public GroceryEntity findGrocery(ShoppingListDTO shoppingListDTO) {
        try {
            logger.info("Finding {} in the shopping list", shoppingListDTO.getName());
            return groceryRepository.findGroceryEntitiesByNameIgnoreCase(shoppingListDTO.getName());
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            //System.out.println(e.getMessage());
            return null;
        }
    }

    // Logic for getting old count
    public int getOldCount(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        logger.info("Getting the old count for {} in {}s shopping list", shoppingListDTO.getName(), account.getUsername());
        return shoppingListRepository.getOldCount(account, findGrocery(shoppingListDTO));
    }

    // Logic for getting old foundInStore
    public boolean getOldFoundInStore(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        logger.info("Getting the old foundInStore for {} in {}s shopping list", shoppingListDTO.getName(), account.getUsername());
        return shoppingListRepository.getOldFoundInStore(account, findGrocery(shoppingListDTO));
    }

    public boolean getOldSuggestion(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        logger.info("Getting the old suggestion for {} in {}s shopping list", shoppingListDTO.getName(), account.getUsername());
        return shoppingListRepository.getOldSuggestion(account, findGrocery(shoppingListDTO));
    }

    // Logic for saving changes in frontend to db
    public boolean save(AccountEntity account, List<ShoppingListDTO> listOfDTOs) {
        for (ShoppingListDTO shoppingListDTO : listOfDTOs) {

            // Check if given grocery is actually a grocery
            if (findGrocery(shoppingListDTO) == null) {
                logger.info("Grocery {} does not exist in database.", shoppingListDTO.getName());
               // System.out.println("Grocery " + shoppingListDTO.getName() + " does not exist in database.");
                continue;
            }

            // If given grocery is already present in shopping list (potential update)
            if (exist(account, shoppingListDTO)) {

                // If new count is different from old count
                if ((getOldCount(account, shoppingListDTO) != shoppingListDTO.getCount())) {

                    // If count is 0 (deletion)
                    if (shoppingListDTO.getCount() == 0) {
                        delete(account, shoppingListDTO);
                        logger.info("Grocery {} has count 0, so it was deleted.", shoppingListDTO.getName());
                        //System.out.println("Grocery " + shoppingListDTO.getName() + " has count 0, so it was deleted.");
                        continue;
                    } else {
                        updateCount(account, shoppingListDTO);
                        logger.info("Grocery {} had new count, so it was updated.", shoppingListDTO.getName() );
                        //System.out.println("Grocery " + shoppingListDTO.getName() + " had new count, so it was updated.");
                    }
                }

                // If new foundInStore is different from old foundInStore
                if (getOldFoundInStore(account, shoppingListDTO) != shoppingListDTO.isFoundInStore()) {
                    updateShoppingListEntity(account, shoppingListDTO);
                    logger.info("Grocery {} had new foundInStore, so it was updated.", shoppingListDTO.getName());
                    //System.out.println("Grocery " + shoppingListDTO.getName() + " had new foundInStore, so it was updated.");
                }

                if (getOldSuggestion(account, shoppingListDTO) != shoppingListDTO.isSuggestion()) {
                    updateShoppingListEntity(account, shoppingListDTO);
                    logger.info("Grocery {} had new suggestion, so it was updated.", shoppingListDTO.getName());
                    //System.out.println("Grocery " + shoppingListDTO.getName() + " had new foundInStore, so it was updated.");
                }
            }

            // Else Given grocery is not present in shopping list (potential add)
            else {

                // Special case
                if (shoppingListDTO.getCount() == 0) {
                    logger.info("Grocery {} is not present is shopping list, but has count = 0, so it is ignored", shoppingListDTO.getName());
                    //System.out.println("Grocery " + shoppingListDTO.getName() + " is not present is shopping list, but " +
                      //      "has count = 0, so it is ignored");
                } else {
                    add(account, shoppingListDTO);
                    logger.info("Grocery {} is not present is database, is it was added.", shoppingListDTO.getName());
                   // System.out.println("Grocery " + shoppingListDTO.getName() + " is not present is database, is it was added.");
                }
            }
        }
        System.out.println("\n");
        //System.out.println("##############################################################################################################");
        return true;
    }

    public void buyMarkedGroceries(AccountEntity account) throws Exception {

        List<ShoppingListEntity> shoppingListEntityList = shoppingListRepository.findAllByAccountEntityAndFoundInStoreTrue(account);

        for (ShoppingListEntity shoppingListEntity : shoppingListEntityList) {
            FridgeGroceryBody fridgeGroceryBody = new FridgeGroceryBody();
            fridgeGroceryBody.setName(shoppingListEntity.getGroceryEntity().getName());
            fridgeGroceryBody.setCount(shoppingListEntity.getCount());
            fridgeGroceryBody.setCategoryId(shoppingListEntity.getGroceryEntity().getCategory().getCategory_id());
            try {
                fridgeService.addGroceryToAccount(shoppingListEntity.getAccountEntity(), fridgeGroceryBody);
            } catch (AccountDoesntExistException e) {
                throw new Exception();
            } catch (AccountAlreadyHasGroceryException e) {
                fridgeService.updateGroceryCount(shoppingListEntity.getAccountEntity(), fridgeGroceryBody);
            }
        }
        shoppingListRepository.removeAllByAccountEntityAndFoundInStoreTrue(account);
        logger.info("Adding groceries to {}s fridge and removing them fro the shopping list", account.getUsername());
    }
    //TODO asdasdasdas
    public List<String> getCorrectGroceriesFromRecipes(List<RecipeEntity> recipeEntities) {
        HashSet<String> allGroceries = groceryRepository.findAll().stream().map(GroceryEntity::getName).map(String::toLowerCase).collect(Collectors.toCollection(HashSet::new));
        ArrayList<String> groceriesToAdd = new ArrayList<>();
        ingredientsListToIngredients(recipeEntities.stream().map(RecipeEntity::getIngredients)
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
