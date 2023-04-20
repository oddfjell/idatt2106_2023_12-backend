package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByUsernameIgnoreCase(String username);


    @Modifying
    @Query("update AccountEntity a set a.username=?1 where a.id=?2")
    void updateUsername(String username, Long userId);

    @Modifying
    @Query("update AccountEntity a set a.password=?1 where a.id=?2")
    void updatePassword(String password, Long userId);

}
