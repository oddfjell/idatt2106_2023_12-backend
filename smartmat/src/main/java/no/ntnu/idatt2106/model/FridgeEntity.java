package no.ntnu.idatt2106.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * FridgeEntity
 */
@Entity
@Table(name = "fridge")
public class FridgeEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "grocery_id")
    private GroceryEntity groceryEntity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private int count;

    /**
     * CONSTRUCTORS
     */
    public FridgeEntity() {}

    public FridgeEntity(AccountEntity accountEntity, GroceryEntity groceryEntity, LocalDate date, int count) {
        super();
        this.accountEntity = accountEntity;
        this.groceryEntity = groceryEntity;
        this.date = date;
        this.count = count;
    }

    /**
     * GETTERS
     */
    public Long getFridge_id() {
        return id;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public GroceryEntity getGroceryEntity() {
        return groceryEntity;
    }

    public int getCount() {
        return count;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * SETTERS
     */
    public void setFridge_id(Long fridge_id) {
        this.id = fridge_id;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setGroceryEntity(GroceryEntity groceryEntity) {
        this.groceryEntity = groceryEntity;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}