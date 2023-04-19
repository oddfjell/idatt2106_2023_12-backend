package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WasteRepository extends JpaRepository<WasteEntity, Long> {
}
