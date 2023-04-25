package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.GroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroceryRepository extends JpaRepository<GroceryEntity, Long> {
    List<GroceryEntity> findAllByCategoryId(Long id);

    // FINDS GROCERY BY NAME
    GroceryEntity findGroceryEntitiesByNameIgnoreCase(String name);
}
