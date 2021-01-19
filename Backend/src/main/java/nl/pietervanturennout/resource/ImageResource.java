package nl.pietervanturennout.resource;

import nl.pietervanturennout.api.responses.StringResponse;
import nl.pietervanturennout.controller.ImageController;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Path("/api/image")
public class ImageResource {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("product_crud")
    public StringResponse uploadImage(
            @FormDataParam("productImage") final InputStream image,
            @FormDataParam("productImageFileName") final String fileName
    ) throws OperationFailedException, InvalidDataException {
        return new StringResponse("imageFileName", ImageController.getInstance().upload(image, fileName));
    }
}
