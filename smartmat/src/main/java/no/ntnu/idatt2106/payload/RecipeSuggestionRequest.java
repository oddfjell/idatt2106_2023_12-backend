package no.ntnu.idatt2106.payload;

/**
 * Payload for the RecipeSuggestionRequests
 */
public class RecipeSuggestionRequest {

    /**
     * ingredients declaration
     */
    private String[] ingredients;

    /**
     * Get method for ingredients
     * @return String[]
     */
    public String[] getIngredients() {
        return ingredients;
    }

    /**
     * Set method for ingredients
     */
    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
