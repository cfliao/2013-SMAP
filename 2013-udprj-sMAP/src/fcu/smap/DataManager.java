package fcu.smap;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

//URL Format /data/<location>/<type>/<vendor>/<object>.
//Example:   /data/iecs.r208/thermo/phidget/reading
@Path("/data")
@Produces("application/xml")
public class DataManager
{
    @GET
    @Path("/")
    public Locations getLocation()
    {
        Locations locations = new Locations();
        locations.addLocation("iecs.r203");
        locations.addLocation("iecs.r208");
        return locations;
    }

    @GET
    @Path("/iecs.r208/thermo/phidget/reading")
    public Reading getThermoPhidgetReading()
    {
        Reading reading = new Reading();

        reading.setReading(25.0d);
        reading.setTimeStamp(new Date());
        reading.setVersion("1");
        reading.setUuid(UUID.randomUUID().toString());

        return reading;
    }

    @GET
    @Path("/iecs.r208/thermo/phidget/reading2")
    @Produces("application/json")
    public void getThermoPhidgetReading2(@Context final HttpServletResponse response)
    {
        String result = "{\"key\":\"value\"}";

        try
        {
            response.getOutputStream().println(result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/iecs.r203/power/abc/reading")
    public Reading getPowerAbcReading()
    {
        Reading reading = new Reading();

        reading.setReading(2000);
        reading.setTimeStamp(new Date());
        reading.setVersion("1");
        reading.setUuid(UUID.randomUUID().toString());

        return reading;
    }

}
