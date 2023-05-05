package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoryRepository
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  /**
   * Remove category by id
   * @param id Long
   */
  void removeById(Long id);

  /**
   * Return category by id
   * @param id Long
   * @return CategoryEntity
   */
  CategoryEntity getCategoryEntityById(Long id);

}
