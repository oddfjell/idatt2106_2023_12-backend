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

    // GET STORED SHOPPING LIST FOR AN ACCOUNT
    @Query(value = "SELECT new no.ntnu.idatt2106.dto.ShoppingListDTO (g.name, s.count, s.foundInStore) FROM ShoppingListEntity s " +
            "INNER JOIN GroceryEntity  g " +
            "ON s.groceryEntity.id = g.id " +
            "WHERE s.accountEntity.id = :id")
    List<ShoppingListDTO> getShoppingList(long id);

    Optional<ShoppingListEntity> findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountName, String groceryName);

    List<ShoppingListEntity> findAllByAccountEntityAndFoundInStoreTrue(AccountEntity account);

    void removeAllByAccountEntityAndFoundInStoreTrue(AccountEntity account);

    @Modifying
    @Query("update ShoppingListEntity s set s.foundInStore=?1 where s.accountEntity=?2 and s.groceryEntity=?3")
    void updateFoundInStore(boolean update, AccountEntity account, GroceryEntity grocery);


}
