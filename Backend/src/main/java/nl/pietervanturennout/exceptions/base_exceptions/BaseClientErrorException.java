package nl.pietervanturennout.exceptions.base_exceptions;

import javax.ws.rs.ClientErrorException;

public abstract class BaseClientErrorException extends BaseHttpException {
    public BaseClientErrorException() {
    }

    public BaseClientErrorException(String message) {
        super(message);
    }

    public BaseClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseClientErrorException(Throwable cause) {
        super(cause);
    }

    public BaseClientErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ClientErrorException getHttpError() {
        return new ClientErrorException(400, this);
    }
}
