package no.ntnu.idatt2106.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account_recipe")
public class AccountRecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

}
