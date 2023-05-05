package no.ntnu.idatt2106.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Service class to encrypt and decrypt passwords
 */
@Service
public class EncryptionService {

    /**
     * Value of encryption.salt.rounds field injection
     */
    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    /**
     * String
     */
    private String salt;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    /**
     * Method that is called immediately after the bean has been constructed. This initializes the salt
     * before the EncryptionService is used
     */
    @PostConstruct
    public void postConstruct(){
        logger.info("Generating salt");
        salt = BCrypt.gensalt(saltRounds);
    }

    /**
     * Method to decrypt a String
     * @param password String
     * @return String
     */
    public String encryptPassword(String password){
        logger.info("Encrypting password");
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Method to encrypt a String and verify it
     * @param password String
     * @param hash String
     * @return boolean
     */
    public boolean verifyPassword(String password, String hash){
        logger.info("Verifying password");
        return BCrypt.checkpw(password, hash);
    }
}
