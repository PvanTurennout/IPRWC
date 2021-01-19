package nl.pietervanturennout.services;

import io.dropwizard.db.DataSourceFactory;
import nl.pietervanturennout.IprwcBackendMain;
import nl.pietervanturennout.sql.NamedParameterStatement;
import nl.pietervanturennout.sql.SQLDuplicateKeyException;
import nl.pietervanturennout.sql.SQLKeyNotExistException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseService {

    private static final DatabaseService instance;
    static { instance = new DatabaseService(); }
    public static DatabaseService getInstance() {
        return instance;
    }

    private Connection connection;
    private DataSourceFactory configuration;

    private DatabaseService(){}

    public Connection getConnection() throws SQLException {
        if (null == connection || connection.isClosed()) {
            try {
                Class.forName(configuration.getDriverClass());
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            connection = DriverManager.getConnection(
                    configuration.getUrl(),
                    configuration.getUser(),
                    configuration.getPassword()
            );
        }

        return connection;
    }

    public void setConfiguration(DataSourceFactory configuration) {
        this.configuration = configuration;
    }

    public NamedParameterStatement createNamedPreparedStatement(String query) throws SQLException
    {
        return new NamedParameterStatement(getConnection(), query);
    }

    public NamedParameterStatement createNamedPreparedStatement(String query, Map<String, Object> params) throws SQLException
    {
        NamedParameterStatement statement = createNamedPreparedStatement(query);
        statement.setParameterMap(params);
        return statement;
    }

    public PreparedStatement createPreparedStatement(String query) throws SQLException
    {
        return getConnection().prepareStatement(query);
    }

    public boolean ping() throws SQLException {
        String validationQuery = String.valueOf(configuration.getValidationQuery());

        return createPreparedStatement(validationQuery)
                .executeQuery()
                .next();
    }

    public SQLException exceptionHelper(PSQLException e) {
        switch (e.getSQLState()) {
            case "23503": return new SQLKeyNotExistException(e);
            case "23505": return new SQLDuplicateKeyException(e);
            default:      return e;
        }
    }
}
