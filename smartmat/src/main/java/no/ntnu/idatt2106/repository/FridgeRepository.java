package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import no.ntnu.idatt2106.model.GroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FridgeRepository extends JpaRepository<FridgeEntity, Long> {

    List<FridgeEntity> findAllByAccountEntity(AccountEntity account);
    FridgeEntity findByAccountEntityAndGroceryEntity(AccountEntity account, GroceryEntity grocery);

}
