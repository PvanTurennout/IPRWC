package nl.pietervanturennout.dao;


import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.services.DAOService;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;
import nl.pietervanturennout.sql.SQLKeyNotExistException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishListDAO implements DAO<Integer>{

    public Integer makeModel(ResultSet resultSet) throws OperationFailedException {
        try{
            return resultSet.getInt("wishlist_product_id");
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }


    public List<Integer> get(int accountId) throws OperationFailedException {
        try {
            return DAOService.getListOfModels("WishList WHERE wishlist_account_id = " + accountId, this);
        } catch (NotFoundException e){
            return new ArrayList<>();
        }
    }

    public void add(int accountId, int productId) throws NotFoundException, DuplicateEntryException, OperationFailedException, InvalidDataException {
        if (productId == 0) {
            throw new InvalidDataException("Id 0 not accepted!");
        }
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO WishList VALUES (:accountId, :productId)"
            );

            statement.setInt("accountId", accountId);
            statement.setInt("productId", productId);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No account with this id exists");
            }
        } catch (SQLDuplicateKeyException e){
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void remove(int accountId, int productId) throws NotFoundException, OperationFailedException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "DELETE FROM WishList WHERE wishlist_account_id = :account AND wishlist_product_id = :product"
            );

            statement.setInt("account", accountId);
            statement.setInt("product", productId);

            if(statement.executeUpdate() == 0) {
                throw new NotFoundException("This product is not in your wishlist");
            }
        } catch (SQLKeyNotExistException e){
            throw new NotFoundException(e);
        }
        catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void removeProduct(int productId) throws OperationFailedException {
        try {
            DAOService.deleteModelById(productId, "WishList", "wishlist_product");
        } catch (NotFoundException e) {
            // do nothing
        }
    }
}
