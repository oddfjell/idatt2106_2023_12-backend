package no.ntnu.idatt2106.service;


import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;
import no.ntnu.idatt2106.model.api.FridgeResponseBody;
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<FridgeResponseBody> getAllGroceriesByAccount(AccountEntity account){
        List<FridgeResponseBody> fridgeResponseBodyList = new ArrayList<>();

        List<FridgeEntity> fridgeEntityList = fridgeRepository.findAllByAccountEntity(account);

        fridgeEntityList.forEach(fridgeEntity -> {
            GroceryEntity groceryEntity = groceryRepository.findById(fridgeEntity.getGroceryEntity().getGrocery_id()).orElse(null);
            FridgeResponseBody fridgeResponseBody = new FridgeResponseBody();
            fridgeResponseBody.setName(groceryEntity.getName());
            fridgeResponseBody.setCount(fridgeEntity.getCount());
            fridgeResponseBody.setCategoryName(groceryEntity.getCategory().getName());
            fridgeResponseBody.setCategoryImage(groceryEntity.getCategory().getImage());
            fridgeResponseBodyList.add(fridgeResponseBody);
        });

        return fridgeResponseBodyList;

    }

    public void addGroceryToAccount(AccountEntity account, FridgeGroceryBody fridgeGroceryBody) throws AccountDoesntExistException, AccountAlreadyHasGroceryException {

        Optional<GroceryEntity> groceryEntityOptional = groceryRepository.findByNameIgnoreCase(fridgeGroceryBody.getName());

        if(groceryEntityOptional.isEmpty()){
            throw new IllegalArgumentException();
        }

        GroceryEntity grocery = groceryEntityOptional.get();

        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isEmpty()){
            throw new AccountDoesntExistException();
        }

        Optional<FridgeEntity> optionalFridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),grocery.getName());

        if(optionalFridgeEntity.isPresent()){
            System.out.println("Exception thrown");
            throw new AccountAlreadyHasGroceryException();
        }

        FridgeEntity fridgeEntity = new FridgeEntity();
        fridgeEntity.setAccountEntity(account);
        fridgeEntity.setGroceryEntity(grocery);
        fridgeEntity.setCount(fridgeGroceryBody.getCount());
        fridgeEntity.setDate(LocalDate.now());

        fridgeRepository.save(fridgeEntity);
    }

    public void updateGroceryCount(AccountEntity account, FridgeGroceryBody accountBody){

        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),accountBody.getName());
        if(fridgeEntity.isEmpty()){
            throw new IllegalArgumentException();
        }

        fridgeRepository.updateCount(accountBody.getCount() + fridgeEntity.get().getCount(),fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
    }

    public void removeGroceryFromAccount(AccountEntity account, GroceryEntity grocery){
        fridgeRepository.removeByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),grocery.getName());
    }

    public void removeGroceryFromAccountByAmount(AccountEntity account, FridgeGroceryBody accountBody) throws Exception {
        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),accountBody.getName());
        if(fridgeEntity.isEmpty() || fridgeEntity.get().getCount() < accountBody.getCount()){
            throw new Exception();
        }

        fridgeRepository.updateCount(fridgeEntity.get().getCount() - accountBody.getCount(),fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
    }

    public void throwGroceryFromAccount(AccountEntity account, GroceryEntity grocery){

    }

    public void throwGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){

    }









}
