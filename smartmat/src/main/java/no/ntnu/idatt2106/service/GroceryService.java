package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.GroceryAlreadyExistsException;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void addGrocery(GroceryEntity grocery) throws GroceryAlreadyExistsException {
        if(groceryRepository.findByNameIgnoreCase(grocery.getName()).isPresent()){
            throw new GroceryAlreadyExistsException();
        }

        groceryRepository.save(grocery);
    }

    public void removeGrocery(GroceryEntity grocery){
        groceryRepository.removeById(grocery.getGrocery_id());
    }



}
