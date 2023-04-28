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

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

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
        profileRepository.save(profile);

    }


}
