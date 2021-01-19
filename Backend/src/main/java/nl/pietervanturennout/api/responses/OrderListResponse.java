package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ListResponseObject;
import nl.pietervanturennout.model.Order;

import java.util.List;

public class OrderListResponse extends BaseResponse{
    public static ListResponseObject generateList(List<Order> orders){
        if (orders == null)
            return null;

        return ListResponseObject.listGenerator(orders, OrderResponse::generateMap);
    }

    public OrderListResponse(List<Order> orders) {
        data = generateList(orders);
    }
}
