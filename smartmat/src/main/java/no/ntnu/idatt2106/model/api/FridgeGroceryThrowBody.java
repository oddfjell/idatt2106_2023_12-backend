package no.ntnu.idatt2106.model.api;

import java.sql.Date;
import java.time.LocalDate;

public class FridgeGroceryThrowBody {

    private String name;
    private double newMoneyValue;
    private LocalDate throwDate;

    public FridgeGroceryThrowBody() {
    }

    //TODO antar at % er ganget i front end
    public FridgeGroceryThrowBody(String name, double newMoneyValue, LocalDate throwDate) {
        this.name = name;
        this.newMoneyValue = newMoneyValue;
        this.throwDate = throwDate;
    }

    public LocalDate getThrowDate() {
        return throwDate;
    }
    public void setThrowDate() {
        this.throwDate = LocalDate.now();
    }

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
}
