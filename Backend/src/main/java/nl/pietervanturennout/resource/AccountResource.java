package nl.pietervanturennout.resource;

import io.dropwizard.auth.Auth;
import nl.pietervanturennout.api.authentication.AuthenticateObject;
import nl.pietervanturennout.api.requests.AuthenticationRequest;
import nl.pietervanturennout.api.responses.AccountResponse;
import nl.pietervanturennout.api.responses.IdResponse;
import nl.pietervanturennout.controller.AccountController;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @GET
    @Path("/{id}")
    @RolesAllowed("account_read")
    public AccountResponse getAccount(@PathParam("id") int personId) throws OperationFailedException, NotFoundException {
        return new AccountResponse(AccountController.getInstance().getAccountFromId(personId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public IdResponse register(@Valid AuthenticationRequest authentication) throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        return new IdResponse(AccountController.getInstance().registerAccount(authentication), "account");
    }
}
