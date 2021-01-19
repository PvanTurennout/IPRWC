package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Order;
import nl.pietervanturennout.services.DAOService;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;
import nl.pietervanturennout.utils.types.OrderStatus;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAO implements DAO<Order>{

    public Order makeModel(ResultSet orderSet) throws OperationFailedException {
        try{
            Order order = new Order();

            order.setOrderId(orderSet.getInt("order_id"));
            order.setOrderStatus(OrderStatus.formStatusId(orderSet.getInt("order_status")));
            order.setFirstName(orderSet.getString("first_name"));
            order.setLastName(orderSet.getString("last_name"));
            order.setAddress(orderSet.getString("address"));
            order.setZipCode(orderSet.getString("zipcode"));
            order.setTown(orderSet.getString("town"));
            order.setShippingMethod(orderSet.getString("shipping_method"));
            order.setTotalPrice(orderSet.getDouble("totalPrice"));
            order.setOrderPlacedDate(new DateTime(orderSet.getTimestamp("order_date")));

            //set items

            return order;
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public List<Order> getAllOrders() throws NotFoundException, OperationFailedException {
        return DAOService.getListOfModels("\"Order\"", this);
    }

    public Order getOrder(int orderId) throws OperationFailedException, NotFoundException {
        return DAOService.searchModelById(orderId, "\"Order\"", "order", this);
    }

    public int insertOrder(Order order) throws DuplicateEntryException, OperationFailedException, InvalidDataException {
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                "INSERT INTO \"Order\" (" +
                        "order_status, first_name, last_name, address, zipcode, town, shipping_method, totalPrice) " +
                        "VALUES (:orderStatus, :fName, :lName, :address, :zip, :town, :shipping, :price)"
            );

            updateAndInsertQueryParameterSetter(order, statement);

            if (statement.executeUpdate() > 0) {
                return statement.getGeneratedId("order_id");
            } else {
                throw new InvalidDataException("Bracelet couldn't be created");
            }
        } catch (SQLDuplicateKeyException e){
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public void updateOrder(Order order)
            throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        if (order.getOrderId() == 0)
            throw new InvalidDataException("Can't update if no id is specified for product");
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "UPDATE \"Order\" SET " +
                            "order_status = :orderStatus, " +
                            "first_name = :fName, " +
                            "last_name = :lName, " +
                            "address = :address, " +
                            "zipcode = :zip, " +
                            "town = :town, " +
                            "shipping_method = :shipping, " +
                            "totalPrice = :price " +
                            "WHERE order_id = :id"
            );

            updateAndInsertQueryParameterSetter(order, statement);
            statement.setInt("id", order.getOrderId());

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No order with this id exists");
            }
        } catch (SQLDuplicateKeyException e){
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }

    }

    public void updateStatus(int orderId, OrderStatus newStatus)
            throws DuplicateEntryException, OperationFailedException, NotFoundException {
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "UPDATE \"Order\" SET order_status = :status WHERE order_id = :id"
            );
            statement.setInt("status", newStatus.getStatusId());
            statement.setInt("id", orderId);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No order with this id exists");
            }
        } catch (SQLDuplicateKeyException e){
            throw new DuplicateEntryException(e);
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public void deleteOrder(int orderId) throws OperationFailedException, NotFoundException{
        DAOService.deleteModelById(orderId, "\"Order\"", "order");
    }

    private void updateAndInsertQueryParameterSetter(Order order, NamedParameterStatement statement)
            throws SQLException {
        statement.setInt("orderStatus", order.getOrderStatus().getStatusId());
        statement.setString("fName", order.getFirstName());
        statement.setString("lName", order.getLastName());
        statement.setString("address", order.getAddress());
        statement.setString("zip", order.getZipCode());
        statement.setString("town", order.getTown());
        statement.setString("shipping", order.getShippingMethod());
        statement.setDouble("price", order.getTotalPrice());
    }
}
