package nl.pietervanturennout.services;

import nl.pietervanturennout.dao.DAO;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLKeyNotExistException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOService {

    public static <T> List<T> getListOfModels(String table, DAO<T> dao) throws OperationFailedException, NotFoundException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement("SELECT * FROM " + table);
            ResultSet result = statement.executeQuery();

            List<T> resultModelList = new ArrayList<>();

            if (!result.next()){
                throw new NotFoundException("Unable to find one " + table + "!");
            }

            resultModelList.add(dao.makeModel(result));

            while(result.next()){
                resultModelList.add(dao.makeModel(result));
            }

            return resultModelList;

        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }

    }

    public static <T> T searchModelById(int id, String table, String idColumn, DAO<T> dao) throws OperationFailedException, NotFoundException {
        try {
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement("SELECT * FROM " + table + " WHERE " + idColumn + "_id = :id");
            statement.setInt("id", id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return dao.makeModel(result);
            } else {
                throw new NotFoundException("Unable to find a " + idColumn + " with this id!");
            }
        } catch (SQLKeyNotExistException e){
            throw new NotFoundException(e);
        }
        catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public static void deleteModelById(int id, String table, String idColumn) throws OperationFailedException, NotFoundException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement("DELETE FROM " + table + " WHERE " + idColumn + "_id = :id");
            statement.setInt("id", id);

            if(statement.executeUpdate() == 0) {
                throw new NotFoundException("Unable to find a " + idColumn + " with this id!");
            }
        } catch (SQLKeyNotExistException e){
            throw new NotFoundException(e);
        }
        catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }
}
