package no.ntnu.idatt2106.model;


import jakarta.persistence.*;

@Entity
@Table(name = "ingredient")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    public IngredientEntity() {
    }

    public IngredientEntity(String name, RecipeEntity recipe) {
        this.name = name;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}
