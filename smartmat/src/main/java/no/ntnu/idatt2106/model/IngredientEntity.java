package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

/**
 * IngredientEntity
 */
@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    /**
     * CONSTRUCTORS
     */
    public IngredientEntity() {
    }

    public IngredientEntity(String name, RecipeEntity recipe) {
        this.name = name;
        this.recipe = recipe;
    }

    /**
     * GETTERS
     */
    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    /**
     * SETTERS
     */
    public String getName() {
        return name;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}
