package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

/**
 * ProfileEntity
 */
@Entity
@Table(name = "profile")
public class ProfileEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private boolean restricted;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity account;


    /**
     * CONSTRUCTORS
     */
    public ProfileEntity() {
    }

    public ProfileEntity(String username, boolean restricted, String password, AccountEntity account) {
        this.username = username;
        this.restricted = restricted;
        this.password = password;
        this.account = account;
    }

    /**
     * GETTERS
     */
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    /**
     * SETTERS
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
