package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WasteRepository extends JpaRepository<WasteEntity, Long> {

    @Query("SELECT SUM(e.money_lost) FROM WasteEntity e WHERE e.accountEntity.id = :id")
    int getMoneyLost(long id);
}
