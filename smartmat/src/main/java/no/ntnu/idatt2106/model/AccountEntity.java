package no.ntnu.idatt2106.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * AccountEntity
 */
@Entity
@Table(name = "account")
public class AccountEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    /**
     * CONSTRUCTORS
     */
    public AccountEntity() {
    }

    public AccountEntity(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    /**
     * GETTERS
     */
    public Long getAccount_id() {
        return id;
    }

    public String getUsername() {
        return username;
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

    public void setPassword(String password) {
        this.password = password;
    }
}
