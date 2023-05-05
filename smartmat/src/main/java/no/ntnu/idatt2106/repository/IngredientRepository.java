package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * IngredientRepository
 */
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

    /**
     * Find ingredient by name
     * @param name String
     * @param recipeUrl String
     * @return Optional<IngredientEntity>
     */
    Optional<IngredientEntity> findByNameIgnoreCaseAndRecipeUrl(String name, String recipeUrl);

    /**
     * Find all by url
     * @param recipeUrl String
     * @param servings int
     * @return List<IngredientEntity>
     */
    List<IngredientEntity> findAllByRecipeUrlAndRecipeServings(String recipeUrl, int servings);

    /**
     * Find all by url and servings
     * @param recipeUrl String
     */
    void removeAllByRecipeUrl(String recipeUrl);

}
