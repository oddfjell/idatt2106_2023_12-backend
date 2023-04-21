package no.ntnu.idatt2106.payload;

import java.util.List;

public class RecipeSuggestionRequest {

    private String[] ingredients;

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
