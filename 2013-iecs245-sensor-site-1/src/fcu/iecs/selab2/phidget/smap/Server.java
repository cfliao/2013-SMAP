package fcu.iecs.selab2.phidget.smap;

import java.util.Timer;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.attentivehome.cpn.node.DefaultNodeManager;
import org.attentivehome.cpn.node.NodeManager;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.TextLCDPhidget;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

public class Server
{

    public static void main(String[] args) throws PhidgetException, InterruptedException
    {
        final InterfaceKitPhidget phidget = new InterfaceKitPhidget();
        ;

        final TextLCDPhidget lcd = new TextLCDPhidget();

        lcd.openAny();
        System.out.println("Waiting for the TextLCD to be attached...");
        lcd.waitForAttachment();
        System.out.println("LCD OK");
        System.out.println("# Screens: " + lcd.getScreenCount());

        lcd.setScreen(0);
        lcd.setBacklight(true);

        lcd.setDisplayString(0, "FCU IECS SELAB");
        lcd.setDisplayString(1, Utils.getCurrentDate());

        Thread.sleep(2000);

        phidget.addSensorChangeListener(new SensorChangeListener() {

            @Override
            public void sensorChanged(SensorChangeEvent se)
            {
                try
                {
                    if (!lcd.getBacklight())
                    {
                        lcd.setBacklight(true);
                        System.out.println("open backlight.");
                        // close light after 10 seconds
                        new Timer().schedule(new CloseBacklightTask(lcd), 10000);
                    }
                    String temperature = Utils.convertTemperature(phidget.getSensorValue(0));
                    String humidity = Utils.convertHumidity(phidget.getSensorValue(1));
                    String light = Utils.convertIndoorLight(phidget.getSensorValue(2));
                    lcd.setDisplayString(0, "T/H= " + temperature + " dc/ " + humidity + "%");
                    lcd.setDisplayString(1, "L=" + light + " lux(FCUIECS)");
                }
                catch (PhidgetException e)
                {
                    e.printStackTrace();
                }

            }

        });

        phidget.openAny();
        System.out.println("waiting for InterfaceKit attachment...");
        phidget.waitForAttachment();
        System.out.println("Phidget OK");
        new Timer().schedule(new CloseBacklightTask(lcd), 10000);

        // start PerNode
        //NodeManager runner = new DefaultNodeManager();
        //runner.setNode(new SensorNode(phidget));
        //runner.start();

        JAXRSServerFactoryBean restServer = new JAXRSServerFactoryBean();
        // Add JSONP support
        restServer.getInInterceptors().add(new JsonpInInterceptor());
        restServer.getOutInterceptors().add(new JsonpPreStreamInterceptor());
        restServer.getOutInterceptors().add(new JsonpPostStreamInterceptor());
        
        restServer.setServiceBean(new RootService());
        restServer.setServiceBean(new DataService(phidget, lcd));
        restServer.setAddress("http://192.168.4.107:8192/");
        // restServer.setAddress("http://localhost:8192/");
        restServer.create();
    }

}
