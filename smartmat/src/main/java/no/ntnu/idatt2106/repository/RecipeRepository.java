package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * RecipeRepository
 */
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    /**
     * Find recipe by url
     * @param url String
     * @param servings int
     * @return Optional<RecipeEntity>
     */
    Optional<RecipeEntity> findByUrlAndServings(String url, int servings);

    /**
     * Find recipe and servings by url
     * @param url String
     * @return List<RecipeEntity>
     */
    List<RecipeEntity> findAllByUrl(String url);

}
