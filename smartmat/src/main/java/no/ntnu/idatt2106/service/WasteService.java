package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WasteService {

    @Autowired
    private WasteRepository wasteRepository;

    public int getMoneyLost(long id) {
        return wasteRepository.getMoneyLost(id);
    }
}
