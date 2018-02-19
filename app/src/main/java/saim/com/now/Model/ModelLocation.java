package saim.com.now.Model;

/**
 * Created by NREL on 2/19/18.
 */

public class ModelLocation {
    String id, location_name, location_lat, location_lon;

    public ModelLocation(String id, String location_name, String location_lat, String location_lon) {
        this.id = id;
        this.location_name = location_name;
        this.location_lat = location_lat;
        this.location_lon = location_lon;
    }

    public String getId() {
        return id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public String getLocation_lon() {
        return location_lon;
    }
}
