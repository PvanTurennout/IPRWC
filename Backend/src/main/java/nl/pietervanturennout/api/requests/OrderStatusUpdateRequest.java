package nl.pietervanturennout.api.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import nl.pietervanturennout.utils.types.OrderStatus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderStatusUpdateRequest {

    @NotNull @Min(1)
    private int orderId;

    @NotNull @Min(1)
    private int orderStatus;


    public int getOrderId() {
        return orderId;
    }

    public OrderStatus getOrderStatus() {
        return OrderStatus.formStatusId(orderStatus);
    }


    @JsonSetter
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @JsonSetter
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
