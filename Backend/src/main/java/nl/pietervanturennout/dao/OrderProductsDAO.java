package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Product;
import nl.pietervanturennout.model.ProductAmountModel;
import nl.pietervanturennout.services.DAOService;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderProductsDAO implements DAO<ProductAmountModel> {

    public ProductAmountModel makeModel(ResultSet result) throws OperationFailedException {
        try {
            Product product = new Product();
            product.setProductId(result.getInt("product_ref_id"));
            return new ProductAmountModel(product, result.getInt("amount"));
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public List<ProductAmountModel> getOrderItemList(int orderId) throws OperationFailedException, NotFoundException {
        return DAOService.getListOfModels("Order_Products WHERE order_ref_id = " + orderId, this);
    }

    public void deleteOrderItemList(int orderId) throws OperationFailedException, NotFoundException {
        DAOService.deleteModelById(orderId, "Order_Products", "order_ref");
    }

    public void insertOrderItemList(List<ProductAmountModel> itemList, int orderId)
            throws OperationFailedException, DuplicateEntryException, NotFoundException {
        for (ProductAmountModel productAmountModel : itemList) {
            insertOneItem(productAmountModel, orderId);
        }
    }

    private void insertOneItem(ProductAmountModel item, int orderId)
            throws OperationFailedException, DuplicateEntryException, NotFoundException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO Order_Products VALUES (:orderId, :productId, :amount)"
            );

            statement.setInt("orderId", orderId);
            statement.setInt("productId", item.getProduct().getProductId());
            statement.setInt("amount", item.getAmount());

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No account with this id exists");
            }
        } catch (SQLDuplicateKeyException e){
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void replaceDeletedProduct(int productId) throws OperationFailedException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "DELETE FORM Order_Products WHERE product_ref_id = :product;")
                    .setInt("product", productId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }
}
