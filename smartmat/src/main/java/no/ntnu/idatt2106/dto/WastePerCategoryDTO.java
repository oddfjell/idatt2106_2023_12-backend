package no.ntnu.idatt2106.dto;

/**
 * WastePerCategoryDTO
 */
public class WastePerCategoryDTO {

    /**
     * category declaration
     */
    private String category;
    /**
     * money_lost declaration
     */
    private double money_lost;

    /**
     * Constructor
     * @param category String
     * @param money_lost double
     */
    public WastePerCategoryDTO(String category, double money_lost) {
        this.category = category;
        this.money_lost = money_lost;
    }

    /**
     * GETTERS
     */
    public String getCategory() {
        return category;
    }

    public double getMoney_lost() {
        return money_lost;
    }

    /**
     * SETTERS
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public void setMoney_lost(double money_lost) {
        this.money_lost = money_lost;
    }
}
