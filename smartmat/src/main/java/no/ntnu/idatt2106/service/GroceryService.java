package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.GroceryAlreadyExistsException;
import no.ntnu.idatt2106.model.*;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.GroceryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class GroceryService {

    @Autowired
    private GroceryRepository groceryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(GroceryService.class);


    public List<GroceryEntity> getAllGroceries(){
        logger.info("Returning all the groceries");
        return groceryRepository.findAll();
    }

    public List<GroceryEntity> getAllGroceriesByCategoryId(Long id){
        logger.info("Returning all the groceries by category id {}", id);
        return groceryRepository.findAllByCategoryId(id);
    }

    public void addGrocery(GroceryEntity grocery) throws GroceryAlreadyExistsException {
        if(groceryRepository.findByNameIgnoreCase(grocery.getName()).isPresent()){
            logger.info("{} already exists", grocery.getName());
            throw new GroceryAlreadyExistsException();
        }
        logger.info("Added {} to the groceries", grocery.getName());
        groceryRepository.save(grocery);
    }

    //TODO logg ja
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
        logger.info("Grocery size is {}", groceries.size());
        //System.err.println("HALLLOOOOO "+groceries.size());
    }

    public void removeGrocery(GroceryEntity grocery){
        logger.info("Removing {} from groceries", grocery.getName());
        groceryRepository.removeById(grocery.getGrocery_id());
    }
}
