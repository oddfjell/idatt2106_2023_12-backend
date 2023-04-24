package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FridgeRepository extends JpaRepository<FridgeEntity, Long> {

    List<FridgeEntity> findAllByAccountEntity(AccountEntity account);
    Optional<FridgeEntity> findByAccountEntityAndGroceryEntity(AccountEntity account, GroceryEntity grocery);

    Optional<FridgeEntity> findByAccountEntityIdAndGroceryEntityId(Long accountId, Long groceryId);

    void removeByAccountEntityUsernameIgnoreCaseAndGroceryEntityNameIgnoreCase(String accountUsername, String groceryName);

    @Modifying
    @Query("update FridgeEntity f set f.count=?1 where f.accountEntity=?2 and f.groceryEntity=?3")
    void updateCount(int count, AccountEntity account, GroceryEntity grocery);


}
