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


    public List<ShoppingListDTO> getShoppingList(long id) {
        return shoppingListRepository.getShoppingList(id);
    }

    // Logic for adding to db
    public boolean add(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            ShoppingListEntity groceryToBeAdded = new ShoppingListEntity(account, grocery, shoppingListDTO.getCount(), shoppingListDTO.isFoundInStore());
            shoppingListRepository.save(groceryToBeAdded);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    // Logic for updating count
    public boolean updateCount(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.updateCount(account, grocery, shoppingListDTO.getCount());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for updating foundInStore
    public boolean updateFoundInStore(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.updateFoundInStore(shoppingListDTO.isFoundInStore(), account, grocery);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for checking existence in db
    public boolean exist(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            return shoppingListRepository.groceryExist(account.getAccount_id(), shoppingListDTO.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for deleting from db
    public boolean delete(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        try {
            GroceryEntity grocery = findGrocery(shoppingListDTO);
            shoppingListRepository.removeByAccountEntityIdAndGroceryEntityId(account.getAccount_id(), grocery.getGrocery_id());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Logic for finding correct grocery based on name
    public GroceryEntity findGrocery(ShoppingListDTO shoppingListDTO) {
        try {
            return groceryRepository.findGroceryEntitiesByNameIgnoreCase(shoppingListDTO.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Logic for getting old count
    public int getOldCount(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        return shoppingListRepository.getOldCount(account, findGrocery(shoppingListDTO));
    }

    // Logic for getting old foundInStore
    public boolean getOldFoundInStore(AccountEntity account, ShoppingListDTO shoppingListDTO) {
        return shoppingListRepository.getOldFoundInStore(account, findGrocery(shoppingListDTO));
    }

    // Logic for saving changes in frontend to db
    public boolean save(AccountEntity account, List<ShoppingListDTO> listOfDTOs) {
        for (ShoppingListDTO shoppingListDTO : listOfDTOs) {

            // Check if given grocery is actually a grocery
            if (findGrocery(shoppingListDTO) == null) {
                System.out.println("Grocery " + shoppingListDTO.getName() + " does not exist in database.");
                continue;
            }

            // If given grocery is already present in shopping list (potential update)
            if (exist(account, shoppingListDTO)) {

                // If new count is different from old count
                if ((getOldCount(account, shoppingListDTO) != shoppingListDTO.getCount())) {

                    // If count is 0 (deletion)
                    if (shoppingListDTO.getCount() == 0) {
                        delete(account, shoppingListDTO);
                        System.out.println("Grocery " + shoppingListDTO.getName() + " has count 0, so it was deleted.");
                        continue;
                    } else {
                        updateCount(account, shoppingListDTO);
                        System.out.println("Grocery " + shoppingListDTO.getName() + " had new count, so it was updated.");
                    }
                }

                // If new foundInStore is different from old foundInStore
                if (getOldFoundInStore(account, shoppingListDTO) != shoppingListDTO.isFoundInStore()) {
                    updateFoundInStore(account, shoppingListDTO);
                    System.out.println("Grocery " + shoppingListDTO.getName() + " had new foundInStore, so it was updated.");
                }
            }

            // Else Given grocery is not present in shopping list (potential add)
            else {

                // Special case
                if (shoppingListDTO.getCount() == 0) {
                    System.out.println("Grocery " + shoppingListDTO.getName() + " is not present is shopping list, but " +
                            "has count = 0, so it is ignored");
                } else {
                    add(account, shoppingListDTO);
                    System.out.println("Grocery " + shoppingListDTO.getName() + " is not present is database, is it was added.");
                }
            }
        }
        System.out.println("##############################################################################################################");
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
    }
}
