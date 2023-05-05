package no.ntnu.idatt2106.model.api;

/**
 * Payload response body for a login account request
 */
public class LoginResponseBody {

    /**
     * username declaration
     */
    private String username;
    /**
     * jwt declaration
     */
    private String jwt;

    /**
     * Constructor
     */
    public LoginResponseBody() {
    }

    /**
     * Constructor
     * @param username String
     * @param jwt String
     */
    public LoginResponseBody(String username, String jwt) {
        super();
        this.username = username;
        this.jwt = jwt;
    }

    /**
     * GETTERS
     */
    public String getUsername() {
        return username;
    }

    public String getJwt() {
        return jwt;
    }

    /**
     * SETTERS
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
