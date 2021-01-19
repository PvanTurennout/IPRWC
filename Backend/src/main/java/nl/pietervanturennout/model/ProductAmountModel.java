package nl.pietervanturennout.model;

public class ProductAmountModel {
    private Product product;

    private int amount;

    public ProductAmountModel(Product product, int amount){
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
