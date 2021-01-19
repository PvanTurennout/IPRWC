package nl.pietervanturennout.exceptions;

import nl.pietervanturennout.exceptions.base_exceptions.BaseClientErrorException;

import javax.ws.rs.ClientErrorException;

public class UnAuthorizedException extends BaseClientErrorException {

    private static final int HTTP_CODE = 401;

    public UnAuthorizedException() {
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException(Throwable cause) {
        super(cause);
    }

    public UnAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ClientErrorException getHttpError() {
        return new ClientErrorException(HTTP_CODE, this);
    }
}
