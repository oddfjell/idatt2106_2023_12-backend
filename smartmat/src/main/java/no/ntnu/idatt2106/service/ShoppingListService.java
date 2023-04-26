package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.dto.ShoppingListDTO;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.AccountRepository;

import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;

import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

            // Finds correct grocery
            GroceryEntity grocery = groceryRepository.findGroceryEntitiesByNameIgnoreCase(shoppingListDTO.getName());
            if (grocery == null) {
                System.out.println("Grocery " + shoppingListDTO.getName() + " does not exist in database.");
                return false;
            }

            // Checks if grocery is already added to shopping list
            boolean groceryAlreadyExistOnShoppingList = shoppingListRepository.groceryExist(id, shoppingListDTO.getName());
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


    public boolean removeFromShoppingList(long id, ShoppingListDTO shoppingListDTO){

        try {

            // Finds correct user
            AccountEntity account = accountRepository.findById(id);

            // Finds correct grocery
            GroceryEntity grocery = groceryRepository.findGroceryEntitiesByNameIgnoreCase(shoppingListDTO.getName());
            if (grocery == null) {
                System.out.println("Grocery " + shoppingListDTO.getName() + " does not exist in database.");
                return false;
            }

            // Checks if grocery exists in shopping list
            boolean groceryExist = shoppingListRepository.groceryExist(id, shoppingListDTO.getName());
            if (!groceryExist) {
                System.out.println("User " + account.getUsername() + " does not have " + grocery.getName() + " in its shopping list.");
                return false;
            }

            shoppingListRepository.removeByAccountEntityIdAndGroceryEntityId(account.getAccount_id(), grocery.getGrocery_id());
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
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

}
