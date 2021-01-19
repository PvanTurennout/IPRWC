package nl.pietervanturennout.exceptions;

import nl.pietervanturennout.exceptions.base_exceptions.BaseServerErrorException;

import javax.ws.rs.ServerErrorException;

public class OperationFailedException extends BaseServerErrorException {
    private static final int HTTP_CODE = 500;

    public OperationFailedException() {
    }

    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationFailedException(Throwable cause) {
        super(cause);
    }

    public OperationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ServerErrorException getHttpError() {
        return new ServerErrorException(HTTP_CODE, this);
    }
}