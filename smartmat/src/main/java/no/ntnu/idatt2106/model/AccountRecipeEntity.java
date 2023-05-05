package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

/**
 * AccountRecipeEntity
 */
@Entity
@Table(name = "account_recipe")
public class AccountRecipeEntity {

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
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    /**
     * CONSTRUCTORS
     */
    public AccountRecipeEntity() {
    }

    public AccountRecipeEntity(AccountEntity accountEntity, RecipeEntity recipe) {
        this.accountEntity = accountEntity;
        this.recipe = recipe;
    }

    /**
     * GETTERS
     */
    public Long getId() {
        return id;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    /**
     * SETTERS
     */

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}
