package nl.pietervanturennout.exceptions;

import nl.pietervanturennout.exceptions.base_exceptions.BaseClientErrorException;

import javax.ws.rs.ClientErrorException;

public class InvalidDataException extends BaseClientErrorException {
    private static final int HTTP_CODE = 400;

    public InvalidDataException() { }

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }

    @Override
    public ClientErrorException getHttpError() {
        return new ClientErrorException(HTTP_CODE, this);
    }
}
