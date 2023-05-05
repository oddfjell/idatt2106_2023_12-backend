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
import no.ntnu.idatt2106.repository.AccountRepository;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Service class for FridgeController
 * FridgeService contains methods that gets, changes, adds or deletes ......
 */
@Service
@Transactional
public class FridgeService {

    /**
     * FridgeRepository field injection
     */
    @Autowired
    FridgeRepository fridgeRepository;
    /**
     * GroceryRepository field injection
     */
    @Autowired
    GroceryRepository groceryRepository;
    /**
     * AccountRepository field injection
     */
    @Autowired
    AccountRepository accountRepository;
    /**
     * CategoryRepository field injection
     */
    @Autowired
    CategoryRepository categoryRepository;
    /**
     * WasteRepository field injection
     */
    @Autowired
    WasteRepository wasteRepository;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(FridgeService.class);

    /**
     * Method that returns all the grocery's owned by a certain account
     * @param account AccountEntity
     * @return List<FridgeResponseBody>
     */
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

    /**
     * Method to add a grocery to the fridge. It checks if the grocery does already exist in the fridge. It will make
     * a new grocery object in the fridge if it does not already exist in the fridge or is empty
     * @param account AccountEntity
     * @param fridgeGroceryBody FridgeGroceryBody
     * @throws AccountDoesntExistException AccountDoesntExistException
     * @throws AccountAlreadyHasGroceryException AccountAlreadyHasGroceryException
     */
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

    /**
     * Method to update the grocery count. It will increment the amount column in the database for the grocery
     * @param account AccountEntity
     * @param accountBody FridgeGroceryBody
     */
    public void updateGroceryCount(AccountEntity account, FridgeGroceryBody accountBody){
        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),accountBody.getName());
        if(fridgeEntity.isEmpty()){
            logger.info("{} is not in the fridge and thus cannot be updated", accountBody.getName());
            throw new IllegalArgumentException();
        }

        logger.info("Updating to {} {}s", accountBody.getCount(), accountBody.getName());
        fridgeRepository.updateCount(accountBody.getCount() + fridgeEntity.get().getCount(),fridgeEntity.get().getAccountEntity(), fridgeEntity.get().getGroceryEntity());
    }

    /**
     * Method to remove a grocery from the fridge when the amount reaches 0
     * @param account AccountEntity
     * @param grocery GroceryEntity
     */
    public void removeGroceryFromAccount(AccountEntity account, GroceryEntity grocery){
        logger.info("Removing {} from {}", grocery.getName(), account.getUsername());
        fridgeRepository.removeByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),grocery.getName());
    }

    /**
     * Method to update the grocery count. It will decrement the amount column in the database for the grocery
     * @param account AccountEntity
     * @param fridgeGroceryBody FridgeGroceryBody
     * @throws Exception Exception
     */
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

    /**
     * Method to remove the grocery from the fridge and to add the grocery to waste. It uses the percent thrown to give a
     * money_lost_ sum.
     * @param account AccountEntity
     * @param fridgeGroceryThrowBody FridgeGroceryThrowBody
     * @throws Exception Exception
     */
    public void throwGroceryFromFridgeToWaste(AccountEntity account, FridgeGroceryThrowBody fridgeGroceryThrowBody) throws Exception {//FridgeEntity product

        Optional<FridgeEntity> fridgeEntity = fridgeRepository.findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(account.getUsername(),fridgeGroceryThrowBody.getName());
        if(fridgeEntity.isEmpty()){
            logger.info("Fridge to {} does not contain {}", account.getUsername(), fridgeGroceryThrowBody.getName());
            throw new Exception();
        }

        if(fridgeGroceryThrowBody.getNewMoneyValue() != 0){
            double price = apiCall(fridgeEntity.get().getGroceryEntity().getName());
            double moneyLost = price * (fridgeGroceryThrowBody.getNewMoneyValue() / 100);

            if(wasteRepository.findWasteEntitiesByGroceryEntity(account, fridgeEntity.get().getGroceryEntity(), fridgeGroceryThrowBody.getThrowDate()).isPresent()){
                wasteRepository.updateMoneyLost(account, fridgeEntity.get().getGroceryEntity(), fridgeGroceryThrowBody.getThrowDate(), moneyLost);
                logger.info("Making a new WasteEntity for {}", fridgeGroceryThrowBody.getName());
            } else{
                wasteRepository.save(new WasteEntity(account, fridgeEntity.get().getGroceryEntity(), moneyLost, fridgeGroceryThrowBody.getThrowDate()));
                logger.info("Adding {} to the WasteEntity {}", moneyLost);
            }
        }
        FridgeGroceryBody fridgeGroceryBody = new FridgeGroceryBody(fridgeGroceryThrowBody.getName(), fridgeEntity.get().getGroceryEntity().getGrocery_id(), 1);
        removeGroceryFromAccountByAmount(account, fridgeGroceryBody);
        logger.info("Successfully thrown {}", fridgeGroceryThrowBody.getName());
    }

    /**
     * Method for the api call to kassalappAPI to get a money sum for the groceries.
     * It is creating a HttpClient instance and then building the request. Then it sends the request asynchronously
     * and retrieve the response when it's ready
     * @param grocery String
     * @return double
     * @throws ExecutionException ExecutionException
     * @throws InterruptedException InterruptedException
     */
    public double apiCall(String grocery) throws ExecutionException, InterruptedException {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://kassal.app/api/v1/products?size=1&search=" + grocery))
                    .header("Authorization", "Bearer jBpCRMx0JMUblSaXKG9syjISQK4aBk1dkE9DoPT5")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> response = futureResponse.get();

            JSONObject object = new JSONObject(response.body());
            JSONArray data = (JSONArray) object.get("data");
            JSONObject firstObject = (JSONObject) data.get(0);
            return (double) firstObject.get("current_price");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
