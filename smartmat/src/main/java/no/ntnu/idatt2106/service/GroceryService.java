package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.repository.FridgeRepository;
import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroceryService {

    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private WasteRepository wasteRepository;

    public boolean addProduct(FridgeEntity product){
        //TODO save må legge til et produkt og/eller øke amount
        // kan være forskjellige metoder
        int before;
        if(fridgeRepository.doContain(product)){
            before = fridgeRepository.getAmount(product.getFullName());
        } else before = 0;

        fridgeRepository.save(product);
        int after = fridgeRepository.getAmount(product.getFullName());

        return before == (after - 1);
    }

    public boolean removeProduct(FridgeEntity product){
        //TODO DO NOT DELETE IF THE PRODUCT AMOUNT IS MORE THAN 1
        int before = fridgeRepository.findAll().size();
        fridgeRepository.delete(product);
        int after = fridgeRepository.findAll().size();
        return before == (after + 1);
    }

    public boolean throwProduct(FridgeEntity product){
        //TODO DO NOT DELETE IF THE PRODUCT AMOUNT IS MORE THAN 1
        int beforeFridge = fridgeRepository.findAll().size();
        int beforeWaste = wasteRepository.findAll().size();

        fridgeRepository.throwProduct(product);

        int afterFridge = fridgeRepository.findAll().size();
        int afterWaste = wasteRepository.findAll().size();

        //TODO feilskjekk om den ene skjer og ikke den andre
        if(beforeFridge == afterFridge + 1 && beforeWaste == afterWaste + 1){
            return true;
        } else return false;
    }
}
