package no.ntnu.idatt2106.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * WasteEntity
 */
@Entity
@Table(name = "waste")
public class WasteEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grocery_id")
    private GroceryEntity groceryEntity;

    private double money_lost;

    private LocalDate date;

    /**
     * CONSTRUCTORS
     */
    public WasteEntity() {
    }

    public WasteEntity(AccountEntity accountEntity, GroceryEntity groceryEntity, double money_lost, LocalDate date) {
        super();
        this.accountEntity = accountEntity;
        this.groceryEntity = groceryEntity;
        this.money_lost = money_lost;
        this.date = date;
    }

    /**
     * GETTERS
     */
    public Long getWaste_id() {
        return id;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public GroceryEntity getGroceryEntity() {
        return groceryEntity;
    }

    public double getMoney_lost() {
        return money_lost;
    }

    public LocalDate getDate() {
        return date;
    }


    /**
     * SETTERS
     */
    public void setWaste_id(Long waste_id) {
        this.id = waste_id;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setGroceryEntity(GroceryEntity groceryEntity) {
        this.groceryEntity = groceryEntity;
    }

    public void setMoney_lost(double money_lost) {
        this.money_lost = money_lost;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}