package no.ntnu.idatt2106.model.api;

/**
 * Payload body for a grocery in the fridge
 */
public class FridgeGroceryBody {

    /**
     * name declaration
     */
    private String name;
    /**
     * categoryId declaration
     */
    private Long categoryId;
    /**
     * count declaration
     */
    private int count;

    /**
     * Constructor
     */
    public FridgeGroceryBody() {
    }

    /**
     * Constructor
     * @param name String
     * @param categoryId Long
     * @param count int
     */
    public FridgeGroceryBody(String name, Long categoryId, int count) {
        super();
        this.name = name;
        this.categoryId = categoryId;
        this.count = count;
    }

    /**
     * GETTERS
     */
    public String getName() {
        return name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public int getCount() {
        return count;
    }

    /**
     * SETTERS
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
