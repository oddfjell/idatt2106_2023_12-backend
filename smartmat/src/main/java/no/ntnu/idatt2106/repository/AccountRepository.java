package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
