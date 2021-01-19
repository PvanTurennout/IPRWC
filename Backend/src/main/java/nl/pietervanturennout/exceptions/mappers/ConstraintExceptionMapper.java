package nl.pietervanturennout.exceptions.mappers;

import io.dropwizard.jersey.validation.JerseyViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ConstraintExceptionMapper implements ExceptionMapper<JerseyViolationException> {
    @Override
    public Response toResponse(JerseyViolationException exception) {
        return GlobalExceptionMapper.createResponse(exception);
    }
}
