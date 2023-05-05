package no.ntnu.idatt2106.model.api;

/**
 * Payload body for a new profile
 */
public class NewProfileBody {

    /**
     * username declaration
     */
    private String username;
    /**
     * restricted declaration
     */
    private boolean restricted;
    /**
     * password declaration
     */
    private String password;

    /**
     * Constructor
     */
    public NewProfileBody() {
    }

    /**
     * Constructor
     * @param username String
     * @param restricted boolean
     * @param password String
     */
    public NewProfileBody(String username, boolean restricted, String password) {
        this.username = username;
        this.restricted = restricted;
        this.password = password;
    }

    /**
     * GETTERS
     */
    public String getUsername() {
        return username;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public String getPassword() {
        return password;
    }

    /**
     * SETTERS
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
