package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface WasteRepository extends JpaRepository<WasteEntity, Long> {

    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id")
    Optional<Integer> getMoneyLost(long id);

    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id AND e.month = :monthNumber")
    Optional<Integer> getMoneyLostPerMonth(long id, int monthNumber);

    @Query("SELECT SUM(w.money_lost) FROM GroceryEntity g " +
            "INNER JOIN WasteEntity w ON g.id = w.groceryEntity.id " +
            "WHERE w.accountEntity.id = :id AND g.category.id = :categoryId")
    Optional<Integer> getMoneyLostByCategory(long id, long categoryId);
}
