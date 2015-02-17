package fcu.iecs.selab2.phidget.smap;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.TextLCDPhidget;

@Path("/data")
@Produces("application/json")
public class DataService
{

    private ObjectMapper mapper = Utils.getJsonObjectMapper();

    private InterfaceKitPhidget phidget;

    private TextLCDPhidget lcd;

    public DataService(InterfaceKitPhidget phidget, TextLCDPhidget lcd)
    {
        this.phidget = phidget;
        this.lcd = lcd;
    }
    
      @GET
    @Path("/")
    public Response listSensePoints()
    {
        ResponseBuilder builder = null;

        return builder.build();
    }

    @GET
    @Path("/245/sensor/{channel}/reading")
    public Response sensorRreading(@PathParam("channel") String channel)
    {

        ResponseBuilder builder = null;
        Map<String, String> readingData = new HashMap<String, String>();

        try
        {

            if ("temperature".equals(channel))
            {
                readingData.put("location", "iecs_245_site1");
                readingData.put("reading", Utils.convertTemperature(phidget.getSensorValue(0)));
                readingData.put("timestamp", Utils.getCurrentTime());
            } else if ("humidity".equals(channel))
            {
                readingData.put("location", "iecs_245_site1");
                readingData.put("reading", Utils.convertHumidity(phidget.getSensorValue(1)));
                readingData.put("timestamp", Utils.getCurrentTime());
            } else if ("light".equals(channel))
            {
                readingData.put("location", "iecs_245_site1");
                readingData.put("reading", Utils.convertIndoorLight(phidget.getSensorValue(2)));
                readingData.put("timestamp", Utils.getCurrentTime());
            }

            if (!readingData.isEmpty())
            {
                builder = Response.ok(mapper.writeValueAsString(readingData));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            builder = Response.serverError();
        }
        // what if location == * ?

        return builder.build();
    }

    @GET
    @Path("/245/sensor/all")
    public Response all()
    {
        ResponseBuilder builder = null;
        Map<String, String> readingData = new HashMap<String, String>();

        try
        {
            readingData.put("location", "iecs_245_site1");
            readingData.put("vendor", "phidget");
            readingData.put("temperature", Utils.convertTemperature(phidget.getSensorValue(0)));
            readingData.put("humidity", Utils.convertHumidity(phidget.getSensorValue(1)));
            readingData.put("light", Utils.convertIndoorLight(phidget.getSensorValue(2)));
            readingData.put("reading_time", Utils.getCurrentTime());

            if (!readingData.isEmpty())
            {
                builder = Response.ok(mapper.writeValueAsString(readingData));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            builder = Response.serverError();
        }

        return builder.build();
    }
    
    @GET
    @Path("/{sensePoint}/sensor/{channel}/format")
    public Response sensorFormat(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

    @GET
    @Path("/{sensePoint}/sensor/{channel}/parameter")
    public Response sensorParameter(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

    @GET
    @Path("/{sensePoint}/sensor/{channel}/profile")
    public Response sensorProfile(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

    @GET
    @Path("/{sensePoint}/meter/{channel}/reading")
    public Response meterRreading(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

    @GET
    @Path("/{sensePoint}/meter/{channel}/format")
    public Response meterFormat(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

    @GET
    @Path("/{sensePoint}/meter/{channel}/parameter")
    public Response meterParameter(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

    @GET
    @Path("/{sensePoint}/meter/{channel}/profile")
    public Response meterProfile(@PathParam("sensePoint") String location, @PathParam("channel") String channel)
    {
        return null;
    }

}
