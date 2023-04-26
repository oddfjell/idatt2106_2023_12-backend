package no.ntnu.idatt2106.model.api;

public class FridgeResponseBody {

    private String name;
    private String categoryName;
    private String categoryImage;
    private int count;
    private int expiresInDays;


    public FridgeResponseBody() {
    }

    public FridgeResponseBody(String name, String categoryName, String categoryImage, int count, int expiresInDays) {
        super();
        this.name = name;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.count = count;
        this.expiresInDays = expiresInDays;
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

    public int getExpiresInDays() {
        return expiresInDays;
    }

    public void setExpiresInDays(int expiresInDays) {
        this.expiresInDays = expiresInDays;
    }
}
