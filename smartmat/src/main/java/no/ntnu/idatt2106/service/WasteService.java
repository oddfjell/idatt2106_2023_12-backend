package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WasteService {

    @Autowired
    private WasteRepository wasteRepository;

    public int getMoneyLost(long id) {
        return wasteRepository.getMoneyLost(id).orElse(0);
    }

    public int getMoneyLostByCategory(long id, long categoryId) {
        return wasteRepository.getMoneyLostByCategory(id, categoryId).orElse(0);
    }

    public List<List> getMoneyLostPerCategory(long id) {
        return wasteRepository.getMoneyLostPerCategory(id);
    }

    public int getMoneyLostPerMonth(long id, int monthNumber) {
        return wasteRepository.getMoneyLostPerMonth(id, monthNumber).orElse(0);
    }


}
