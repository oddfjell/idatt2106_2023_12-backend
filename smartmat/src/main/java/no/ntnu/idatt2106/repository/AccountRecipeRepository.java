package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.AccountRecipeEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * AccountRecipeRepository
 */
public interface AccountRecipeRepository extends JpaRepository<AccountRecipeEntity, Long> {

    /**
     * Remove all recipes by AccountEntity
     * @param account AccountEntity
     */
    void removeAllByAccountEntity(AccountEntity account);

    /**
     * Find AccountRecipeEntity by AccountEntity and RecipeEntity
     * @param account AccountEntity
     * @param recipe RecipeEntity
     * @return Optional<AccountRecipeEntity>
     */
    Optional<AccountRecipeEntity> findByAccountEntityAndRecipe(AccountEntity account, RecipeEntity recipe);

    /**
     * DELETE FROM AccountRecipeEntity
     * @param account AccountEntity
     * @return List<AccountRecipeEntity>
     */
    List<AccountRecipeEntity> findAllByAccountEntity(AccountEntity account);
    @Modifying
    @Query("DELETE FROM AccountRecipeEntity a WHERE a.accountEntity = :account")
    void deleteByAccount(AccountEntity account);

    /**
     * UPDATE AccountRecipeEntity
     * @param account AccountEntity
     * @param fromRecipe RecipeEntity
     * @param toRecipe RecipeEntity
     */
    @Modifying
    @Query("UPDATE AccountRecipeEntity a set a.recipe=?3 WHERE a.accountEntity=?1 and a.recipe=?2")
    void replaceRecipe(AccountEntity account, RecipeEntity fromRecipe, RecipeEntity toRecipe);
}
