package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, Long> {
}
