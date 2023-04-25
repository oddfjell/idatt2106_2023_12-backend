package no.ntnu.idatt2106.dto;

public class ShoppingListDTO {

    private String name;
    private int count;
    private boolean foundInStore;

    public ShoppingListDTO(String name, int count, boolean foundInStore) {
        this.name = name;
        this.count = count;
        this.foundInStore = foundInStore;
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
}
