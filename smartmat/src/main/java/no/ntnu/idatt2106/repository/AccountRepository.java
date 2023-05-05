package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

/**
 * AccountRepository
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    /**
     * Find account by username
     * @param username String
     * @return Optional<AccountEntity>
     */
    Optional<AccountEntity> findByUsernameIgnoreCase(String username);

    /**
     * Find account by id
     * @param id long
     * @return AccountEntity
     */
    AccountEntity findById(long id);

    /**
     * Remove account by id
     * @param id Long
     */
    void removeAccountEntityById(Long id);

    /**
     * Remove account by username
     * @param username String
     */
    void removeAccountEntityByUsername(String username);

    /**
     * update AccountEntity
     * @param username String
     * @param userId Long
     */
    @Modifying
    @Query("update AccountEntity a set a.username=?1 where a.id=?2")
    void updateUsername(String username, Long userId);

    /**
     * update AccountEntity
     * @param password String
     * @param userId Long
     */
    @Modifying
    @Query("update AccountEntity a set a.password=?1 where a.id=?2")
    void updatePassword(String password, Long userId);

}
