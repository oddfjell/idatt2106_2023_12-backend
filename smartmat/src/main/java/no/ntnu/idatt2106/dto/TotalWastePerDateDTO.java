package no.ntnu.idatt2106.dto;

import java.util.Date;

public class TotalWastePerDateDTO {

    private Date date;
    private double money_lost;
    private double total;

    public TotalWastePerDateDTO(Date date, double money_lost, double total) {
        this.date = date;
        this.money_lost = money_lost;
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMoney_lost() {
        return money_lost;
    }

    public void setMoney_lost(double money_lost) {
        this.money_lost = money_lost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}