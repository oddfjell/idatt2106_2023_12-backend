package no.ntnu.idatt2106.model;


import jakarta.persistence.*;

@Entity
@Table(name = "waste")
public class WasteEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ean;
    private String fullName;
    private double price;
    private double weight;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity account;


    public WasteEntity() {
    }

    public WasteEntity(String ean, String fullName, double price, double weight, AccountEntity account) {
        super();
        this.ean = ean;
        this.fullName = fullName;
        this.price = price;
        this.weight = weight;
        this.account = account;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
