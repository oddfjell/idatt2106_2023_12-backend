package no.ntnu.idatt2106.model.api;

public class FridgeResponseBody {

    private String name;
    private String categoryName;
    private String categoryImage;
    private int count;

    public FridgeResponseBody() {
    }

    public FridgeResponseBody(String name, String categoryName, String categoryImage, int count) {
        super();
        this.name = name;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
