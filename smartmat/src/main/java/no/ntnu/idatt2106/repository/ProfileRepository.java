package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    List<ProfileEntity> findAllByAccount(AccountEntity account);

    Optional<ProfileEntity> findByUsernameIgnoreCaseAndAccount(String username, AccountEntity account);

    void deleteByAccountAndUsername(AccountEntity account, String username);

    void deleteByAccount(AccountEntity account);

}