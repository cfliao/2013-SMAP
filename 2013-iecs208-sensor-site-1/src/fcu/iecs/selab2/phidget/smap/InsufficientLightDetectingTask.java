package fcu.iecs.selab2.phidget.smap;

import java.util.Calendar;
import java.util.Queue;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

import org.attentivehome.cpn.utils.JsonBuilder;
import org.attentivehome.cpn.utils.MessageUtils;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

public class InsufficientLightDetectingTask extends TimerTask
{
    private static final int LIGHT_CHANNEL_NUMBER = 5;

    private static final int THREADSHOLD = 200;

    private static final int WINDOW_SIZE = 6;

    private InterfaceKitPhidget phidget;

    private SensorNode pernode;

    private final static JsonBuilder json = MessageUtils.jsonBuilder();

    public InsufficientLightDetectingTask(InterfaceKitPhidget phidget, SensorNode pernode)
    {
        this.phidget = phidget;
        this.pernode = pernode;
    }

    private Queue<Double> window = new ArrayBlockingQueue<Double>(WINDOW_SIZE);

    @Override
    public void run()
    {
        double lux = 0;

        try
        {
            lux = (phidget.getSensorValue(LIGHT_CHANNEL_NUMBER) * 1.9659) + 43.492;
        }
        catch (PhidgetException e)
        {
            e.printStackTrace();
            lux = -1;
        }

        if (window.size() == WINDOW_SIZE)
            window.poll();

        if (lux <= 0)
        {
            window.add(Double.valueOf(0));
        } else
        {
            window.add(Double.valueOf(lux));
        }

        int count = 0;

        for (Double d : window)
        {
            if (d < THREADSHOLD && d > 0)
                count++;
            // System.out.println(d);
        }

        if (count == WINDOW_SIZE) // and time is not in the range of 2400-0700
        {
            Calendar c = Calendar.getInstance();
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
            if (hourOfDay < 17 && hourOfDay > 10)
            {
                json.add("value", "208-LIGHT-1_ON");
                pernode.getSender().send(json.toJson(), "home.COMMAND");
                json.reset();

            }
        }
    }

    public static void main(String[] args)
    {
        // Calendar c = Calendar.getInstance();
        // System.out.println(c.get(Calendar.HOUR_OF_DAY));
    }
}
