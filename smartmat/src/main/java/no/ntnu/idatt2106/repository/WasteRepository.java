package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WasteRepository extends JpaRepository<WasteEntity, Long> {

    // GET TOTAL WASTE FOR AN ACCOUNT
    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id")
    Optional<Integer> getMoneyLost(long id);

    // GET WASTE SORTED BY CATEGORY
    @Query("SELECT g.category.id, SUM(w.money_lost) AS money_lost from GroceryEntity g " +
            "INNER JOIN WasteEntity  w " +
            "ON g.id = w.groceryEntity.id " +
            "WHERE w.accountEntity.id = :id " +
            "GROUP BY g.category.id " +
            "ORDER BY money_lost DESC")
    List<List> getMoneyLostPerCategory(long id);

    // GET WASTE FOR A GIVEN CATEGORY
    @Query("SELECT SUM(w.money_lost) FROM GroceryEntity g " +
            "INNER JOIN WasteEntity w ON g.id = w.groceryEntity.id " +
            "WHERE w.accountEntity.id = :id AND g.category.id = :categoryId")
    Optional<Integer> getMoneyLostByCategory(long id, long categoryId);

    // Get total waste per date by month
    @Query(value = "SELECT date, money_lost, SUM(money_lost) OVER (ORDER BY money_lost) AS total  " +
            " FROM waste " +
            "WHERE account_id=?1 AND month(date)=?2", nativeQuery = true)
    List<List<Object>> getTotalWastePerDateByMonth(long id, int month);




    //TODO JONAS FIKS
    Optional<WasteEntity> findWasteEntitiesByGroceryEntity(AccountEntity account, GroceryEntity groceryEntity, java.sql.Date date);

    /*@Modifying
    @Query("UPDATE WasteEntity w SET w.money_lost = w.money_lost + :newValue WHERE w.id = :id")
    void updateMoneyLost(Long id, Double newValue);//@Param("id") @Param("newValue")*/

    @Modifying
    @Query("UPDATE WasteEntity w " +
            "SET w.money_lost = w.money_lost + :newValue " +
            "WHERE w.groceryEntity = :groceryEntity " +
            "AND w.accountEntity = :accountEntity " +
            "AND w.date = :date")
    void updateMoneyLost(AccountEntity accountEntity, GroceryEntity groceryEntity, java.sql.Date date, Double newValue);


}