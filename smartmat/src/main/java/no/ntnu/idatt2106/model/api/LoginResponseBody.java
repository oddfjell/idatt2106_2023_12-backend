package no.ntnu.idatt2106.model.api;

public class LoginResponseBody {

    private String username;
    private String jwt;

    public LoginResponseBody() {
    }

    public LoginResponseBody(String username, String jwt) {
        super();
        this.username = username;
        this.jwt = jwt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
