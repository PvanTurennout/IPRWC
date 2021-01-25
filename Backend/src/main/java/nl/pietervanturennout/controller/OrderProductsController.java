package nl.pietervanturennout.controller;

import nl.pietervanturennout.dao.OrderProductsDAO;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Order;
import nl.pietervanturennout.model.ProductAmountModel;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class OrderProductsController {

    //Singleton
    private static final OrderProductsController instance;
    static {instance = new OrderProductsController();}
    public static OrderProductsController getInstance(){return instance;}

    private final OrderProductsDAO itemsDAO;

    public OrderProductsController() {
        itemsDAO = new OrderProductsDAO();
    }

    public void populateOrdersItems(Order order) throws OperationFailedException, NotFoundException{
        try {
            order.setItems(this.itemsDAO.getOrderItemList(order.getOrderId()));
        } catch (NotFoundException e) {
            order.setItems(new ArrayList<>());
            return;
        }
        populateProducts(order.getItems());
    }

    private void populateProducts(List<ProductAmountModel> items) throws OperationFailedException, NotFoundException {
//        List<ProductAmountModel> populatedItems = new ArrayList<>();
        for (ProductAmountModel item : items){
            item.setProduct(ProductController.getInstance().getProductFromId(item.getProduct().getProductId()));
        }
    }

    public void deleteItems(int orderId) throws OperationFailedException, NotFoundException{
        this.itemsDAO.deleteOrderItemList(orderId);
    }

    public void addItems(List<ProductAmountModel> items, int orderId) throws OperationFailedException, DuplicateEntryException, NotFoundException {
        this.itemsDAO.insertOrderItemList(items, orderId);
    }

    public void modifyItems(List<ProductAmountModel> items, int orderId) throws OperationFailedException, DuplicateEntryException, NotFoundException {
        this.itemsDAO.deleteOrderItemList(orderId);
        this.itemsDAO.insertOrderItemList(items, orderId);
    }

    public void productDeleteUpdate(int productId) throws OperationFailedException, NotFoundException {
        itemsDAO.replaceDeletedProduct(productId);
    }
}
