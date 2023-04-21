package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shopping_list")
public class ShoppingListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grocery_id")
    private GroceryEntity groceryEntity;

    private int count;

    public ShoppingListEntity() {
    }

    public ShoppingListEntity(AccountEntity accountEntity, GroceryEntity groceryEntity, int count) {
        super();
        this.accountEntity = accountEntity;
        this.groceryEntity = groceryEntity;
        this.count = count;
    }

    public Long getShopping_list_id() {
        return id;
    }

    public void setShopping_list_id(Long shopping_list_id) {
        this.id = shopping_list_id;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
