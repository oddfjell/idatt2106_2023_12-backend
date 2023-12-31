package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.dto.WastePerCategoryDTO;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * WasteRepository
 */
public interface WasteRepository extends JpaRepository<WasteEntity, Long> {

    /**
     * Get total waste fro an account
     * @param id long
     * @return ptional<Integer>
     */
    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id")
    Optional<Integer> getMoneyLost(long id);

    /**
     * Get waste sorted by category
     * @param id long
     * @return List<WastePerCategoryDTO>
     */
    @Query("SELECT new no.ntnu.idatt2106.dto.WastePerCategoryDTO(c.name, SUM(w.money_lost)) from GroceryEntity g " +
            "INNER JOIN WasteEntity  w " +
            "ON g.id = w.groceryEntity.id " +
            "INNER JOIN CategoryEntity c " +
            "ON g.category.id = c.id " +
            "WHERE w.accountEntity.id = :id " +
            "GROUP BY g.category.id ")
    List<WastePerCategoryDTO> getMoneyLostPerCategory(long id);

    /**
     * Get waste for a given category
     * @param id long
     * @param categoryId long
     * @return Optional<Integer>
     */
    @Query("SELECT SUM(w.money_lost) FROM GroceryEntity g " +
            "INNER JOIN WasteEntity w ON g.id = w.groceryEntity.id " +
            "WHERE w.accountEntity.id = :id AND g.category.id = :categoryId")
    Optional<Integer> getMoneyLostByCategory(long id, long categoryId);

    /**
     * Get total waste per date by month
     * @param id long
     * @param month int
     * @return List<List<Object>>
     */
    @Query(value = "SELECT cast(date.date as date) as date, ifnull(money_lost,0) as money_lost, ifnull((SUM(money_lost) OVER (ORDER BY date.date)),0) as total " +
            "FROM " +
            "( " +
            "    SELECT date, sum(money_lost) as money_lost " +
            "    from waste " +
            "    where account_id = ?1 and month(date)=?2 " +
            "    group by date " +
            ") as waste " +
            "" +
            "right JOIN " +
            "(" +
            "select FROM_UNIXTIME(UNIX_TIMESTAMP(CONCAT(year(now()),'-',?2,'-',n)),'%Y-%m-%d') as date from ( " +
            "select (((b4.0 << 1 | b3.0) << 1 | b2.0) << 1 | b1.0) << 1 | b0.0 as n " +
            "from  (select 0 union all select 1) as b0, " +
            "(select 0 union all select 1) as b1, " +
            "(select 0 union all select 1) as b2, " +
            "(select 0 union all select 1) as b3, " +
            "(select 0 union all select 1) as b4 ) t " +
            "where n > 0 and n <= if(month(now())=?2, DAY(now()), DAY(LAST_DAY(CONCAT(YEAR(CURDATE()), '-', ?2, '-', '01')))) " +
            "order by date " +
            ") as date " +
            "on waste.date = date.date", nativeQuery = true)
    List<List<Object>> getTotalWastePerDateByMonth(long id, int month);

    /**
     * SELECT w FROM WasteEntity
     * @param account AccountEntity
     * @param groceryEntity GroceryEntity
     * @param date LocalDate
     * @return Optional<WasteEntity>
     */
    @Query("SELECT w FROM WasteEntity w " +
            "WHERE w.groceryEntity = :groceryEntity " +
            "AND w.accountEntity = :account " +
            "AND w.date = :date")
    Optional<WasteEntity> findWasteEntitiesByGroceryEntity(AccountEntity account, GroceryEntity groceryEntity, LocalDate date);

    /**
     * UPDATE WasteEntity
     * @param accountEntity AccountEntity
     * @param groceryEntity GroceryEntity
     * @param date LocalDate
     * @param newValue Double
     */
    @Modifying
    @Query("UPDATE WasteEntity w " +
            "SET w.money_lost = w.money_lost + :newValue " +
            "WHERE w.groceryEntity = :groceryEntity " +
            "AND w.accountEntity = :accountEntity " +
            "AND w.date = :date")
    void updateMoneyLost(AccountEntity accountEntity, GroceryEntity groceryEntity, LocalDate date, Double newValue);

    /**
     * DELETE FROM WasteEntity
     * @param account AccountEntity
     */
    @Modifying
    @Query("DELETE FROM WasteEntity w WHERE w.accountEntity = :account")
    void deleteByAccount(AccountEntity account);
}