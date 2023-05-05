package no.ntnu.idatt2106.repository;

import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * ProfileRepository
 */
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    /**
     * Find all profiles in account
     * @param account AccountEntity
     * @return List<ProfileEntity>
     */
    List<ProfileEntity> findAllByAccount(AccountEntity account);

    /**
     * Find profile by username
     * @param username String
     * @param account AccountEntity
     * @return Optional<ProfileEntity>
     */
    Optional<ProfileEntity> findByUsernameIgnoreCaseAndAccount(String username, AccountEntity account);

    /**
     * Delete profile by username
     * @param account AccountEntity
     * @param username String
     */
    void deleteByAccountAndUsername(AccountEntity account, String username);

    /**
     * Delete profile by account
     * @param account AccountEntity
     */
    void deleteByAccount(AccountEntity account);

}