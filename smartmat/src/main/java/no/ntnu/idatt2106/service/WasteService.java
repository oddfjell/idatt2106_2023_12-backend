package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.dto.TotalWastePerDateDTO;
import no.ntnu.idatt2106.dto.WastePerCategoryDTO;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.repository.WasteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for waste statistics related requests
 * WasteService contains methods that gets, changes or adds wasted money
 */
@Service
public class WasteService {

    /**
     * WasteRepository field injection
     */
    @Autowired
    private WasteRepository wasteRepository;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(WasteService.class);

    /**
     * Returns money lost by an account
     * @param id long
     * @return int
     */
    public int getMoneyLost(long id) {
        logger.info("Returning money lost");
        return wasteRepository.getMoneyLost(id).orElse(0);
    }

    /**
     * Returns money lost by account sorted in a certain category
     * @param id long
     * @return List<WastePerCategoryDTO>
     */
    public List<WastePerCategoryDTO> getMoneyLostPerCategory(long id) {
        return wasteRepository.getMoneyLostPerCategory(id);
    }

    /**
     * Returns money lost by account sorted in category's
     * @param id long
     * @param categoryId long
     * @return int
     */
    public int getMoneyLostByCategory(long id, long categoryId) {
        logger.info("Returning money lost by the category {}", categoryId);
        return wasteRepository.getMoneyLostByCategory(id, categoryId).orElse(0);
    }

    /**
     * Returns money lost by an account in current month
     * @param account AccountEntity
     * @param month int
     * @return List<TotalWastePerDateDTO>
     */
    public List<TotalWastePerDateDTO> getTotalWastePerDateByMonth(AccountEntity account, int month) {
        List<TotalWastePerDateDTO> list = new ArrayList<>();
        for (List<Object> row : wasteRepository.getTotalWastePerDateByMonth(account.getAccount_id(), month)) {
            list.add(new TotalWastePerDateDTO((Date) row.get(0), (Double) row.get(1), (Double) row.get(2)));
        }
        logger.info("Returning list of total waste per month");
        return list;
    }
}
