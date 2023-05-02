package no.ntnu.idatt2106.model.api;

public class NewProfileBody {

    private String username;
    private boolean restricted;
    private String password;

    public NewProfileBody() {
    }

    public NewProfileBody(String username, boolean restricted, String password) {
        this.username = username;
        this.restricted = restricted;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
