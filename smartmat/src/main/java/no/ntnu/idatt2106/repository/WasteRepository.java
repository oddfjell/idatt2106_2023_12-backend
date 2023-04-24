package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface WasteRepository extends JpaRepository<WasteEntity, Long> {

    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id")
    Optional<Integer> getMoneyLost(long id);

    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id AND e.month = :monthNumber")
    Optional<Integer> getMoneyLostPerMonth(long id, int monthNumber);

    @Query("SELECT g.category.id, SUM(w.money_lost) AS money_lost from GroceryEntity g " +
            "INNER JOIN WasteEntity  w " +
            "ON g.id = w.groceryEntity.id " +
            "WHERE w.accountEntity.id = :id " +
            "GROUP BY g.category.id " +
            "ORDER BY money_lost DESC")
    List<List> getMoneyLostPerCategory(long id);

    @Query("SELECT SUM(w.money_lost) FROM GroceryEntity g " +
            "INNER JOIN WasteEntity w ON g.id = w.groceryEntity.id " +
            "WHERE w.accountEntity.id = :id AND g.category.id = :categoryId")
    Optional<Integer> getMoneyLostByCategory(long id, long categoryId);


}