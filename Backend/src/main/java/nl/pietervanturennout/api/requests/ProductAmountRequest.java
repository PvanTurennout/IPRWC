package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.model.Product;
import nl.pietervanturennout.model.ProductAmountModel;

import javax.validation.constraints.NotNull;

public class ProductAmountRequest {

    @NotNull
    private int productId;

    @NotNull
    private int amount;

    public int getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    @JsonSetter
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @JsonSetter
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductAmountModel getProductAmountModel(){
        Product tempProduct = new Product();
        tempProduct.setProductId(this.productId);
        return new ProductAmountModel(tempProduct, this.amount);
    }
}
