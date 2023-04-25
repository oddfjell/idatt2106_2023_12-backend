package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryService {

  @Autowired
  CategoryRepository categoryRepository;

  public void addCategory(CategoryEntity category){
    categoryRepository.save(category);
  }

  public void removeCategory(CategoryEntity category){
    categoryRepository.removeById(category.getCategory_id());
  }

}
