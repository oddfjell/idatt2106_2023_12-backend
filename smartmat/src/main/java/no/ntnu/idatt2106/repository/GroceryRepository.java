package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.GroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroceryRepository extends JpaRepository<GroceryEntity, Long> {

    /**
     * Find all grocery's by category id
     * @param id Long
     * @return List<GroceryEntity>
     */
    List<GroceryEntity> findAllByCategoryId(Long id);

    // FINDS GROCERY BY NAME

    /**
     * Find grocery's by name
     * @param name String
     * @return GroceryEntity
     */
    GroceryEntity findGroceryEntitiesByNameIgnoreCase(String name);

    /**
     * Find grocery by name
     * @param name String
     * @return Optional<GroceryEntity>
     */
    Optional<GroceryEntity> findByNameIgnoreCase(String name);

    /**
     * Remove grocery by id
     * @param id Long
     */
    void removeById(Long id);
}
