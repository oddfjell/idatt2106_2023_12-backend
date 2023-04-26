package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.GroceryAlreadyExistsException;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroceryService {

    @Autowired
    private GroceryRepository groceryRepository;

    @Autowired
    private CategoryRepository categoryRepository;


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

    public void populateDbGroceries() {
        ArrayList<GroceryEntity> groceries = new ArrayList<>();
        String path = "/Users/josteinlind/Skole/test/varer.txt";

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                    String[] read = line.split(",");
                    if (!line.equals(" ") && !line.equals("")) {
                        GroceryEntity grocery = new GroceryEntity();
                        grocery.setName(read[0]);
                        grocery.setCategory(categoryRepository.getCategoryEntityById(5L));
                        grocery.setExpiryDate(1);
                        if (!groceries.contains(grocery)) {
                            groceries.add(grocery);
                        }
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        groceries.forEach(g-> {
            try {
                this.addGrocery(g);
            } catch (GroceryAlreadyExistsException e) {
                System.err.println(g.getName());
            }
        });
        System.err.println("HALLLOOOOO "+groceries.size());
    }

    public void removeGrocery(GroceryEntity grocery){
        groceryRepository.removeById(grocery.getGrocery_id());
    }
}
