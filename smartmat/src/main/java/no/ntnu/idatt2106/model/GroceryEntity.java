package no.ntnu.idatt2106.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * GroceryEntity
 */
@Entity
@Table(name = "grocery")
public class GroceryEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private int expiryDate;

    /**
     * CONSTRUCTORS
     */
    public GroceryEntity() {
    }

    public GroceryEntity(String name, CategoryEntity category, int expiryDate) {
        super();
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    /**
     * GETTERS
     */
    public Long getGrocery_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public int getExpiryDate() {
        return expiryDate;
    }

    /**
     * SETTERS
     */
    public void setGrocery_id(Long grocery_id) {
        this.id = grocery_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }
}
