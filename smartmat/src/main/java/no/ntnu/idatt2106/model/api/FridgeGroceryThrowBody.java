package no.ntnu.idatt2106.model.api;

import java.time.LocalDate;

/**
 * Payload body for a grocery in the waste
 */
public class FridgeGroceryThrowBody {

    /**
     * name declaration
     */
    private String name;
    /**
     * newMoneyValue declaration
     */
    private double newMoneyValue;
    /**
     * throwDate declaration
     */
    private LocalDate throwDate;

    /**
     * Constructor
     */
    public FridgeGroceryThrowBody() {
    }

    /**
     * Constructor
     * @param name String
     * @param newMoneyValue double
     * @param throwDate LocalDate
     */
    public FridgeGroceryThrowBody(String name, double newMoneyValue, LocalDate throwDate) {
        this.name = name;
        this.newMoneyValue = newMoneyValue;
        this.throwDate = throwDate;
    }

    /**
     * GETTERS
     */
    public LocalDate getThrowDate() {
        return throwDate;
    }

    public String getName() {
        return name;
    }

    public double getNewMoneyValue() {
        return newMoneyValue;
    }

    /**
     * SETTERS
     */
    public void setThrowDate() {
        this.throwDate = LocalDate.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNewMoneyValue(double newMoneyValue) {
        this.newMoneyValue = newMoneyValue;
    }
}
