package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

  public void addCategory(CategoryEntity category){
    logger.info("Saving category {}", category.getName());
    categoryRepository.save(category);
  }

  public void removeCategory(CategoryEntity category){
    logger.info("Removing category {}", category.getName());
    categoryRepository.removeById(category.getCategory_id());
  }
}
