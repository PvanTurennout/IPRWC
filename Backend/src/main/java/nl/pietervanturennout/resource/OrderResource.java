package nl.pietervanturennout.resource;

import io.dropwizard.auth.Auth;
import nl.pietervanturennout.api.authentication.AuthenticateObject;
import nl.pietervanturennout.api.requests.OrderRequest;
import nl.pietervanturennout.api.requests.OrderStatusUpdateRequest;
import nl.pietervanturennout.api.requests.OrderUpdateRequest;
import nl.pietervanturennout.api.responses.IdResponse;
import nl.pietervanturennout.api.responses.OrderListResponse;
import nl.pietervanturennout.api.responses.OrderResponse;
import nl.pietervanturennout.controller.OrderController;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/order")
public class OrderResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("order_create")
    public IdResponse createOrder(@Auth AuthenticateObject user, @Valid OrderRequest request)
            throws DuplicateEntryException, OperationFailedException, InvalidDataException, NotFoundException {
        return new IdResponse(
                OrderController.getInstance().createOrder(request.getOrderModel(), user.getAccountId()),
                "order");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed("order_read_all")
    public OrderResponse getOrder(@Auth AuthenticateObject user, @NotNull @PathParam("id") int orderId)
            throws NotFoundException, OperationFailedException {
        return new OrderResponse(OrderController.getInstance().getOrder(orderId));
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("order_delete")
    public void deleteOrder(@Auth AuthenticateObject user, @NotNull @PathParam("id") int orderId)
            throws NotFoundException, OperationFailedException{
        OrderController.getInstance().removeOrder(orderId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("order_update")
    public void updateOrder(@Auth AuthenticateObject user, @Valid OrderUpdateRequest request)
            throws DuplicateEntryException, OperationFailedException, InvalidDataException, NotFoundException {
        OrderController.getInstance().modifyOrder(request.getOrderModel());
    }

    @PUT
    @Path("/status")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("order_change_status")
    public void updateOrderStatus(@Auth AuthenticateObject user, @Valid OrderStatusUpdateRequest request)
            throws OperationFailedException, DuplicateEntryException, NotFoundException {
        OrderController.getInstance().updateStatus(request.getOrderId(), request.getOrderStatus());
    }

    @GET
    @Path("/account")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("order_read_own")
    public OrderListResponse getAccountsOrders(@Auth AuthenticateObject user) throws OperationFailedException {
        return new OrderListResponse(OrderController.getInstance().getAccountsOrders(user.getAccountId()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("order_read_all")
    public OrderListResponse getAllOrders(@Auth AuthenticateObject user) throws NotFoundException, OperationFailedException {
        return new OrderListResponse(OrderController.getInstance().getAllOrders());
    }
}
