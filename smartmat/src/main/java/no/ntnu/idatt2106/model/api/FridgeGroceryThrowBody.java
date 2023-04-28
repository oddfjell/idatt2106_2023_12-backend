package no.ntnu.idatt2106.model.api;

public class FridgeGroceryThrowBody {

    private String name;
    private Long categoryId;
    private double percent;

    public FridgeGroceryThrowBody() {
    }

    public FridgeGroceryThrowBody(String name, Long categoryId, int percent) {
        super();
        this.name = name;
        this.categoryId = categoryId;
        this.percent = percent;
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

    public double getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
