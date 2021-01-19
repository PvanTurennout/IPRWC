package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.model.Order;
import nl.pietervanturennout.model.ProductAmountModel;
import nl.pietervanturennout.utils.constraints.Validator;
import nl.pietervanturennout.utils.types.OrderStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    @NotNull @Valid
    private List<ProductAmountRequest> items;

    @NotNull
    @Validator("standard")
    private String firstName;

    @NotNull
    @Validator("standard")
    private String lastName;

    @NotNull
    @Validator("standard")
    private String address;

    @NotNull
    @Validator("zipcode")
    private String zipCode;

    @NotNull
    @Validator("standard")
    private String town;

    @NotNull
    @Validator("standard")
    private String shippingMethod;

    @NotNull
    private double totalPrice;


//    public OrderStatus getOrderStatus() { return orderStatus; }

    public List<ProductAmountRequest> getItems() { return items; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getAddress() { return address; }

    public String getZipCode() { return zipCode; }

    public String getTown() { return town; }

    public String getShippingMethod() { return shippingMethod; }

    public double getTotalPrice() { return totalPrice; }


//    @JsonSetter
//    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    @JsonSetter
    public void setItems(List<ProductAmountRequest> items) { this.items = items; }

    @JsonSetter
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @JsonSetter
    public void setLastName(String lastName) { this.lastName = lastName; }

    @JsonSetter
    public void setAddress(String address) { this.address = address; }

    @JsonSetter
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    @JsonSetter
    public void setTown(String town) { this.town = town; }

    @JsonSetter
    public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }

    @JsonSetter
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public Order getOrderModel(){
        List<ProductAmountModel> productAmountModels = new ArrayList<>();
        for (ProductAmountRequest productAmountRequest : this.items) {
            productAmountModels.add(productAmountRequest.getProductAmountModel());
        }
        return new Order(OrderStatus.RECEIVED, productAmountModels, this.firstName, this.lastName, this.address,
                this.zipCode, this.town, this.shippingMethod, this.totalPrice);
    }
}
