package nl.pietervanturennout.resource;

import io.dropwizard.auth.Auth;
import nl.pietervanturennout.api.authentication.AuthenticateObject;
import nl.pietervanturennout.controller.WishListController;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.utils.JwtUtil;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/wishlist")
public class WishListResource {

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void addToWishList(@Auth AuthenticateObject user, @NotNull int productId) throws NotFoundException, OperationFailedException, InvalidDataException, DuplicateEntryException {
        WishListController.getInstance().addProductToAccount(JwtUtil.getInstance().getAccountIdFromJwt(user.getToken()), productId);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeFromWishList(@Auth AuthenticateObject user, @NotNull int productId) throws NotFoundException, OperationFailedException {
        WishListController.getInstance().deleteProductFromAccount(JwtUtil.getInstance().getAccountIdFromJwt(user.getToken()), productId);
    }
}
