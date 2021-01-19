package nl.pietervanturennout.sql;

import nl.pietervanturennout.services.DatabaseService;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.*;

/**
 * @author adam_crume
 * source: https://www.javaworld.com/article/2077706/named-parameters-for-preparedstatement.html?page = 2
 */

public class NamedParameterStatement {
    /** The statement this object is wrapping. */
    private final PreparedStatement statement;

    /** Maps parameter names to arrays of ints which are the parameter indices.
     */
    private final Map<String, Integer[]> indexMap;


    /**
     * Creates a NamedParameterStatement.  Wraps a call to
     * c.{@link Connection#prepareStatement(java.lang.String)
    prepareStatement}.
     * @param connection the database connection
     * @param query      the parameterized query
     * @throws SQLException if the statement could not be created
     */
    public NamedParameterStatement(Connection connection, String query) throws
            SQLException {
        indexMap = new HashMap<>();
        String parsedQuery = parse(query, indexMap);
        statement = connection.prepareStatement(parsedQuery, PreparedStatement.RETURN_GENERATED_KEYS);
    }


    /**
     * Parses a query with named parameters.  The parameter-index mappings are
     put into the map, and the
     * parsed query is returned.  DO NOT CALL FROM CLIENT CODE.  This
     method is non-private so JUnit code can
     * test it.
     * @param query    query to parse
     * @param paramMap map to hold parameter-index mappings
     * @return the parsed query
     */
    static String parse(String query, Map<String, Integer[]> paramMap) {
        int length = query.length();
        StringBuilder parsedQuery = new StringBuilder(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean isEscape = false;
        int index=1;
        Map<String, LinkedList<Integer>> temp = new HashMap<>();

        for(int i = 0; i < length; i++) {
            char c = query.charAt(i);

            if (c == '\\') {
                isEscape = true;
                continue;
            }

            if (isEscape) {
                isEscape = false;
            }
            else {
                if (inSingleQuote) {
                    if (c == '\'') {
                        inSingleQuote = false;
                    }
                } else if (inDoubleQuote) {
                    if (c == '"') {
                        inDoubleQuote = false;
                    }
                } else {
                    if (c == '\'') {
                        inSingleQuote = true;
                    } else if (c == '"') {
                        inDoubleQuote = true;
                    } else if (c == ':' && i + 1 < length &&
                            Character.isJavaIdentifierStart(query.charAt(i + 1))) {
                        int j = i + 2;
                        while (j < length && Character.isJavaIdentifierPart(query.charAt(j))) {
                            j++;
                        }
                        String name = query.substring(i + 1, j);
                        c = '?';
                        i += name.length();

                        LinkedList<Integer> indexList = temp.computeIfAbsent(name, k -> new LinkedList<>());
                        indexList.add(index);
                        index++;
                    }
                }
            }

            parsedQuery.append(c);
        }

        paramMap.clear();

        for (Map.Entry<String, LinkedList<Integer>> entry : temp.entrySet()) {
            LinkedList<Integer> list = entry.getValue();
            Integer[] indexes = new Integer[list.size()];
            int i = 0;

            for (Object o : list) {
                Integer x = (Integer) o;
                indexes[i++] = x;
            }

            paramMap.put(entry.getKey(), indexes);
        }

        return parsedQuery.toString();
    }


    /**
     * Returns the indexes for a parameter.
     * @param name parameter name
     * @return parameter indexes
     * @throws IllegalArgumentException if the parameter does not exist
     */
    private Integer[] getIndexes(String name) {
        Integer[] indexes = indexMap.get(name);

        if (indexes == null) {
            throw new IllegalArgumentException("Parameter not found: " + name);
        }

        return indexes;
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setObject(int, java.lang.Object)
     */
    public NamedParameterStatement setObject(String name, Object value) throws SQLException {
        Integer[] indexes = getIndexes(name);

        for (int index : indexes) {
            statement.setObject(index, value);
        }

        return this;
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setString(int, java.lang.String)
     */
    public NamedParameterStatement setString(String name, String value) throws SQLException {
        Integer[] indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setString(index, value);
        }

        return this;
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public NamedParameterStatement setInt(String name, int value) throws SQLException {
        Integer[] indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setInt(index, value);
        }

        return this;
    }

    public NamedParameterStatement setDouble(String name, double value) throws SQLException {
        Integer[] indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setDouble(index, value);
        }

        return this;
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public NamedParameterStatement setLong(String name, long value) throws SQLException {
        Integer[] indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setLong(index, value);
        }

        return this;
    }


    /**
     * Sets a parameter.
     * @param name  parameter name
     * @param value parameter value
     * @throws SQLException if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public NamedParameterStatement setTimestamp(String name, Timestamp value) throws SQLException {
        Integer[] indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setTimestamp(index, value);
        }

        return this;
    }


    /**
     * Returns the underlying statement.
     * @return the statement
     */
    public PreparedStatement getStatement() {
        return statement;
    }


    /**
     * Executes the statement.
     * @return true if the first result is a {@link ResultSet}
     * @throws SQLException if an error occurred
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException {
        try {
            return statement.execute();
        }
        catch (PSQLException e) {
            throw DatabaseService.getInstance().exceptionHelper(e);
        }
    }


    /**
     * Executes the statement, which must be a query.
     * @return the query results
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException {
        try {
            return statement.executeQuery();
        }
        catch (PSQLException e) {
            throw DatabaseService.getInstance().exceptionHelper(e);
        }
    }


    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
     statement;
     * or an SQL statement that returns nothing, such as a DDL statement.
     * @return number of rows affected
     * @throws SQLException if an error occurred
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException {
        try {
            return statement.executeUpdate();
        }
        catch (PSQLException e) {
            throw DatabaseService.getInstance().exceptionHelper(e);
        }
    }

    /**
     * Closes the statement.
     * @throws SQLException if an error occurred
     * @see Statement#close()
     */
    public void close() throws SQLException {
        statement.close();
    }


    /**
     * Adds the current set of parameters as a batch entry.
     * @throws SQLException if something went wrong
     */
    public void addBatch() throws SQLException {
        statement.addBatch();
    }


    /**
     * Executes all of the batched statements.
     *
     * See {@link Statement#executeBatch()} for details.
     * @return update counts for each statement
     * @throws SQLException if something went wrong
     */
    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    public NamedParameterStatement setParameterMap(Map<String, Object> params) throws SQLException {
        for (Map.Entry<String, Object> param : params.entrySet()) {
            setParameter(param.getKey(), param.getValue());
        }

        return this;
    }

    public NamedParameterStatement setParameter(String name, Object value) throws SQLException
    {
        if (value instanceof String)
            return setString(name, (String) value);

        if (value instanceof Integer)
            return setInt(name, (int) value);

        if (value instanceof Long)
            return setLong(name, (long) value);

        if (value instanceof Timestamp)
            return setTimestamp(name, (Timestamp) value);

        if (value instanceof Boolean)
            return setBoolean(name, (boolean) value);

        if (value instanceof Enum)
            return setEnum(name, (Enum<?>) value);

        return setObject(name, value);
    }

    public NamedParameterStatement setBoolean(String name, boolean value) throws SQLException
    {
        Integer[] indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setBoolean(index, value);
        }

        return this;
    }

    public <T extends Enum<?>> NamedParameterStatement setEnum(String name, T enumValue) throws SQLException
    {
        return setString(name, enumValue.name());
    }

    public ResultSet getGeneratedKeys() throws SQLException{
        return statement.getGeneratedKeys();
    }

    public int getGeneratedId(String idColumn) throws SQLException{
        ResultSet result = statement.getGeneratedKeys();
        result.next();
        return result.getInt(idColumn);
    }
}