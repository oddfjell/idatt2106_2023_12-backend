package no.ntnu.idatt2106.model.api;

/**
 * Payload response body for a grocery in the fridge
 */
public class FridgeResponseBody {

    /**
     * name declaration
     */
    private String name;
    /**
     * categoryName declaration
     */
    private String categoryName;
    /**
     * categoryImage declaration
     */
    private String categoryImage;
    /**
     * count declaration
     */
    private int count;
    /**
     * expiresInDays declaration
     */
    private int expiresInDays;

    /**
     * Constructor
     */
    public FridgeResponseBody() {
    }

    /**
     * Constructor
     * @param name String
     * @param categoryName String
     * @param categoryImage String
     * @param count int
     * @param expiresInDays int
     */
    public FridgeResponseBody(String name, String categoryName, String categoryImage, int count, int expiresInDays) {
        super();
        this.name = name;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.count = count;
        this.expiresInDays = expiresInDays;
    }

    /**
     * GETTERS
     */
    public String getName() {
        return name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public int getCount() {
        return count;
    }public int getExpiresInDays() {
        return expiresInDays;
    }

    /**
     * SETTERS
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setExpiresInDays(int expiresInDays) {
        this.expiresInDays = expiresInDays;
    }
}
