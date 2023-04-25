package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.ShoppingListEntity;
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
    private GroceryRepository groceryRepository;

    @Autowired
    private FridgeService fridgeService;

    public List<ShoppingListDTO> getShoppingList(long id){
        return shoppingListRepository.getShoppingList(id);
    }

    public boolean addToShoppingList(ShoppingListEntity product){
        //TODO save må legge til et produkt og/eller øke amount
        // kan være forskjellige metoder
        return false;
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

    public void updateFoundInStore(AccountEntity account, String groceryName){
        Optional<ShoppingListEntity> shoppingListEntityOptional = shoppingListRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(), groceryName);
        if(shoppingListEntityOptional.isEmpty()){
            throw new IllegalArgumentException();
        }

        Optional<GroceryEntity> groceryEntity = groceryRepository.findByNameIgnoreCase(groceryName);
        if(groceryEntity.isEmpty()){
            throw new IllegalArgumentException();
        }

        shoppingListRepository.updateFoundInStore(!shoppingListEntityOptional.get().isFoundInStore(),account,groceryEntity.get());

    }


    public void buyMarkedGroceries(AccountEntity account){

        List<ShoppingListEntity> shoppingListEntityList = shoppingListRepository.findAllByAccountEntityAndFoundInStoreTrue(account);

        shoppingListEntityList.forEach(shoppingListEntity -> {
            FridgeGroceryBody fridgeGroceryBody = new FridgeGroceryBody();
            fridgeGroceryBody.setName(shoppingListEntity.getGroceryEntity().getName());
            fridgeGroceryBody.setCount(shoppingListEntity.getCount());
            fridgeGroceryBody.setCategoryId(shoppingListEntity.getGroceryEntity().getCategory().getCategory_id());
            try {
                fridgeService.addGroceryToAccount(shoppingListEntity.getAccountEntity(),fridgeGroceryBody);
            } catch (AccountDoesntExistException e) {
                throw new IllegalArgumentException();
            } catch (AccountAlreadyHasGroceryException e) {
                fridgeService.updateGroceryCount(shoppingListEntity.getAccountEntity(), fridgeGroceryBody);
            }
        });
        shoppingListRepository.removeAllByAccountEntityAndFoundInStoreTrue(account);
    }

}
