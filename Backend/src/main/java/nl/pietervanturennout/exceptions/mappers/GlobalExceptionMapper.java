package nl.pietervanturennout.exceptions.mappers;

import io.dropwizard.jersey.validation.JerseyViolationException;
import nl.pietervanturennout.utils.constraints.Validator;
import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.exceptions.base_exceptions.*;
import nl.pietervanturennout.utils.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    private static final String LOGGER_NAME = "response-exceptions";
    private static final Logger LOGGER = LoggerFactory.getLogger(LOGGER_NAME);

    private static boolean detailedMessage = false;

    public static void setDetailedMessage(boolean detailedMessage) {
        GlobalExceptionMapper.detailedMessage = detailedMessage;
    }

    public static boolean isDetailedMessage() {
        return detailedMessage;
    }

    public static Response createResponse(Throwable exception) {
        int status = 500;

        Tuple<Boolean, Integer> jerseyViolation = checkJerseyViolation(exception);

        if (jerseyViolation.getValueA()) {
            LOGGER.info("Client error occurred", exception);
            status = jerseyViolation.getValueB();
        }
        else if (exception instanceof BaseHttpException) {
            WebApplicationException httpException = ((BaseHttpException) exception).getHttpError();
            Response originalResponse = httpException.getResponse();
            status = originalResponse.getStatus();

            if (exception instanceof BaseServerErrorException)
                LOGGER.error("Request processing has thrown an exception.", exception);
            else if (exception instanceof BaseClientErrorException)
                LOGGER.info("Client error occurred", exception);
            else
                LOGGER.warn("Unknown error type", exception);

            exception = httpException;
        }
        else if (exception instanceof ClientErrorException) {
            LOGGER.info("Client error occurred", exception);
            status = ((ClientErrorException) exception).getResponse().getStatus();
        }
        else if (exception instanceof WebApplicationException) {
            LOGGER.error("Request processing has thrown an exception.", exception);
            status = ((WebApplicationException) exception).getResponse().getStatus();
        }
        else {
            LOGGER.error("Request processing has thrown an exception.", exception);
        }

        return Response
                .status(status)
                .type("application/json")
                .entity(buildResponseEntity(status, exception))
                .build();
    }

    private static Tuple<Boolean, Integer> checkJerseyViolation(Throwable throwable) {
        if (throwable instanceof JerseyViolationException) {
//            if (isCausingConstraint((JerseyViolationException) throwable, Authenticated.class)) {
//                return new Tuple<>(true, 401);
//            }

            if (isCausingConstraint((JerseyViolationException) throwable)) {
                return new Tuple<>(true, 400);
            }

            return new Tuple<>(true, 400);
        }

        return new Tuple<>(false, 500);
    }

    private static boolean isCausingConstraint(JerseyViolationException e) {
        for (ConstraintViolation<?> violation: e.getConstraintViolations()) {
            ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();
            Object annotation = descriptor.getAnnotation();
            Class<?> clazz = annotation.getClass();
            if (Validator.class.isAssignableFrom(clazz))
                return true;
        }

        return false;
    }

    private static ResponseObject buildResponseEntity(int code, Throwable e) {
        ResponseObject response = new ResponseObject();
        response.put("code", code);
        response.put("message", e.getMessage());

        if (detailedMessage) {
            response.put("stackTrace", e.getStackTrace());
        }

        return response;
    }

    @Context
    private HttpServletRequest request;

    @Override
    public Response toResponse(Throwable exception) {
        return createResponse(exception);
    }
}
