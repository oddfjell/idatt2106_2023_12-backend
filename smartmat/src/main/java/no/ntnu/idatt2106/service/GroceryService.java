package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GroceryService {

    @Autowired
    private GroceryRepository groceryRepository;


    public List<GroceryEntity> getAllGroceries(){
        return groceryRepository.findAll();
    }

    public List<GroceryEntity> getAllGroceriesByCategoryId(Long id){
        return groceryRepository.findAllByCategoryId(id);
    }

}
