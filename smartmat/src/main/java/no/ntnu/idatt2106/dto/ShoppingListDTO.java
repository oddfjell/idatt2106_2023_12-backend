package no.ntnu.idatt2106.dto;

public class ShoppingListDTO {

    private String name;
    private int count;
    private boolean foundInStore;

    private boolean suggestion;

    public ShoppingListDTO(String name, int count, boolean foundInStore, boolean suggestion) {
        this.name = name;
        this.count = count;
        this.foundInStore = foundInStore;
        this.suggestion = suggestion;
    }

    public ShoppingListDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isFoundInStore() {
        return foundInStore;
    }

    public void setFoundInStore(boolean foundInStore) {
        this.foundInStore = foundInStore;
    }

    public boolean isSuggestion() {
        return suggestion;
    }

    public void setSuggestion(boolean suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public String toString() {
        return "ShoppingListDTO{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", foundInStore=" + foundInStore +
                '}';
    }
}
