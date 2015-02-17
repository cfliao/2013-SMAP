package fcu.smap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Locations
{
    List<String> locations = new ArrayList<String>();

    public List<String> getLocations()
    {
        return locations;
    }

    public void setLocations(List<String> locations)
    {
        this.locations = locations;
    }

    public void addLocation(String location)
    {
        locations.add(location);
    }

}
