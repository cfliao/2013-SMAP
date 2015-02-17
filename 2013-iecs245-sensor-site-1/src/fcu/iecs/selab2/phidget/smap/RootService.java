package fcu.iecs.selab2.phidget.smap;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
@Produces("application/json")
public class RootService
{

    private ObjectMapper mapper = Utils.getJsonObjectMapper();

    @GET
    @Path("/")
    public Response list()
    {
        ResponseBuilder builder = null;

        String[] rootList = new String[] { "data", "status", "reporting", "context" };

        try
        {
            builder = Response.ok(mapper.writeValueAsString(rootList));
        }
        catch (JsonProcessingException e)
        {
            builder = Response.serverError();
            e.printStackTrace();
        }

        return builder.build();
    }

}
