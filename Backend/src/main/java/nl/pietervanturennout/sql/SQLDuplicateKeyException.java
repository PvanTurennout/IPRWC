package nl.pietervanturennout.sql;

import java.sql.SQLIntegrityConstraintViolationException;

public class SQLDuplicateKeyException extends SQLIntegrityConstraintViolationException {
    public SQLDuplicateKeyException() {
    }

    public SQLDuplicateKeyException(String reason) {
        super(reason);
    }

    public SQLDuplicateKeyException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public SQLDuplicateKeyException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public SQLDuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public SQLDuplicateKeyException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public SQLDuplicateKeyException(String reason, String SQLState, Throwable cause) {
        super(reason, SQLState, cause);
    }

    public SQLDuplicateKeyException(String reason, String SQLState, int vendorCode, Throwable cause) {
        super(reason, SQLState, vendorCode, cause);
    }
}
