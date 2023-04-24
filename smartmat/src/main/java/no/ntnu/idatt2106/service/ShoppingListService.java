package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShoppingListService {

    @Autowired
    private ShoppingListRepository shoppingListRepository;

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
}
