package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.ShoppingListEntity;
import no.ntnu.idatt2106.dto.ShoppingListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {

    // GET STORED SHOPPING LIST FOR AN ACCOUNT
    @Query(value = "SELECT new no.ntnu.idatt2106.dto.ShoppingListDTO (g.name, s.count, s.foundInStore) FROM ShoppingListEntity s " +
            "INNER JOIN GroceryEntity  g " +
            "ON s.groceryEntity.id = g.id " +
            "WHERE s.accountEntity.id = :id")
    List<ShoppingListDTO> getShoppingList(long id);

}
