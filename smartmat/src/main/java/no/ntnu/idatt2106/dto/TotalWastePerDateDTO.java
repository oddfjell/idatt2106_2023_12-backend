package no.ntnu.idatt2106.dto;

import java.util.Date;

/**
 * TotalWastePerDateDTO
 */
public class TotalWastePerDateDTO {

    /**
     * date declaration
     */
    private Date date;
    /**
     * money_lost declaration
     */
    private double money_lost;
    /**
     * total declaration
     */
    private double total;

    /**
     * Constructor
     * @param date Date
     * @param money_lost double
     * @param total double
     */
    public TotalWastePerDateDTO(Date date, double money_lost, double total) {
        this.date = date;
        this.money_lost = money_lost;
        this.total = total;
    }

    /**
     * GETTERS
     */
    public Date getDate() {
        return date;
    }

    public double getMoney_lost() {
        return money_lost;
    }

    public double getTotal() {
        return total;
    }

    /**
     * SETTERS
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public void setMoney_lost(double money_lost) {
        this.money_lost = money_lost;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}