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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FridgeService.class);

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

        logger.info("Returning all of the groceries to {}", account.getUsername());
        return fridgeResponseBodyList;

    }

    public void addGroceryToAccount(AccountEntity account, FridgeGroceryBody fridgeGroceryBody) throws AccountDoesntExistException, AccountAlreadyHasGroceryException {

        Optional<GroceryEntity> groceryEntityOptional = groceryRepository.findByNameIgnoreCase(fridgeGroceryBody.getName());

        if(groceryEntityOptional.isEmpty()){
            logger.info("The grocery {} does not exist in the database", fridgeGroceryBody.getName());
            throw new IllegalArgumentException();
        }

        GroceryEntity grocery = groceryEntityOptional.get();

        if(accountRepository.findByUsernameIgnoreCase(account.getUsername()).isEmpty()){
            logger.info("The account {} does not exist", account.getUsername());
            throw new AccountDoesntExistException();
        }

        Optional<FridgeEntity> optionalFridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),grocery.getName());

        if(optionalFridgeEntity.isPresent()){
           // System.out.println("Exception thrown");
            logger.info("{} already exists in the fridge", optionalFridgeEntity.get().getGroceryEntity().getName());
            throw new AccountAlreadyHasGroceryException();
        }

        FridgeEntity fridgeEntity = new FridgeEntity();
        fridgeEntity.setAccountEntity(account);
        fridgeEntity.setGroceryEntity(grocery);
        fridgeEntity.setCount(fridgeGroceryBody.getCount());
        fridgeEntity.setDate(LocalDate.now());

        logger.info("Saving {} in fridge", fridgeEntity.getGroceryEntity().getName());
        fridgeRepository.save(fridgeEntity);
    }

    public void updateGroceryCount(AccountEntity account, FridgeGroceryBody accountBody){

        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),accountBody.getName());
        if(fridgeEntity.isEmpty()){
            logger.info("{} is not in the fridge and thus cannot be updated", accountBody.getName());
            throw new IllegalArgumentException();
        }

        logger.info("Updating to {} {}s", accountBody.getCount(), accountBody.getName());
        fridgeRepository.updateCount(accountBody.getCount() + fridgeEntity.get().getCount(),fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
    }

    public void removeGroceryFromAccount(AccountEntity account, GroceryEntity grocery){
        logger.info("Removing {} from {}", grocery.getName(), account.getUsername());
        fridgeRepository.removeByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),grocery.getName());
    }

    public void removeGroceryFromAccountByAmount(AccountEntity account, FridgeGroceryBody fridgeGroceryBody) throws Exception {
        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),fridgeGroceryBody.getName());
        if(fridgeEntity.isEmpty() || fridgeEntity.get().getCount() < fridgeGroceryBody.getCount()){
            logger.info("{} does not exist or has a smaller count than {}", fridgeGroceryBody.getName(), fridgeGroceryBody.getCount());
            throw new Exception();
        }

        if(fridgeEntity.get().getCount() - fridgeGroceryBody.getCount() < 1){
            logger.info("{} was deleted due to being set to 0 or less", fridgeGroceryBody.getName());
            fridgeRepository.deleteByAccountEntityAndGroceryEntity(fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
        }else{
            logger.info("Removing {} of the {}s", fridgeGroceryBody.getCount(), fridgeGroceryBody.getName());
            fridgeRepository.updateCount(fridgeEntity.get().getCount() - fridgeGroceryBody.getCount(),fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
        }
    }

    public void throwGroceryFromAccount(AccountEntity account, GroceryEntity grocery){

    }

    public void throwGroceryFromAccountByAmount(AccountEntity account, GroceryEntity grocery, int count){

    }

}
