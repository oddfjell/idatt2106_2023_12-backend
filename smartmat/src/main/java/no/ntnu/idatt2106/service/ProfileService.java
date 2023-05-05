package no.ntnu.idatt2106.service;


import jakarta.transaction.Transactional;
import no.ntnu.idatt2106.exceptions.ProfileAlreadyExistsInAccountException;
import no.ntnu.idatt2106.model.AccountEntity;
import no.ntnu.idatt2106.model.ProfileEntity;
import no.ntnu.idatt2106.model.api.NewProfileBody;
import no.ntnu.idatt2106.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EncryptionService encryptionService;

    public List<ProfileEntity> getAllProfilesByAccount(AccountEntity account){
        return profileRepository.findAllByAccount(account);
    }

    public void addProfileToAccount(NewProfileBody newProfileBody, AccountEntity account) throws ProfileAlreadyExistsInAccountException {

        if(profileRepository.findByUsernameIgnoreCaseAndAccount(newProfileBody.getUsername(), account).isPresent()){
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
        profileRepository.save(profile);
    }

    public ProfileEntity loginProfile(AccountEntity account, NewProfileBody newProfileBody){
        Optional<ProfileEntity> optionalProfileEntity = profileRepository.findByUsernameIgnoreCaseAndAccount(newProfileBody.getUsername(), account);

        if(optionalProfileEntity.isPresent()){
            ProfileEntity profile = optionalProfileEntity.get();
            if(encryptionService.verifyPassword( newProfileBody.getPassword(), profile.getPassword())){
                return profile;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    public boolean deleteProfileFromAccount(AccountEntity account, NewProfileBody newProfileBody){

        if(newProfileBody.getPassword().isEmpty()){
            Optional<ProfileEntity> profileEntity = profileRepository.findByUsernameIgnoreCaseAndAccount(newProfileBody.getUsername(), account);

            profileEntity.ifPresent(entity -> profileRepository.deleteByAccountAndUsername(account, entity.getUsername()));
            return true;
        }else{
            ProfileEntity profile = this.loginProfile(account, newProfileBody);

            if(profile != null){
                profileRepository.deleteByAccountAndUsername(account, profile.getUsername());
                return true;
            }else{
                return false;
            }
        }
    }
}
