package nl.pietervanturennout.exceptions;


import nl.pietervanturennout.exceptions.base_exceptions.BaseClientErrorException;

import javax.ws.rs.ClientErrorException;

public class DuplicateEntryException extends BaseClientErrorException {
    private static final int HTTP_CODE = 409;

    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntryException(Throwable cause) {
        super(cause);
    }

    public DuplicateEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ClientErrorException getHttpError()
    {
        return new ClientErrorException(HTTP_CODE, this);
    }
}
