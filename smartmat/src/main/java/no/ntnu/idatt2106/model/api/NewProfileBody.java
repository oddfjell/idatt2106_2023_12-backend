package no.ntnu.idatt2106.model.api;

public class NewProfileBody {

    private String username;
    private boolean restricted;

    public NewProfileBody() {
    }

    public NewProfileBody(String username, boolean restricted) {
        this.username = username;
        this.restricted = restricted;
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
}
