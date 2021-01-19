package nl.pietervanturennout.dao;

import nl.pietervanturennout.exceptions.OperationFailedException;

import java.sql.ResultSet;

public interface DAO<T> {
    T makeModel(ResultSet result) throws OperationFailedException;
}
