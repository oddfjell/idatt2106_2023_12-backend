package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * ShoppingListRepository
 */
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {

    /**
     * Find all with suggestion = true by AccountEntity
     * @param account AccountEntity
     * @return List<ShoppingListEntity>
     */
    List<ShoppingListEntity> findAllBySuggestionTrueAndAccountEntity(AccountEntity account);

    /**
     * Find by AccountEntity and GroceryEntity
     */
    Optional<ShoppingListEntity> findByAccountEntityAndGroceryEntityName(AccountEntity account, String groceryName);

    /**
     * Get stored shoppingList for an account
     * @param id long
     * @return List<ShoppingListDTO>
     */
    @Query(value = "SELECT new no.ntnu.idatt2106.dto.ShoppingListDTO (g.name, s.count, s.foundInStore, s.suggestion) FROM ShoppingListEntity s " +
            "INNER JOIN GroceryEntity  g " +
            "ON s.groceryEntity.id = g.id " +
            "WHERE s.accountEntity.id = :id")
    List<ShoppingListDTO> getShoppingList(long id);

    /**
     * Checks if grocery already exists in the shoppingList
     * @param id long
     * @param groceryName String
     * @return boolean
     */
    @Query("SELECT CASE WHEN (COUNT(s) > 0) THEN TRUE ELSE FALSE END " +
            "FROM ShoppingListEntity s " +
            "WHERE s.accountEntity.id = :id AND s.groceryEntity.name = :groceryName")
    boolean groceryExist(long id, String groceryName);

    /**
     * Updates count on grocery
     * @param account AccountEntity
     * @param grocery GroceryEntity
     * @param count int
     */
    @Modifying
    @Query("UPDATE ShoppingListEntity s " +
            "SET s.count = :count " +
            "WHERE s.accountEntity = :account AND s.groceryEntity = :grocery")
    void updateCount(AccountEntity account, GroceryEntity grocery, int count);

    /**
     * Removes grocery from shoppingList
     * @param id long
     * @param groceryId AccountEntity
     */
    void removeByAccountEntityIdAndGroceryEntityId(long id, long groceryId);

    /**
     * Find by AccountEntity
     * @param accountName String
     * @param groceryName String
     * @return Optional<ShoppingListEntity>
     */
    Optional<ShoppingListEntity> findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountName, String groceryName);

    /**
     * Find all with foundInStore = true by AccountEntity
     * @param account AccountEntity
     * @return List<ShoppingListEntity>
     */
    List<ShoppingListEntity> findAllByAccountEntityAndFoundInStoreTrue(AccountEntity account);


    /**
     * Remove all with foundInStore = true by AccountEntity
     * @param account AccountEntity
     */
    void removeAllByAccountEntityAndFoundInStoreTrue(AccountEntity account);

    /**
     * update ShoppingListEntity
     * @param update boolean
     * @param account AccountEntity
     * @param grocery GroceryEntity
     */
    @Modifying
    @Query("update ShoppingListEntity s set s.foundInStore=?1 where s.accountEntity=?2 and s.groceryEntity=?3")
    void updateFoundInStore(boolean update, AccountEntity account, GroceryEntity grocery);

    /**
     * update ShoppingListEntity
     * @param suggestion boolean
     * @param account AccountEntity
     * @param grocery GroceryEntity
     */
    @Modifying
    @Query("update ShoppingListEntity s set s.suggestion=?1 where s.accountEntity=?2 and s.groceryEntity=?3")
    void updateSuggestion(boolean suggestion, AccountEntity account, GroceryEntity grocery);


    /**
     * SELECT count FROM ShoppingListEntity
     * @param account AccountEntity
     * @param grocery GroceryEntity
     * @return int
     */
    @Query (value = "SELECT s.count FROM ShoppingListEntity s " +
            "WHERE s.accountEntity= :account AND s.groceryEntity = :grocery")
    int getOldCount (AccountEntity account, GroceryEntity grocery);


    /**
     * SELECT foundInStore FROM ShoppingListEntity
     * @param account AccountEntity
     * @param grocery GroceryEntity
     * @return boolean
     */
    @Query (value = "SELECT s.foundInStore FROM ShoppingListEntity s " +
            "WHERE s.accountEntity = :account AND s.groceryEntity = :grocery")
    boolean getOldFoundInStore (AccountEntity account, GroceryEntity grocery);

    /**
     * SELECT suggestion FROM ShoppingListEntity
     * @param account AccountEntity
     * @param grocery GroceryEntity
     * @return boolean
     */
    @Query (value = "SELECT s.suggestion FROM ShoppingListEntity s " +
            "WHERE s.accountEntity = :account AND s.groceryEntity = :grocery")
    boolean getOldSuggestion (AccountEntity account, GroceryEntity grocery);

    /**
     * DELETE FROM ShoppingListEntity
     * @param account AccountEntity
     */
    @Modifying
    @Query("DELETE FROM ShoppingListEntity s WHERE s.accountEntity = :account")
    void deleteByAccount(AccountEntity account);
}
