package nl.pietervanturennout.resource;

import nl.pietervanturennout.api.requests.BraceletRequest;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("api/coffee")
@Produces(MediaType.APPLICATION_JSON)
public class CoffeeResource {

    @GET
    public Response getCoffee(){
        return Response.status(418).entity("No Coffee Can Be Ordered Here").build();
    }

    @POST
    public Response testValidator(@Valid BraceletRequest request) { return Response.status(200).entity(request.getColor() + " is valid!").build(); }
}
