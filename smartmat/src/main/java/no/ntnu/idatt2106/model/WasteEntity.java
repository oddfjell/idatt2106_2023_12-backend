package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

@Entity
@Table(name = "waste")
public class WasteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waste_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grocery_id")
    private GroceryEntity groceryEntity;

    private int money_lost;

    public WasteEntity() {
    }

    public Long getWaste_id() {
        return waste_id;
    }

    public void setWaste_id(Long waste_id) {
        this.waste_id = waste_id;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public GroceryEntity getGroceryEntity() {
        return groceryEntity;
    }

    public void setGroceryEntity(GroceryEntity groceryEntity) {
        this.groceryEntity = groceryEntity;
    }

    public int getMoney_lost() {
        return money_lost;
    }

    public void setMoney_lost(int money_lost) {
        this.money_lost = money_lost;
    }
}