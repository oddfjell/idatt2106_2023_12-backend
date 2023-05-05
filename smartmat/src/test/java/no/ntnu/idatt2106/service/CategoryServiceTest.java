package no.ntnu.idatt2106.service;
import no.ntnu.idatt2106.model.CategoryEntity;
import no.ntnu.idatt2106.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    CategoryEntity category;
    @BeforeEach
    void setUp() {
        category = new CategoryEntity("testCategoryYES", null);
        categoryService.addCategory(category);
    }

    @Test
    void testAddCategory() {
        assertEquals("testCategoryYES", categoryRepository.getCategoryEntityById(category.getCategory_id()).get().getName());
        categoryService.removeCategory(category);
    }

    @Test
    void testRemoveCategory() {
        categoryService.removeCategory(category);
        assertFalse(categoryRepository.getCategoryEntityById(category.getCategory_id()).isPresent());
    }
}
