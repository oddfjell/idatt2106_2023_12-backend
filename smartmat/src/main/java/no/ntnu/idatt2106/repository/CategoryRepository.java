package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  void removeById(Long id);

}
