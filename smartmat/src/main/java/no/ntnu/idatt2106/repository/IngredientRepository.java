package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.IngredientEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

    Optional<IngredientEntity> findByNameIgnoreCaseAndRecipeUrl(String name, String recipeUrl);

    List<IngredientEntity> findAllByRecipeUrlAndRecipeServings(String recipeUrl, int servings);

    void removeAllByRecipeUrl(String recipeUrl);

}
