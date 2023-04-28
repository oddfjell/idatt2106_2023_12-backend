package no.ntnu.idatt2106.service;


import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.AccountAlreadyHasGroceryException;
import no.ntnu.idatt2106.exceptions.AccountDoesntExistException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.WasteEntity;
import no.ntnu.idatt2106.model.api.FridgeGroceryBody;
import no.ntnu.idatt2106.model.api.FridgeGroceryThrowBody;
import no.ntnu.idatt2106.model.api.FridgeResponseBody;
import no.ntnu.idatt2106.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    @Autowired
    WasteRepository wasteRepository;

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
            fridgeResponseBody.setExpiresInDays(groceryEntity.getExpiryDate() - (int) ChronoUnit.DAYS.between(fridgeEntity.getDate(),LocalDate.now()));
            fridgeResponseBodyList.add(fridgeResponseBody);
        });

        fridgeResponseBodyList.sort(Comparator.comparing(FridgeResponseBody::getExpiresInDays));

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

    public void removeGroceryFromAccountByAmount(AccountEntity account, FridgeGroceryBody fridgeGroceryBody) throws Exception {
        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),fridgeGroceryBody.getName());
        if(fridgeEntity.isEmpty() || fridgeEntity.get().getCount() < fridgeGroceryBody.getCount()){
            throw new Exception();
        }

        if(fridgeEntity.get().getCount() - fridgeGroceryBody.getCount() < 1){
            fridgeRepository.deleteByAccountEntityAndGroceryEntity(fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
        }else{
            fridgeRepository.updateCount(fridgeEntity.get().getCount() - fridgeGroceryBody.getCount(),fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
        }
    }

    public void throwGroceryFromAccount(AccountEntity account, GroceryEntity grocery){

    }

    public void throwGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){

    }

    private final int tempValue = 100;
    public void throwGroceryFromFridgeToWaste(AccountEntity account, FridgeGroceryThrowBody fridgeGroceryThrowBody) throws Exception {//FridgeEntity product

        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),fridgeGroceryThrowBody.getName());
        if(fridgeEntity.isEmpty()){
            throw new Exception();
        }

        if(fridgeGroceryThrowBody.getPercent() == 0){
            FridgeGroceryBody fridgeGroceryBody = new FridgeGroceryBody(fridgeGroceryThrowBody.getName(), fridgeGroceryThrowBody.getCategoryId(), 1);
            removeGroceryFromAccountByAmount(account, fridgeGroceryBody);
        } else{
            double money = tempValue * fridgeGroceryThrowBody.getPercent();

            if(wasteRepository.findWasteEntitiesByGroceryEntity(fridgeEntity.get().getGroceryEntity()).isPresent()){
                //UPDATE MONEY LOST    plusse på
                //TODO JONAS
                // WÆÆÆ
            } else{
                //TODO JONAS
                // wasteRepository.save(new WasteEntity(account, fridgeEntity.get().getGroceryEntity(), money, java.time.LocalDateTime.now()))
            }
        }
    }
}
