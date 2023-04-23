package no.ntnu.idatt2106.service;


import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.AddGroceryToAccountBody;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FridgeService {

    @Autowired
    FridgeRepository fridgeRepository;
    @Autowired
    GroceryRepository groceryRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public List<GroceryEntity> getAllGroceriesByAccount(AccountEntity account){
        List<GroceryEntity> groceryEntityList = new ArrayList<>();

        List<FridgeEntity> fridgeEntityList = fridgeRepository.findAllByAccountEntity(account);

        fridgeEntityList.forEach(fridgeEntity -> {
            GroceryEntity groceryEntity = groceryRepository.findById(fridgeEntity.getGroceryEntity().getGrocery_id()).orElse(null);
            groceryEntityList.add(groceryEntity);
        });

        return groceryEntityList;

    }

    public void addGroceryToAccount(AccountEntity account, AddGroceryToAccountBody accountBody) throws AccountDoesntExistException, AccountAlreadyHasGroceryException {
        GroceryEntity groceryEntity = new GroceryEntity();
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(accountBody.getCategoryId());

        if(optionalCategoryEntity.isPresent()){
            groceryEntity.setCategory(optionalCategoryEntity.get());
        }else{
            groceryEntity.setCategory(null);
        }
        groceryEntity.setName(accountBody.getName());

        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isEmpty()){
            throw new AccountDoesntExistException();
        }

        Optional<FridgeEntity> optionalFridgeEntity = fridgeRepository.findByAccountEntityIdAndGroceryEntityId(account.getAccount_id(),groceryEntity.getGrocery_id());


        if(optionalFridgeEntity.isPresent()){
            throw new AccountAlreadyHasGroceryException();
        }

        FridgeEntity fridgeEntity = new FridgeEntity();
        fridgeEntity.setAccountEntity(account);
        fridgeEntity.setGroceryEntity(groceryEntity);
        fridgeEntity.setCount(accountBody.getCount());

        fridgeRepository.save(fridgeEntity);
    }

    public void updateGroceryCount(AccountEntity account, AddGroceryToAccountBody accountBody){

        GroceryEntity groceryEntity = new GroceryEntity();
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(accountBody.getCategoryId());
        if(optionalCategoryEntity.isPresent()){
            groceryEntity.setCategory(optionalCategoryEntity.get());
        }else{
            groceryEntity.setCategory(null);
        }
        groceryEntity.setName(accountBody.getName());



        fridgeRepository.updateCount(accountBody.getCount(),account,groceryEntity);
    }

    public void removeGroceryFromAccount(AccountEntity account, GroceryEntity grocery){
        Optional<FridgeEntity> optionalFridgeEntity = fridgeRepository.findByAccountEntityAndGroceryEntity(account,grocery);
        optionalFridgeEntity.ifPresent(fridgeEntity -> fridgeRepository.delete(fridgeEntity));
    }

    public void removeGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){
        Optional<FridgeEntity> optionalFridgeEntity = fridgeRepository.findByAccountEntityAndGroceryEntity(account,grocery);
        optionalFridgeEntity.ifPresent(fridgeEntity -> {
            if(count > fridgeEntity.getCount()){
                throw new IllegalArgumentException();
            }

            fridgeRepository.updateCount(fridgeEntity.getCount() - count, account, grocery);
        });
    }

    public void throwGroceryFromAccount(AccountEntity account, GroceryEntity grocery){

    }

    public void throwGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){

    }









}
