package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Account;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;
import nl.pietervanturennout.utils.types.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements DAO<Account>{

    public Account searchAccountById(int id) throws OperationFailedException, NotFoundException {
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "SELECT * FROM full_account WHERE account_id = :id");
            statement.setInt("id", id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return makeModel(result);
            } else {
                throw new NotFoundException("No account with this id");
            }
        }  catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }


    public Account searchAccountByEmail(String mail) throws OperationFailedException, NotFoundException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "SELECT * FROM full_account WHERE mailAddress = :email;");
            statement.setString("email", mail);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return makeModel(result);
            } else {
                throw new NotFoundException("No account with this mail address");
            }
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public int registerAccount(Account account) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO public.account (mailAddress, password) VALUES (:mail, :pword);");
            statement.setString("mail", account.getMailAddress());
            statement.setString("pword", account.getPassword());

            if (statement.executeUpdate() > 0) {
                return statement.getGeneratedId("account_id");
            } else {
                throw new InvalidDataException("Account couldn't be created");
            }
        } catch (SQLDuplicateKeyException e){
            throw new DuplicateEntryException(e);
        }catch (SQLException e) {
            throw new OperationFailedException(e);
        }

    }

    public void changeGroup(int accountId, Group group) throws OperationFailedException, InvalidDataException, NotFoundException {
        if (accountId == 0){
            throw new InvalidDataException("Can't update if no id is specified for account");
        }
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                            "UPDATE Account_Group_Junction SET group_id = :group WHERE account_id = :account;")
                    .setInt("account", accountId)
                    .setInt("group", group.getGroupId());

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No account with this id exists");
            }
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public void addOrderToAccount(int accountId, int orderId) throws OperationFailedException, NotFoundException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO Account_Order VALUES (:account, :order)"
            );

            statement.setInt("account", accountId);
            statement.setInt("order", orderId);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("Unable to add order to account");
            }
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public void removeOrderFromAccount(int orderId) throws OperationFailedException, NotFoundException {
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "DELETE FROM Account_Order WHERE order_id_ref = :order"
            );

            statement.setInt("order", orderId);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("This order doesn't belong to this account");
            }
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public List<Integer> getAccountOrders(int accountId) throws OperationFailedException, NotFoundException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "SELECT * FROM Account_Order WHERE account_id_ref = :account"
            );

            statement.setInt("account", accountId);

            ResultSet result = statement.executeQuery();

            List<Integer> idList = new ArrayList<>();

            if (!result.next()){
                throw new NotFoundException("This account doesn't have an order");
            }

            idList.add(result.getInt("order_id_ref"));

            while(result.next()){
                idList.add(result.getInt("order_id_ref"));
            }

            return idList;

        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }

    }

    public Account makeModel(ResultSet result) throws OperationFailedException{
        Account account = new Account();

        try {
            account.setAccountId(result.getInt("account_id"));
            account.setPassword(result.getString("password"));
            account.setMailAddress(result.getString("mailAddress"));
            account.setGroup(addGroup(result.getString("group_name")));
            return account;
        }  catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    private Group addGroup(String name){
        return name == null || name.isEmpty() ? null : Group.valueOf(name.toUpperCase());
    }
}