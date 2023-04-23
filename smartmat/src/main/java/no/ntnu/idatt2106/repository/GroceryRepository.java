package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.GroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryRepository extends JpaRepository<GroceryEntity, Long> {
    List<GroceryEntity> findAllByCategoryId(Long id);

    void removeById(Long id);
}
