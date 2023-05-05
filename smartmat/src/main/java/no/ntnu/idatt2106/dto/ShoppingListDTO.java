package no.ntnu.idatt2106.dto;

/**
 * ShoppingListDTO
 */
public class ShoppingListDTO {

    /**
     * name declaration
     */
    private String name;
    /**
     * count declaration
     */
    private int count;
    /**
     * foundInStore declaration
     */
    private boolean foundInStore;
    /**
     * suggestion declaration
     */
    private boolean suggestion;

    /**
     * Constructor
     * @param name String
     * @param count int
     * @param foundInStore boolean
     * @param suggestion boolean
     */
    public ShoppingListDTO(String name, int count, boolean foundInStore, boolean suggestion) {
        this.name = name;
        this.count = count;
        this.foundInStore = foundInStore;
        this.suggestion = suggestion;
    }

    /**
     * Constructor
     * @param name String
     */
    public ShoppingListDTO(String name) {
        this.name = name;
    }

    /**
     * GETTERS
     */
    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public boolean isFoundInStore() {
        return foundInStore;
    }

    public boolean isSuggestion() {
        return suggestion;
    }

    /**
     * SETTERS
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setFoundInStore(boolean foundInStore) {
        this.foundInStore = foundInStore;
    }

    public void setSuggestion(boolean suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * toString method
     * @return String
     */
    @Override
    public String toString() {
        return "ShoppingListDTO{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", foundInStore=" + foundInStore +
                '}';
    }
}
