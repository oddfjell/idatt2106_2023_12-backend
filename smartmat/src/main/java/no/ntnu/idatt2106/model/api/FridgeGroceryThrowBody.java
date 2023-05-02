package no.ntnu.idatt2106.model.api;

import no.ntnu.idatt2106.model.GroceryEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FridgeGroceryThrowBody {

    /*private String name;
    private Long categoryId;
    private double percent;*/
    private String name;
    //private GroceryEntity groceryEntity;
    private double newMoneyValue;
    private java.sql.Date throwDate;

    /*
     LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        System.out.println(date);
     */


    public FridgeGroceryThrowBody() {
    }

    //TODO antar at % er ganget i front end
    public FridgeGroceryThrowBody(String name, double newMoneyValue, Date throwDate) {
        this.name = name;
        this.newMoneyValue = newMoneyValue;
        this.throwDate = throwDate;//Date.valueOf(LocalDate.now());
    }

    public java.sql.Date getThrowDate() {
        return throwDate;
    }
    public void setThrowDate() {
        this.throwDate = Date.valueOf(LocalDate.now());
    }

    /*public GroceryEntity getGroceryEntity() {
        return groceryEntity;
    }

    public void setGroceryEntity(GroceryEntity groceryEntity) {
        this.groceryEntity = groceryEntity;
    }*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNewMoneyValue() {
        return newMoneyValue;
    }

    public void setNewMoneyValue(double newMoneyValue) {
        this.newMoneyValue = newMoneyValue;
    }
    /*public FridgeGroceryThrowBody(String name, Long categoryId, int percent) {
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
    }*/
}
