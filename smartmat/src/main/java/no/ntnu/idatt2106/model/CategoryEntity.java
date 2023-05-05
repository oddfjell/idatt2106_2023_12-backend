package no.ntnu.idatt2106.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * CategoryEntity
 */
@Entity
@Table(name = "category")
public class CategoryEntity {

    /**
     * COLUMNS
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    private String image;

    /**
     * CONSTRUCTORS
     */
    public CategoryEntity() {
    }

    public CategoryEntity(String name, String image) {
        super();
        this.name = name;
        this.image = image;
    }

    /**
     * GETTERS
     */
    public Long getCategory_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    /**
     * SETTERS
     */
    public void setCategory_id(Long category_id) {
        this.id = category_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
