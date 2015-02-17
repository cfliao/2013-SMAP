package fcu.iecs.selab2.phidget.smap;

import java.util.TimerTask;

import com.phidgets.PhidgetException;
import com.phidgets.TextLCDPhidget;

public class CloseBacklightTask extends TimerTask
{
    private TextLCDPhidget lcd;

    public CloseBacklightTask(TextLCDPhidget lcd)
    {
        this.lcd = lcd;
    }

    @Override
    public void run()
    {
        try
        {
            lcd.setBacklight(false);
            System.out.println("close backlight.");
        }
        catch (PhidgetException e)
        {
            e.printStackTrace();
        }
    }

}
