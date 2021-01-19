package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.model.Product;
import nl.pietervanturennout.utils.constraints.Validator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

public class ProductUpdateRequest {

    @NotNull @Min(1)
    private int productId;

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

    @NotNull @Valid
    private WatchUpdateRequest watch;


    public int getProductId() { return this.productId; }

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

    public WatchUpdateRequest getProductItem() {
        return this.watch;
    }


    @JsonSetter
    public void setProductId(int productId) { this.productId = productId; }

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
    public void setWatch(WatchUpdateRequest productItem) {
        this.watch = productItem;
    }


    public Product getProductModel() {
        return new Product(this.productId, this.name, this.brand, this.price, this.description, this.imagePath, this.stock, 0, this.watch.getWatchUpdateModel());
    }
}
