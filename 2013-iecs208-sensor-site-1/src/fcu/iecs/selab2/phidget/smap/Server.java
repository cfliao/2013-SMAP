package fcu.iecs.selab2.phidget.smap;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.attentivehome.cpn.node.DefaultNodeManager;
import org.attentivehome.cpn.node.NodeManager;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

public class Server
{

    public static void main(String[] args)
    {
        // start phidget
        InterfaceKitPhidget phidget = null;

        try
        {
            phidget = new InterfaceKitPhidget();
            phidget.openAny();
            System.out.println("waiting for InterfaceKit attachment...");
            phidget.waitForAttachment();
            System.out.println("OK");
        }
        catch (PhidgetException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        if (phidget == null)
            System.exit(1);

        // start PerNode
        NodeManager runner = new DefaultNodeManager();
        runner.setNode(new SensorNode(phidget));
        runner.start();

        // start REST server
        JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();
        restServer.setServiceBean(new RootService());
        restServer.setServiceBean(new DataService(phidget));
        // Add JSONP support
        restServer.getInInterceptors().add(new JsonpInInterceptor());
        restServer.getOutInterceptors().add(new JsonpPreStreamInterceptor());
        restServer.getOutInterceptors().add(new JsonpPostStreamInterceptor());
        // -----------------
        // restServer.setAddress("http://:8192/");
        restServer.setAddress("http://192.168.4.108:8192/");
        //restServer.setAddress("http://localhost:8192/");
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("staticSubresourceResolution", true);
        restServer.setProperties(props);

        restServer.create();
    }

}
