package fcu.iecs.selab2.phidget.smap;

import java.util.Timer;

import org.attentivehome.cpn.mom.Id;
import org.attentivehome.cpn.mom.Init;
import org.attentivehome.cpn.mom.ListeningTo;
import org.attentivehome.cpn.mom.MOM;
import org.attentivehome.cpn.node.PerNodeBase;

import com.phidgets.InterfaceKitPhidget;

@Id("http://selab2.iecs.fcu.edu.tw/pernode/208door")
@MOM("failover:(tcp://192.168.4.100:61616)")
@ListeningTo("home.COMMAND")
public class SensorNode extends PerNodeBase
{

    private InterfaceKitPhidget phidget;

    private Timer timer;

    public SensorNode(InterfaceKitPhidget phidget)
    {
        this.phidget = phidget;
        timer = new Timer();
    }

    @Init
    public void init()
    {
        timer.schedule(new HumanDetectingTask(phidget, this), 1000, 1000);
        timer.schedule(new InsufficientLightDetectingTask(phidget, this), 1000, 6000);
        timer.schedule(new SendDataTask(phidget, this), 1000, 60000);
    }

    @Override
    public void processMessage(String arg0)
    {
        // do nothing
    }

}
