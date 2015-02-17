package fcu.smap;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

public class Server
{
    public static void main(String[] args)
    {
        new Server();
    }

    public Server()
    {
        JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();

        List<Class> classes = new ArrayList<Class>();
        classes.add(Reading.class);
        classes.add(Locations.class);
        restServer.setResourceClasses(classes);

        List<Object> managers = new ArrayList<Object>();
        managers.add(new DataManager());
        // managers.add(StatusManager.class);

        restServer.setServiceBeans(managers);

        restServer.setAddress("http://localhost:8192/");
        restServer.create();
    }
}
