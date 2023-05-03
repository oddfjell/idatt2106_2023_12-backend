package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {


    List<ShoppingListEntity> findAllBySuggestionTrueAndAccountEntity(AccountEntity account);
    Optional<ShoppingListEntity> findByAccountEntityAndGroceryEntityName(AccountEntity account, String groceryName);

    // GET STORED SHOPPING LIST FOR AN ACCOUNT
    @Query(value = "SELECT new no.ntnu.idatt2106.dto.ShoppingListDTO (g.name, s.count, s.foundInStore, s.suggestion) FROM ShoppingListEntity s " +
            "INNER JOIN GroceryEntity  g " +
            "ON s.groceryEntity.id = g.id " +
            "WHERE s.accountEntity.id = :id")
    List<ShoppingListDTO> getShoppingList(long id);



    // CHECK IF GROCERY EXIST IN SHOPPING LIST
    @Query("SELECT CASE WHEN (COUNT(s) > 0) THEN TRUE ELSE FALSE END " +
            "FROM ShoppingListEntity s " +
            "WHERE s.accountEntity.id = :id AND s.groceryEntity.name = :groceryName")
    boolean groceryExist(long id, String groceryName);



    // UPDATE COUNT ON GROCERY
    @Modifying
    @Query("UPDATE ShoppingListEntity s " +
            "SET s.count = :count " +
            "WHERE s.accountEntity = :account AND s.groceryEntity = :grocery")
    void updateCount(AccountEntity account, GroceryEntity grocery, int count);



    // REMOVE GROCERY FROM SHOPPING LIST
    void removeByAccountEntityIdAndGroceryEntityId(long id, long groceryId);



    Optional<ShoppingListEntity> findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountName, String groceryName);



    List<ShoppingListEntity> findAllByAccountEntityAndFoundInStoreTrue(AccountEntity account);



    void removeAllByAccountEntityAndFoundInStoreTrue(AccountEntity account);



    @Modifying
    @Query("update ShoppingListEntity s set s.foundInStore=?1 where s.accountEntity=?2 and s.groceryEntity=?3")
    void updateFoundInStore(boolean update, AccountEntity account, GroceryEntity grocery);

    @Modifying
    @Query("update ShoppingListEntity s set s.suggestion=?1 where s.accountEntity=?2 and s.groceryEntity=?3")
    void updateSuggestion(boolean suggestion, AccountEntity account, GroceryEntity grocery);




    @Query (value = "SELECT s.count FROM ShoppingListEntity s " +
            "WHERE s.accountEntity= :account AND s.groceryEntity = :grocery")
    int getOldCount (AccountEntity account, GroceryEntity grocery);



    @Query (value = "SELECT s.foundInStore FROM ShoppingListEntity s " +
            "WHERE s.accountEntity = :account AND s.groceryEntity = :grocery")
    boolean getOldFoundInStore (AccountEntity account, GroceryEntity grocery);

    @Query (value = "SELECT s.suggestion FROM ShoppingListEntity s " +
            "WHERE s.accountEntity = :account AND s.groceryEntity = :grocery")
    boolean getOldSuggestion (AccountEntity account, GroceryEntity grocery);
}
