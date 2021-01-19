package nl.pietervanturennout.resource;

import nl.pietervanturennout.api.requests.RefreshRequest;
import nl.pietervanturennout.api.responses.AuthenticationResponse;
import nl.pietervanturennout.api.responses.StringResponse;
import nl.pietervanturennout.controller.AuthenticationController;
import nl.pietervanturennout.api.requests.AuthenticationRequest;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.exceptions.UnAuthorizedException;
import nl.pietervanturennout.utils.JwtUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/api/authentication")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public AuthenticationResponse login(@Valid AuthenticationRequest authentication)
            throws UnAuthorizedException, InvalidDataException {
        return AuthenticationController.getInstance().login(authentication);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/refresh")
    public StringResponse refresh(@Valid RefreshRequest refreshRequest)
            throws OperationFailedException, InvalidDataException, UnAuthorizedException, NotFoundException {
        return new StringResponse("freshToken", AuthenticationController.getInstance().refreshJwt(refreshRequest.getRefreshToken()));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/validate")
    public void validateToken( @NotNull String token) throws UnAuthorizedException {
        JwtUtil.getInstance().verifyJwt(token, false);
    }

}