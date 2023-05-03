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

    @Column(name = "found_in_store")
    private boolean foundInStore;

    private boolean suggestion;

    public ShoppingListEntity() {
    }

    public ShoppingListEntity(AccountEntity accountEntity, GroceryEntity groceryEntity, int count, boolean foundInStore, boolean suggestion) {
        super();
        this.accountEntity = accountEntity;
        this.groceryEntity = groceryEntity;
        this.count = count;
        this.foundInStore = foundInStore;
        this.suggestion = suggestion;
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

    public boolean isFoundInStore() {
        return foundInStore;
    }

    public void setFoundInStore(boolean foundInStore) {
        this.foundInStore = foundInStore;
    }

    public boolean isSuggestion() {
        return suggestion;
    }

    public void setSuggestion(boolean suggestion) {
        this.suggestion = suggestion;
    }
}
