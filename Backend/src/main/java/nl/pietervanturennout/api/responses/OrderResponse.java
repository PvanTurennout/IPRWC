package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.model.Order;

public class OrderResponse extends BaseResponse{
    public static ResponseObject generateMap(Order order) {
        if (order == null || order.getOrderId() == 0)
            return null;

        ResponseObject response = new ResponseObject();

        response.put("orderId", order.getOrderId());
        response.put("orderStatus", order.getOrderStatus().getStatusId());
        response.put("items", new ProductAmountListResponse(order.getItems()).getData());
        response.put("firstName", order.getFirstName());
        response.put("lastName", order.getLastName());
        response.put("address", order.getAddress());
        response.put("zipcode", order.getZipCode());
        response.put("town", order.getTown());
        response.put("shippingMethod", order.getShippingMethod());
        response.put("totalCost", order.getTotalPrice());
        response.put("orderPlacementDate", order.getOrderPlacedDate());

        return response;
    }

    public OrderResponse(Order order) { data = generateMap(order); }
}