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

/**
 * Service class for shoppingList related requests
 * ShoppingListService contains methods that gets, changes, adds or deletes accounts
 */
@Service
@Transactional
public class ShoppingListService {

    /**
     * ShoppingListRepository field injection
     */
    @Autowired
    private ShoppingListRepository shoppingListRepository;
    /**
     * AccountRepository field injection
     */
    @Autowired
    private AccountRepository accountRepository;
    /**
     * GroceryRepository field injection
     */
    @Autowired
    private GroceryRepository groceryRepository;
    /**
     * FridgeService field injection
     */
    @Autowired
    private FridgeService fridgeService;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ShoppingListService.class);

    /**
     * Method to get all the shoppingList items from the database
     * @param id long
     * @return List<ShoppingListDTO>
     */
    public List<ShoppingListDTO> getShoppingList(long id) {
        logger.info("Returning all of the things in the shopping list");
        return shoppingListRepository.getShoppingList(id);
    }

    /**
     * Method to add a grocery to the shoppingList in the database
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return boolean
     */
    public boolean add(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            ShoppingListEntity groceryToBeAdded = new ShoppingListEntity(account, grocery, shoppingListDTO.getCount(), shoppingListDTO.isFoundInStore(), shoppingListDTO.isSuggestion());
            shoppingListRepository.save(groceryToBeAdded);
            logger.info("Adding {} to {}s shopping list", shoppingListDTO.getName(), account.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Method to update the count of a grocery in the shoppingList in the database
     * @param account
     * @param shoppingListDTO
     * @return
     */
    public boolean updateCount(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.updateCount(account, grocery, shoppingListDTO.getCount());
            logger.info("Updating count of {} to {} in {}s shopping list", grocery.getName(), shoppingListDTO.getCount(), account.getUsername());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Method to update the boolean foundInStore in the products
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return boolean
     */
    public boolean updateShoppingListEntity(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.updateFoundInStore(shoppingListDTO.isFoundInStore(), account, grocery);
            shoppingListRepository.updateSuggestion(shoppingListDTO.isSuggestion(), account, grocery);
            logger.info("{}s foundInSTore and suggestion value in {}s list is now {} and {}", grocery.getName(), account.getUsername(), shoppingListDTO.isFoundInStore(),shoppingListDTO.isSuggestion());
            return true;
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Method to check if given grocery is already present in shopping list
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return boolean
     */
    public boolean exist(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            logger.info("Is {} already in the shopping list to {}", shoppingListDTO.getName(), account.getUsername());
            return shoppingListRepository.groceryExist(account.getAccount_id(), shoppingListDTO.getName());
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Method to delete a grocery from a shoppingList
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return boolean
     */
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


    /**
     * Method for finding correct grocery based on name in the ShoppingList
     * @param shoppingListDTO ShoppingListDTO
     * @return GroceryEntity
     */
    public GroceryEntity findGrocery(ShoppingListDTO shoppingListDTO) {
        try {
            logger.info("Finding {} in the shopping list", shoppingListDTO.getName());
            return groceryRepository.findGroceryEntitiesByNameIgnoreCase(shoppingListDTO.getName());
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Method for getting old count to see if it is different from the new
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return int
     */
    public int getOldCount(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        logger.info("Getting the old count for {} in {}s shopping list", shoppingListDTO.getName(), account.getUsername());
        return shoppingListRepository.getOldCount(account, findGrocery(shoppingListDTO));
    }

    /**
     * Method for getting old foundInStore to see if it is different from the new
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return boolean
     */
    public boolean getOldFoundInStore(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        logger.info("Getting the old foundInStore for {} in {}s shopping list", shoppingListDTO.getName(), account.getUsername());
        return shoppingListRepository.getOldFoundInStore(account, findGrocery(shoppingListDTO));
    }

    /**
     * Method for getting old suggestion to see if it is different from the new
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     * @return boolean
     */
    public boolean getOldSuggestion(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        logger.info("Getting the old suggestion for {} in {}s shopping list", shoppingListDTO.getName(), account.getUsername());
        return shoppingListRepository.getOldSuggestion(account, findGrocery(shoppingListDTO));
    }

    // Logic for saving changes in frontend to db

    /**
     * Method for saving changes from the frontend to the database
     * @param account AccountEntity
     * @param listOfDTOs List<ShoppingListDTO>
     * @return boolean
     */
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
        return true;
    }

    /**
     * Method for moving (buying) grocery's from the shoppingList to the fridge
     * @param account AccountEntity
     * @throws Exception Exception
     */
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

    /**
     * Method to get the grocery's needed for a certain recipe and adding them to the shoppingList
     * @param recipeEntities List<RecipeEntity>
     * @return List<String>
     */
    public List<String> getCorrectGroceriesFromRecipes(List<RecipeEntity> recipeEntities) {
        HashSet<String> allGroceries = groceryRepository.findAll().stream().map(GroceryEntity::getName).map(String::toLowerCase).collect(Collectors.toCollection(HashSet::new));
        ArrayList<String> groceriesToAdd = new ArrayList<>();
        ingredientsListToIngredients(recipeEntities.stream().map(RecipeEntity::getIngredients)
                .flatMap(Arrays::stream).toList())
                .forEach(i -> groceriesToAdd.add(ingredientToGrocery(i,allGroceries)));

        return groceriesToAdd;
    }

    /**
     * Method to get ingredients from a recipe in a list
     * @param ingredients List<String>
     * @return List<String>
     */
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

    /**
     * Method to find the correct grocery`s that is in the ingredients list
     * @param ingredient String
     * @param allGroceries Set<String>
     * @return String
     */
    public String ingredientToGrocery(String ingredient, Set<String> allGroceries) {
            for (String t: ingredient.replace("\"", "").split(" ")) {
                if (allGroceries.contains(t)) {
                    return t;
                }
            }

        return ingredient;
    }

    /**
     * Method to move a suggestion to the shoppingList
     * @param account AccountEntity
     * @param shoppingListDTO ShoppingListDTO
     */
    public void moveSuggestionToList(AccountEntity account, ShoppingListDTO shoppingListDTO){
        Optional<ShoppingListEntity> shoppingListEntityOptional = shoppingListRepository.findByAccountEntityAndGroceryEntityName(account,shoppingListDTO.getName());

        if(shoppingListEntityOptional.isPresent()){
            ShoppingListEntity shoppingListEntity = shoppingListEntityOptional.get();
            shoppingListRepository.updateSuggestion(false,shoppingListEntity.getAccountEntity(),shoppingListEntity.getGroceryEntity());
        }
    }

    /**
     * Method to get all the suggestions in an account
     * @param account AccountEntity
     * @return List<ShoppingListEntity>
     */
    public List<ShoppingListEntity> getSuggestionsByAccount(AccountEntity account){
        return shoppingListRepository.findAllBySuggestionTrueAndAccountEntity(account);
    }
}
