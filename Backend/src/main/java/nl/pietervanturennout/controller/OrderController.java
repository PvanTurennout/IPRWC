package nl.pietervanturennout.controller;

import nl.pietervanturennout.dao.OrderDAO;
import nl.pietervanturennout.dao.OrderProductsDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Order;
import nl.pietervanturennout.utils.types.OrderStatus;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderController {

    //Singleton
    private static final OrderController instance;
    static {instance = new OrderController();}
    public static OrderController getInstance(){return instance;}

    public OrderController(){orderDAO = new OrderDAO();}

    private final OrderDAO orderDAO;

    public int createOrder(Order newOrder, int accountId)
            throws DuplicateEntryException, OperationFailedException, InvalidDataException, NotFoundException {
        int id = orderDAO.insertOrder(newOrder);
        AccountController.getInstance().addOrder(accountId, id);
        OrderProductsController.getInstance().addItems(newOrder.getItems(), id);
        return id;
    }

    public Order getOrder(int orderId) throws NotFoundException, OperationFailedException {
        Order order = orderDAO.getOrder(orderId);
        OrderProductsController.getInstance().populateOrdersItems(order);
        return order;
    }

    public void modifyOrder(Order adjustedOrder)
            throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        OrderProductsController.getInstance().modifyItems(adjustedOrder.getItems(), adjustedOrder.getOrderId());
        orderDAO.updateOrder(adjustedOrder);
    }

    public void removeOrder(int orderId) throws NotFoundException, OperationFailedException{
        OrderProductsController.getInstance().deleteItems(orderId);
        AccountController.getInstance().removeOrder(orderId);
        orderDAO.deleteOrder(orderId);
    }

    public void updateStatus(int orderId, OrderStatus newStatus)
            throws OperationFailedException, DuplicateEntryException, NotFoundException {
        orderDAO.updateStatus(orderId, newStatus);
    }

    public List<Order> getAccountsOrders(int accountId) throws OperationFailedException{
        List<Integer> orderIdList;
        List<Order> orderList = new ArrayList<>();

        try {
            orderIdList = AccountController.getInstance().getOrders(accountId);
        } catch (NotFoundException e){
            return new ArrayList<>();
        }

        for (Integer id : orderIdList) {
            try {
                orderList.add(getOrder(id));
            } catch (NotFoundException e) {
                try {
                    AccountController.getInstance().removeOrder(id);
                } catch (NotFoundException notFoundException) {
                    System.out.println("!!!!! There Is Something Wrong With Order " + id + " !!!!!");
                    throw new OperationFailedException(notFoundException);
                }
            }
        }

        return orderList;
    }

    public List<Order> getAllOrders() throws NotFoundException, OperationFailedException {
        List<Order> orders = orderDAO.getAllOrders();
        for (Order order : orders) {
            OrderProductsController.getInstance().populateOrdersItems(order);
        }
        return orders;
    }
}
