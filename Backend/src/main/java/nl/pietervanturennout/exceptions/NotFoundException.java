package nl.pietervanturennout.exceptions;

import nl.pietervanturennout.exceptions.base_exceptions.BaseClientErrorException;

public class NotFoundException extends BaseClientErrorException {
    public NotFoundException() { }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public javax.ws.rs.NotFoundException getHttpError() throws javax.ws.rs.NotFoundException
    {
        return new javax.ws.rs.NotFoundException(getMessage(), this);
    }
}