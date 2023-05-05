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

/**
 * Service class for grocery related requests
 * GroceryService contains methods that gets, changes, adds or deletes grocery's
 */
@Service
@Transactional
public class GroceryService {

    /**
     * GroceryRepository field injection
     */
    @Autowired
    private GroceryRepository groceryRepository;
    /**
     * CategoryRepository field injection
     */
    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(GroceryService.class);

    /**
     * Method that returns all the grocery's
     * @return List<GroceryEntity>
     */
    public List<GroceryEntity> getAllGroceries(){
        logger.info("Returning all the groceries");
        return groceryRepository.findAll();
    }

    /**
     * Method that returns all the grocery's from a certain category
     * @param id Long
     * @return List<GroceryEntity>
     */
    public List<GroceryEntity> getAllGroceriesByCategoryId(Long id){
        logger.info("Returning all the groceries by category id {}", id);
        return groceryRepository.findAllByCategoryId(id);
    }

    /**
     * Method that adds a new grocery to the database
     * @param grocery GroceryEntity
     * @throws GroceryAlreadyExistsException GroceryAlreadyExistsException
     */
    public void addGrocery(GroceryEntity grocery) throws GroceryAlreadyExistsException {
        if(groceryRepository.findByNameIgnoreCase(grocery.getName()).isPresent()){
            logger.info("{} already exists", grocery.getName());
            throw new GroceryAlreadyExistsException();
        }
        logger.info("Added {} to the groceries", grocery.getName());
        groceryRepository.save(grocery);
    }

    /**
     * Method that populates the database with grocery's retrieved from "matvaretabellen". A BufferedReader
     * reads a txt-file and sets the name, category and expire date. Then they are added to the database
     */
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
    }

    /**
     * Method to remove a grocery from the database
     * @param grocery GroceryEntity
     */
    public void removeGrocery(GroceryEntity grocery){
        logger.info("Removing {} from groceries", grocery.getName());
        groceryRepository.removeById(grocery.getGrocery_id());
    }
}
