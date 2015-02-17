package fcu.iecs.selab2.phidget.smap;

import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import org.attentivehome.cpn.utils.JsonBuilder;
import org.attentivehome.cpn.utils.MessageUtils;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

public class HumanDetectingTask extends TimerTask
{
    private static final int WINDOW_SIZE = 3;

    private InterfaceKitPhidget phidget;

    private SensorNode pernode;

    private final static JsonBuilder json = MessageUtils.jsonBuilder();

    public HumanDetectingTask(InterfaceKitPhidget phidget, SensorNode pernode)
    {
        this.phidget = phidget;
        this.pernode = pernode;
    }

    private Queue<Double> window = new ArrayBlockingQueue<Double>(WINDOW_SIZE);

    @Override
    public void run()
    {
        int rawValue = 0;
        double distance = 0;

        try
        {
            rawValue = phidget.getSensorValue(0);
            distance = 4800 / (rawValue - 20);
        }
        catch (PhidgetException e)
        {
            e.printStackTrace();
            distance = -1;
        }

        if (window.size() == WINDOW_SIZE)
            window.poll();

        if (distance >= 60 || distance <= 0)
        {
            window.add(Double.valueOf(0));
        } else
        {
            window.add(Double.valueOf(distance));
        }

        int count = 0;

        for (Double d : window)
        {
            if (d > 0)
                count++;
        }

        if (count == WINDOW_SIZE)
        {
            json.add("HumanPresent", "true").add("timestamp", Utils.getCurrentTime());
            pernode.getSender().send(json.toJson(), "home.CONTEXT");
            json.reset();
        }
    }

}
