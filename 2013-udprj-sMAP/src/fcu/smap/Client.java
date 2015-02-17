package fcu.smap;

import org.apache.cxf.jaxrs.client.WebClient;

public class Client
{
    public static void main(String[] args)
    {
        WebClient client = WebClient.create("http://localhost:8192");
        // URL Format /data/<location>/<type>/<vendor>/<object>.
        Reading reading = client.path("/data/iecs.r208/thermo/phidget/reading").accept("application/xml").get(Reading.class);
        System.out.println("The reading is:" + reading.getReading());
    }
}
