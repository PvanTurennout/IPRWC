package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.model.Product;
import nl.pietervanturennout.utils.constraints.Validator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

public class ProductRequest {

    @NotNull
    @Validator("standard")
    private String name;

    @NotNull
    @Validator("standard")
    private String brand;

    @NotNull @Min(1)
    private double price;

    @NotNull
    @Validator("description")
    private String description;

    @NotNull
    @Validator("standard")
    private String imagePath;

    @NotNull @Min(1)
    private int stock;

    @DefaultValue("0")
    private int unitsSold;

    @NotNull @Valid
    private WatchRequest watch;


    public String getName() {
        return this.name;
    }

    public String getBrand() {
        return this.brand;
    }

    public double getPrice() {
        return this.price;
    }

    public String getDescription() { return description; }

    public String getImagePath() {
        return this.imagePath;
    }

    public int getStock() {
        return this.stock;
    }

    public int getUnitsSold() {
        return this.unitsSold;
    }

    public WatchRequest getWatch() {
        return this.watch;
    }


    @JsonSetter
    public void setName(String name) { this.name = name; }

    @JsonSetter
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @JsonSetter
    public void setPrice(double price) {
        this.price = price;
    }

    @JsonSetter
    public void setDescription(String description) { this.description = description; }

    @JsonSetter
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @JsonSetter
    public void setStock(int stock) {
        this.stock = stock;
    }

    @JsonSetter
    public void setUnitsSold(int unitsSold) {
        this.unitsSold = unitsSold;
    }

    @JsonSetter
    public void setWatch(WatchRequest watch) {
        this.watch = watch;
    }


    public Product getProductModel() {
        return new Product(this.name, this.brand, this.price, this.description, this.imagePath, this.stock,
                this.unitsSold, this.watch.getWatchModel());
    }
}
