package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.dto.TotalWastePerDateDTO;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WasteService {

    @Autowired
    private WasteRepository wasteRepository;

    public int getMoneyLost(long id) {
        return wasteRepository.getMoneyLost(id).orElse(0);
    }

    public List<List> getMoneyLostPerCategory(long id) {
        return wasteRepository.getMoneyLostPerCategory(id);
    }

    public int getMoneyLostByCategory(long id, long categoryId) {
        return wasteRepository.getMoneyLostByCategory(id, categoryId).orElse(0);
    }


    public List<TotalWastePerDateDTO> getTotalWastePerDateByMonth(AccountEntity account, int month) {
        List<TotalWastePerDateDTO> list = new ArrayList<>();
        for (List<Object> row : wasteRepository.getTotalWastePerDateByMonth(account.getAccount_id(), month)) {
            list.add(new TotalWastePerDateDTO((Date) row.get(0), (Double) row.get(1), (Double) row.get(2)));
        }
        return list;
    }
}
