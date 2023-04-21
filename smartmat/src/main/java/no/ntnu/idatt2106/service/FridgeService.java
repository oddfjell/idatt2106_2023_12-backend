package no.ntnu.idatt2106.service;


import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
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

    public List<GroceryEntity> getAllGroceriesByAccount(AccountEntity account){
        List<GroceryEntity> groceryEntityList = new ArrayList<>();

        List<FridgeEntity> fridgeEntityList = fridgeRepository.findAllByAccountEntity(account);

        fridgeEntityList.forEach(fridgeEntity -> {
            GroceryEntity groceryEntity = groceryRepository.findById(fridgeEntity.getGroceryEntity().getGrocery_id()).orElse(null);
            groceryEntityList.add(groceryEntity);
        });

        return groceryEntityList;

    }





}
