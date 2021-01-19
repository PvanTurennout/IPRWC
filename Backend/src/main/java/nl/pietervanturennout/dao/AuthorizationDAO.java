package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.services.DatabaseService;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.utils.types.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationDAO {
    public boolean roleCheck(String role, Group group) throws OperationFailedException {
        try{
            ResultSet result = DatabaseService.getInstance().createNamedPreparedStatement(
                    "SELECT COUNT(group_id) FROM Role_Group_Junction " +
                            "WHERE group_id = :group AND role_id = " +
                            "(SELECT role_id FROM Role WHERE role_name = :role)")
                    .setString("role", role)
                    .setInt("group", group.getGroupId())
                    .executeQuery();
            result.next();

            return result.getInt("count") > 0;
        } catch (SQLException e){
            throw new OperationFailedException(e);
        }
    }
}
