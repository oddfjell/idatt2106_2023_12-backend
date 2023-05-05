package no.ntnu.idatt2106.service;

import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.ProfileAlreadyExistsInAccountException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ProfileEntity;
import no.ntnu.idatt2106.model.api.NewProfileBody;
import no.ntnu.idatt2106.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service class for profiles/users in an account related requests
 * ProfileService contains methods that gets, adds or deletes profiles/users
 */
@Service
@Transactional
public class ProfileService {

    /**
     * ProfileRepository field injection
     */
    @Autowired
    private ProfileRepository profileRepository;
    /**
     * EncryptionService field injection
     */
    @Autowired
    private EncryptionService encryptionService;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    /**
     * Method that gets all the profiles connected to a certain account
     * @param account AccountEntity
     * @return List<ProfileEntity>
     */
    public List<ProfileEntity> getAllProfilesByAccount(AccountEntity account){
        logger.info("Returning all the profiles of {}", account.getUsername());
        return profileRepository.findAllByAccount(account);
    }

    /**
     * Method to add a new normal or restricted profile to an account. It does also set a PIN for the profile
     * @param newProfileBody NewProfileBody
     * @param account AccountEntity
     * @throws ProfileAlreadyExistsInAccountException ProfileAlreadyExistsInAccountException
     */
    public void addProfileToAccount(NewProfileBody newProfileBody, AccountEntity account) throws ProfileAlreadyExistsInAccountException {
        if(profileRepository.findByUsernameIgnoreCaseAndAccount(newProfileBody.getUsername(), account).isPresent()){
            logger.info("This profile already exists!");
            throw new ProfileAlreadyExistsInAccountException();
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setUsername(newProfileBody.getUsername());
        profile.setRestricted(newProfileBody.isRestricted());
        profile.setAccount(account);
        if(newProfileBody.getPassword().isEmpty()){
            profile.setPassword("");
        }else{
            profile.setPassword(encryptionService.encryptPassword(newProfileBody.getPassword()));
        }
        logger.info("Saving {}", newProfileBody.getUsername());
        profileRepository.save(profile);
    }

    /**
     * Method for the login using the PIN of a profile after the initial login to the account
     * @param account AccountEntity
     * @param newProfileBody NewProfileBody
     * @return ProfileEntity
     */
    public ProfileEntity loginProfile(AccountEntity account, NewProfileBody newProfileBody){
        Optional<ProfileEntity> optionalProfileEntity = profileRepository.findByUsernameIgnoreCaseAndAccount(newProfileBody.getUsername(), account);

        if(optionalProfileEntity.isPresent()){
            ProfileEntity profile = optionalProfileEntity.get();
            if(encryptionService.verifyPassword( newProfileBody.getPassword(), profile.getPassword())){
                logger.info("Login registered for {} in {}", newProfileBody.getUsername(), account.getUsername());
                return profile;
            }else{
                logger.info("Wrong password");
                return null;
            }
        }else{
            logger.info("{} does not exist", newProfileBody.getUsername());
            return null;
        }
    }

    /**
     * Method for deleting ....
     * @param account
     * @param newProfileBody
     * @return
     */
    public boolean deleteProfileFromAccount(AccountEntity account, NewProfileBody newProfileBody){
        ProfileEntity profile = this.loginProfile(account, newProfileBody);

        if(profile != null){
            profileRepository.deleteByAccountAndUsername(account, profile.getUsername());
            return true;
        }else{
            return false;
        }
    }
}
