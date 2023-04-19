package no.ntnu.idatt2106.model;


import jakarta.persistence.*;

@Entity
@Table(name = "shoppinglist")
public class ShoppingListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String ean;
    private String fullName;
    private String brand;
    private String url;
    private String imageUrl;
    private String description;
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity account;


    public ShoppingListEntity() {
    }

    public ShoppingListEntity(String ean, String fullName, String brand, String url, String imageUrl, String description, double price, AccountEntity account) {
        super();
        this.ean = ean;
        this.fullName = fullName;
        this.brand = brand;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
