package nl.pietervanturennout.resource;

import io.dropwizard.auth.Auth;
import nl.pietervanturennout.api.responses.IdResponse;
import nl.pietervanturennout.api.responses.ProductListResponse;
import nl.pietervanturennout.api.authentication.AuthenticateObject;
import nl.pietervanturennout.api.requests.ProductRequest;
import nl.pietervanturennout.api.requests.ProductUpdateRequest;
import nl.pietervanturennout.api.responses.ProductResponse;
import nl.pietervanturennout.controller.ProductController;
import nl.pietervanturennout.exceptions.DuplicateEntryException;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    public ProductListResponse getAllProducts()
            throws OperationFailedException, NotFoundException {
        return new ProductListResponse(ProductController.getInstance().getAllProducts(), false);
    }

    @POST
    @Path("/list")
    public ProductListResponse getProductList(@NotNull List<Integer> productIds)
            throws NotFoundException, OperationFailedException {
        return new ProductListResponse(
                ProductController.getInstance().getProductsFromIdList(productIds), false);
    }

    @GET
    @Path("/{id}")
    public ProductResponse getProductById(@NotNull @Min(1) @PathParam("id") int productId)
            throws OperationFailedException, NotFoundException {
        return new ProductResponse(ProductController.getInstance().getProductFromId(productId), true);
    }

    @GET
    @Path("/highlighted-products")
    public ProductListResponse getHighlightedProducts() throws OperationFailedException, NotFoundException {
        return new ProductListResponse(ProductController.getInstance().getHighlightedProducts());
    }

    @GET
    @Path("/top-selling-products")
    public ProductListResponse getTopSellingProducts() throws OperationFailedException, NotFoundException {
        return new ProductListResponse(ProductController.getInstance().getTopSellingProducts(), false);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("product_crud")
    public IdResponse createProduct(@Valid ProductRequest request, @Auth AuthenticateObject someone)
            throws OperationFailedException, DuplicateEntryException, InvalidDataException {
        return new IdResponse(ProductController.getInstance().createProductFromRequest(request), "product");
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("product_crud")
    public void updateProduct(@Valid ProductUpdateRequest request, @Auth AuthenticateObject someone)
            throws OperationFailedException, DuplicateEntryException, InvalidDataException, NotFoundException {
        ProductController.getInstance().updateProductFromRequest(request);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("product_crud")
    public void deleteProduct(@NotNull @Min(1) @PathParam("id") int productId, @Auth AuthenticateObject someone)
            throws OperationFailedException, NotFoundException{
        ProductController.getInstance().deleteProductById(productId);
    }
}
