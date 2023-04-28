package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRecipeRepository extends JpaRepository<AccountRecipeEntity, Long> {
}
