package nl.pietervanturennout.sql;

import java.sql.SQLIntegrityConstraintViolationException;

public class SQLKeyNotExistException extends SQLIntegrityConstraintViolationException {
    public SQLKeyNotExistException() {
    }

    public SQLKeyNotExistException(String reason) {
        super(reason);
    }

    public SQLKeyNotExistException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public SQLKeyNotExistException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public SQLKeyNotExistException(Throwable cause) {
        super(cause);
    }

    public SQLKeyNotExistException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public SQLKeyNotExistException(String reason, String SQLState, Throwable cause) {
        super(reason, SQLState, cause);
    }

    public SQLKeyNotExistException(String reason, String SQLState, int vendorCode, Throwable cause) {
        super(reason, SQLState, vendorCode, cause);
    }
}