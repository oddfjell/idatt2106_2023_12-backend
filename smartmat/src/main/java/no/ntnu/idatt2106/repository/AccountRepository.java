package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsernameIgnoreCase(String username);

}
