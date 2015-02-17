package fcu.iecs.selab2.phidget.smap;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import org.attentivehome.cpn.utils.JsonBuilder;
import org.attentivehome.cpn.utils.MessageUtils;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

public class SendDataTask extends TimerTask
{
    private InterfaceKitPhidget phidget;

    private SensorNode pernode;

    private final static JsonBuilder json = MessageUtils.jsonBuilder();

    private Map<String, String> readingData;

    public SendDataTask(InterfaceKitPhidget phidget, SensorNode pernode)
    {
        this.phidget = phidget;
        this.pernode = pernode;
        readingData = new HashMap<String, String>();
    }

    @Override
    public void run()
    {
        readingData.clear();
        json.reset();

        readingData.put("location", "iecs_208");
        readingData.put("vendor", "phidget");

        try
        {
            // should check validness of sensor input first
            readingData.put("temperature", Utils.convertTemperature(phidget.getSensorValue(6)));
            readingData.put("humidity", Utils.convertHumidity(phidget.getSensorValue(7)));
            readingData.put("light", Utils.convertIndoorLight(phidget.getSensorValue(5)));
            readingData.put("reading_time", Utils.getCurrentTime());
            json.add(readingData);
        }
        catch (PhidgetException e)
        {
            e.printStackTrace();
        }

        if (readingData.size() > 0 && !"0".equals(readingData.get("temperature"))) // valid values
        {
            pernode.getSender().send(json.toJson(), "home.RAW_DATA");
        }

    }

}
