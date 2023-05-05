package no.ntnu.idatt2106.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import no.ntnu.idatt2106.model.AccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * Service class to make JWTTokens and decrypt JWTTokens
 */
@Service
public class JWTService {

    /**
     * Value of jwt.algorithm.key field injection
     */
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    /**
     * Value of jwt.issuer field injection
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * Value of jwt.expiryInSeconds field injection
     */
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    /**
     * Algorithm instance
     */
    private Algorithm algorithm;
    /**
     * Username declaration
     */
    private static final String USERNAME_KEY = "USERNAME";
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    /**
     * Method that is called immediately after the bean has been constructed. This initializes the algorithm
     * before the EncryptionService is used
     */
    @PostConstruct
    public void postConstruct(){
        logger.info("Setting algorithm");
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    /**
     * Method to generate a JWTToken by using the username of the account
     * @param user AccountEntity
     * @return String
     */
    public String generateJWT(AccountEntity user){
        logger.info("Generating key");
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000*expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    /**
     * Method to decrypt the JWTToken and get the username
     * @param token String
     * @return String
     */
    public String getUsername(String token){
        logger.info("Getting the username from the jwt");
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
}
