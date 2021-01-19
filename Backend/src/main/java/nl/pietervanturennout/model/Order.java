package nl.pietervanturennout.model;

import nl.pietervanturennout.utils.types.OrderStatus;
import org.joda.time.DateTime;

import java.util.List;

public class Order {
    private int orderId;
    private OrderStatus orderStatus;
    private List<ProductAmountModel> items;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String town;
    private String shippingMethod;
    private double totalPrice;
    private DateTime orderPlacedDate;


    public Order(){}

    public Order(int orderId, OrderStatus orderStatus, List<ProductAmountModel> items,
                 String firstName, String lastName, String address, String zipCode,
                 String town, String shippingMethod, double totalPrice) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.items = items;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.town = town;
        this.shippingMethod = shippingMethod;
        this.totalPrice = totalPrice;
    }

    public Order(OrderStatus orderStatus, List<ProductAmountModel> items, String firstName,
                 String lastName, String address, String zipCode, String town,
                 String shippingMethod, double totalPrice) {
        this.orderStatus = orderStatus;
        this.items = items;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zipCode = zipCode;
        this.town = town;
        this.shippingMethod = shippingMethod;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<ProductAmountModel> getItems() {
        return items;
    }

    public void setItems(List<ProductAmountModel> items) {
        this.items = items;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() { return town; }

    public void setTown(String town) {
        this.town = town;
    }

    public String getShippingMethod() { return shippingMethod; }

    public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public DateTime getOrderPlacedDate() { return orderPlacedDate; }

    public void setOrderPlacedDate(DateTime orderPlacedDate) { this.orderPlacedDate = orderPlacedDate; }
}
