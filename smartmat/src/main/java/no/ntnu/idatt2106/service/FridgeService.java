package no.ntnu.idatt2106.service;


import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.UserDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FridgeService {

    @Autowired
    FridgeRepository fridgeRepository;
    @Autowired
    GroceryRepository groceryRepository;
    @Autowired
    AccountRepository accountRepository;

    public List<GroceryEntity> getAllGroceriesByAccount(AccountEntity account){
        List<GroceryEntity> groceryEntityList = new ArrayList<>();

        List<FridgeEntity> fridgeEntityList = fridgeRepository.findAllByAccountEntity(account);

        fridgeEntityList.forEach(fridgeEntity -> {
            GroceryEntity groceryEntity = groceryRepository.findById(fridgeEntity.getGroceryEntity().getGrocery_id()).orElse(null);
            groceryEntityList.add(groceryEntity);
        });

        return groceryEntityList;

    }

    public void addGroceryToAccount(AccountEntity account, GroceryEntity grocery, int count) throws UserDoesntExistException {

        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isEmpty()){
            throw new UserDoesntExistException();
        }

        FridgeEntity fridgeEntity = new FridgeEntity();
        fridgeEntity.setAccountEntity(account);
        fridgeEntity.setGroceryEntity(grocery);
        fridgeEntity.setCount(count);

        fridgeRepository.save(fridgeEntity);
    }

    public void removeGroceryFromAccount(AccountEntity account, GroceryEntity grocery){

    }

    public void removeGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){

    }

    public void throwGroceryFromAccount(AccountEntity account, GroceryEntity grocery){

    }

    public void throwGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){

    }









}
