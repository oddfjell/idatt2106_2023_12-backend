package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.AccountRecipeEntity;
import no.ntnu.idatt2106.model.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AccountRecipeRepository extends JpaRepository<AccountRecipeEntity, Long> {


    void removeAllByAccountEntity(AccountEntity account);

    Optional<AccountRecipeEntity> findByAccountEntityAndRecipe(AccountEntity account, RecipeEntity recipe);

    List<AccountRecipeEntity> findAllByAccountEntity(AccountEntity account);
    @Modifying
    @Query("DELETE FROM AccountRecipeEntity a WHERE a.accountEntity = :account")
    void deleteByAccount(AccountEntity account);


    @Modifying
    @Query("UPDATE AccountRecipeEntity a set a.recipe=?3 WHERE a.accountEntity=?1 and a.recipe=?2")
    void replaceRecipe(AccountEntity account, RecipeEntity fromRecipe, RecipeEntity toRecipe);
}
