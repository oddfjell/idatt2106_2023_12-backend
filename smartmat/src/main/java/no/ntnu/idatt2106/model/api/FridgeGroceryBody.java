package no.ntnu.idatt2106.model.api;

public class FridgeGroceryBody {

    private String name;
    private Long categoryId;
    private int count;

    public FridgeGroceryBody() {
    }

    public FridgeGroceryBody(String name, Long categoryId, int count) {
        super();
        this.name = name;
        this.categoryId = categoryId;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
