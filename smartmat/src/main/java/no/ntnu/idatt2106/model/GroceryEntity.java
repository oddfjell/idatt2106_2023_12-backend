package no.ntnu.idatt2106.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "grocery")
public class GroceryEntity {

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



    public GroceryEntity() {
    }

    public GroceryEntity(String name, CategoryEntity category, int expiryDate) {
        super();
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    public Long getGrocery_id() {
        return id;
    }

    public void setGrocery_id(Long grocery_id) {
        this.id = grocery_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public int getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }
}
