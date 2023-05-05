package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for category related requests
 * CategoryService contains methods that adds and deletes category's
 */
@Service
@Transactional
public class CategoryService {

  /**
   * CategoryRepository field injection
   */
  @Autowired
  CategoryRepository categoryRepository;
  /**
   * Logger
   */
  private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

  /**
   * Method to add a category to the database
   * @param category CategoryEntity
   */
  public void addCategory(CategoryEntity category){
    logger.info("Saving category {}", category.getName());
    categoryRepository.save(category);
  }

  /**
   * Method to remove a category from the database
   * @param category CategoryEntity
   */
  public void removeCategory(CategoryEntity category){
    logger.info("Removing category {}", category.getName());
    categoryRepository.removeById(category.getCategory_id());
  }
}
