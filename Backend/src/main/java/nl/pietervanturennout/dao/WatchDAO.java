package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.model.Bracelet;
import nl.pietervanturennout.model.Watch;
import nl.pietervanturennout.services.DAOService;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WatchDAO implements DAO<Watch> {

    private final String TABLE = "full_watch";

    public Watch makeModel(ResultSet result) throws OperationFailedException {
        try {
            Watch watch = new Watch();

            watch.setWatchId(result.getInt("watch_id"));

            watch.setSize(result.getInt("chassis_size"));
            watch.setMaterial(result.getString("chassis_material"));
            watch.setColorDial(result.getString("chassis_color_dial"));
            watch.setColorPointer(result.getString("chassis_color_pointers"));

            watch.setWatchBracelet(
                    new Bracelet(
                            result.getInt("bracelet_id"),
                            result.getInt("bracelet_length"),
                            result.getString("bracelet_material"),
                            result.getString("bracelet_style"),
                            result.getString("bracelet_color")
                    )
            );

            return watch;
        } catch (SQLException e) {
            throw new OperationFailedException(e);
        }
    }

    public List<Watch> getAllWatchesList() throws OperationFailedException, NotFoundException {
        return DAOService.getListOfModels(TABLE, this);
    }

    public Watch searchWatchById(int id) throws OperationFailedException, NotFoundException {
        return DAOService.searchModelById(id, TABLE, "watch", this);
    }

    public int insertWatch(Watch watch, int braceletId)
            throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "INSERT INTO Watch " +
                            "(watch_bracelet_id, chassis_material, chassis_size, " +
                            "chassis_color_dial, chassis_color_pointers) " +
                            "VALUES (:bracelet_id, :material, :size, :dial, :pointers);");
            updateAndInsertQueryParameterSetter(watch, braceletId, statement);

            if (statement.executeUpdate() > 0) {
                return statement.getGeneratedId("watch_id");
            } else {
                throw new InvalidDataException("Bracelet couldn't be created");
            }
        } catch (SQLDuplicateKeyException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void updateWatch(Watch watch)
            throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        if (watch.getWatchId() == 0) {
            throw new InvalidDataException("Can't update if no id is specified for watch");
        }

        try{
            NamedParameterStatement statement = DatabaseService.getInstance().createNamedPreparedStatement(
                    "UPDATE Watch SET " +
                            "watch_bracelet_id = :bracelet_id, " +
                            "chassis_material = :material, " +
                            "chassis_size = :size, " +
                            "chassis_color_dial = :dial, " +
                            "chassis_color_pointers = :pointers " +
                            "WHERE watch_id = :id;");

            statement.setInt("id", watch.getWatchId());
            updateAndInsertQueryParameterSetter(watch, watch.getWatchBracelet().getBraceletId(), statement);

            if (statement.executeUpdate() == 0) {
                throw new NotFoundException("No account with this id exists");
            }
        } catch (SQLDuplicateKeyException e) {
            throw new DuplicateEntryException(e);
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }

    public void deleteWatch(int watchId) throws OperationFailedException, NotFoundException {
        DAOService.deleteModelById(watchId, "Watch", "watch");
    }

    private void updateAndInsertQueryParameterSetter(Watch watch, int braceletId, NamedParameterStatement statement)
            throws SQLException {
        statement.setInt("bracelet_id", braceletId);
        statement.setString("material", watch.getMaterial());
        statement.setInt("size", watch.getSize());
        statement.setString("dial", watch.getColorDial());
        statement.setString("pointers", watch.getColorPointer());
    }


}