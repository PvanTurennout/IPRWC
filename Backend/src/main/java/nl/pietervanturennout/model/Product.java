package nl.pietervanturennout.model;

public class Product {
    private int productId;
    private String name;
    private String brand;
    private double price;
    private String description;
    private String imagePath;
    private int stock;
    private int unitsSold;
    private Watch watch;
    // private List<Review> reviews;


    public Product() {}

    public Product(int id, String name, String brand, double price, String description, String imagePath, int stock, int sold, Watch watch) {
        this.productId = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.stock = stock;
        this.unitsSold = sold;
        this.watch = watch;
    }

    public Product(String name, String brand, double price, String description, String imagePath, int stock, int sold, Watch watch){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.stock = stock;
        this.unitsSold = sold;
        this.watch = watch;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUnitsSold() { return this.unitsSold; }

    public void setUnitsSold(int sold) { this.unitsSold = sold; }

    public Watch getWatch() { return this.watch; }

    public void setWatch(Watch item) { this.watch = item; }
}
