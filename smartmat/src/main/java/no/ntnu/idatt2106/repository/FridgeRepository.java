package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.FridgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FridgeRepository extends JpaRepository<FridgeEntity, Long> {
    /**int getAmount(String name);
    boolean doContain(FridgeEntity fridgeEntity);

    boolean throwProduct(FridgeEntity fridgeEntity);*/
}
