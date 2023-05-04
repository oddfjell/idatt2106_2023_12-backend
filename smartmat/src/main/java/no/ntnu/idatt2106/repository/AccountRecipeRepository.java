package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.AccountRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRecipeRepository extends JpaRepository<AccountRecipeEntity, Long> {

    @Modifying
    @Query("DELETE FROM AccountRecipeEntity a WHERE a.accountEntity = :account")
    void deleteByAccount(AccountEntity account);
}
