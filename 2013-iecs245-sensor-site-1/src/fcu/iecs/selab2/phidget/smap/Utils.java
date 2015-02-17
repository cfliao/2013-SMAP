package fcu.iecs.selab2.phidget.smap;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils
{

    private static ObjectMapper mapper;

    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");;

    private final static SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");

    public static ObjectMapper getJsonObjectMapper()
    {
        if (mapper == null)
            mapper = new ObjectMapper();

        return mapper;
    }

    public static String getRoundedString(double input, int numberOfDigits)
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setRoundingMode(RoundingMode.HALF_UP);
        format.setMaximumFractionDigits(numberOfDigits);
        return format.format(input);
    }

    public static String convertIndoorLight(int sensorValue)
    {
        return getRoundedString((sensorValue * 1.9659) + 43.492, 1);
    }

    public static String convertTemperature(int sensorValue)
    {
        return getRoundedString((sensorValue * 0.2222) - 61.111, 1);
    }

    public static String convertHumidity(int sensorValue)
    {
        return getRoundedString((sensorValue * 0.1906) - 40.2, 1);
    }

    public static String getCurrentTime()
    {
        return formatter.format(new Date());
    }

    public static String getCurrentDate()
    {
        return formatter1.format(new Date());
    }

}
