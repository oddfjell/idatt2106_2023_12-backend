package no.ntnu.idatt2106.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    @Value("${encryption.salt.rounds}")
    private int saltRounds;
    private String salt;

    private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    @PostConstruct
    public void postConstruct(){
        logger.info("Generating salt");
        salt = BCrypt.gensalt(saltRounds);
    }

    public String encryptPassword(String password){
        logger.info("Encrypting password");
        return BCrypt.hashpw(password, salt);
    }

    public boolean verifyPassword(String password, String hash){
        logger.info("Verifying password");
        return BCrypt.checkpw(password, hash);
    }
}
