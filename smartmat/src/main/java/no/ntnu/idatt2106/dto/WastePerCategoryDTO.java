package no.ntnu.idatt2106.dto;

public class WastePerCategoryDTO {

    private String category;
    private double money_lost;

    public WastePerCategoryDTO(String category, double money_lost) {
        this.category = category;
        this.money_lost = money_lost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMoney_lost() {
        return money_lost;
    }

    public void setMoney_lost(double money_lost) {
        this.money_lost = money_lost;
    }
}
