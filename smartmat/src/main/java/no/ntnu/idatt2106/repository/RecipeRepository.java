package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {

    Optional<RecipeEntity> findByUrl(String url);

}