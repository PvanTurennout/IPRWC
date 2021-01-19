package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Bracelet;
import nl.pietervanturennout.services.DAOService;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BraceletDAO implements DAO<Bracelet> {

    public Bracelet makeModel(ResultSet result) throws OperationFailedException {
        try {
            return new Bracelet(
                    result.getInt("bracelet_id"),
                    result.getInt("bracelet_length"),
                    result.getString("bracelet_material"),
                    result.getString("bracelet_style"),
                    result.getString("bracelet_color")
            );
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public Bracelet getBraceletFromId(int braceletId) throws OperationFailedException, NotFoundException {
        return DAOService.searchModelById(braceletId, "Bracelet", "bracelet", this);
    }

    public int insertBracelet(Bracelet bracelet) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO Bracelet " +
                            "(bracelet_length, bracelet_material, bracelet_style, bracelet_color) VALUES " +
                            "(:length, :material, :style, :color);");
            updateAndInsertQueryParameterSetter(bracelet, statement);

            if (statement.executeUpdate() > 0) {
                return statement.getGeneratedId("bracelet_id");
            } else {
                throw new InvalidDataException("Bracelet couldn't be created");
            }
        } catch (SQLDuplicateKeyException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void updateBracelet(Bracelet bracelet) throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        if (bracelet.getBraceletId() == 0) {
            throw new InvalidDataException("Can't update if no id is specified for bracelet");
        }

        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "UPDATE Bracelet SET " +
                            "bracelet_length = :length, " +
                            "bracelet_material = :material, " +
                            "bracelet_style = :style, " +
                            "bracelet_color = :color " +
                            "WHERE bracelet_id = :id;");

            statement.setInt("id", bracelet.getBraceletId());
            updateAndInsertQueryParameterSetter(bracelet, statement);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("Unable to find a bracelet with this id!");
            }
        } catch (SQLDuplicateKeyException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void deleteBracelet(int id) throws NotFoundException, OperationFailedException {
        DAOService.deleteModelById(id, "Bracelet", "bracelet");
    }

    private void updateAndInsertQueryParameterSetter(Bracelet bracelet, NamedParameterStatement statement) throws SQLException {
        statement.setInt("length", bracelet.getLength());
        statement.setString("material", bracelet.getMaterial());
        statement.setString("style", bracelet.getStyle());
        statement.setString("color", bracelet.getColor());
    }
}
