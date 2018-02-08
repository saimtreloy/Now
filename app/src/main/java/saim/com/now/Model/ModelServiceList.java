package saim.com.now.Model;

/**
 * Created by NREL on 2/7/18.
 */

public class ModelServiceList {
    public String id, service_id, service_name, service_icon, service_color,service_status;

    public ModelServiceList(String id, String service_id, String service_name, String service_icon, String service_color, String service_status) {
        this.id = id;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_icon = service_icon;
        this.service_color = service_color;
        this.service_status = service_status;
    }

    public String getId() {
        return id;
    }

    public String getService_id() {
        return service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public String getService_icon() {
        return service_icon;
    }

    public String getService_color() {
        return service_color;
    }

    public String getService_status() {
        return service_status;
    }
}
