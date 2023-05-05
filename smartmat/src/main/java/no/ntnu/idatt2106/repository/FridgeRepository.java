package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

/**
 * FridgeRepository
 */
public interface FridgeRepository extends JpaRepository<FridgeEntity, Long> {

    /**
     * Find all grocery's in the fridge by account
     * @param account AccountEntity
     * @return List<FridgeEntity>
     */
    List<FridgeEntity> findAllByAccountEntity(AccountEntity account);

    /**
     * Find FridgeEntity by the account and grocery
     * @param account AccountEntity
     * @param grocery GroceryEntity
     * @return Optional<FridgeEntity>
     */
    Optional<FridgeEntity> findByAccountEntityAndGroceryEntity(AccountEntity account, GroceryEntity grocery);

    /**
     * Find by account entity username ignore case and grocery entity name ignore case
     * @param accountUsername String
     * @param groceryName String
     * @return Optional<FridgeEntity>
     */
    Optional<FridgeEntity> findByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountUsername, String groceryName);

    /**
     * Remove by account entity username ignore case and grocery entity name ignore case
     * @param accountUsername String
     * @param groceryName String
     */
    void removeByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountUsername, String groceryName);

    /**
     * Remove by AccountEntity and GroceryEntity
     * @param account AccountEntity
     * @param groceryName String
     */
    void removeByAccountEntityAndGroceryEntityNameIgnoreCase(AccountEntity account, String groceryName);

    /**
     * Delete by AccountEntity
     * @param accountUsername String
     * @param groceryName String
     */
    void deleteByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountUsername, String groceryName);

    /**
     * DELETE FROM FridgeEntity
     * @param account AccountEntity
     * @param grocery GroceryEntity
     */
    @Modifying
    @Query("DELETE FROM FridgeEntity f WHERE f.accountEntity=?1 AND f.groceryEntity=?2")
    void deleteByAccountEntityAndGroceryEntity(AccountEntity account, GroceryEntity grocery);

    /**
     * update FridgeEntity
     * @param count int
     * @param account AccountEntity
     * @param grocery GroceryEntity
     */
    @Modifying
    @Query("update FridgeEntity f set f.count=?1 where f.accountEntity=?2 and f.groceryEntity=?3")
    void updateCount(int count, AccountEntity account, GroceryEntity grocery);

    /**
     * DELETE FROM FridgeEntity
     * @param account AccountEntity
     */
    @Modifying
    @Query("DELETE FROM FridgeEntity f WHERE f.accountEntity = :account")
    void deleteByAccount(AccountEntity account);
}
