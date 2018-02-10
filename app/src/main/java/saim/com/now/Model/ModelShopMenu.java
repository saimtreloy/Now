package saim.com.now.Model;

/**
 * Created by NREL on 2/10/18.
 */

public class ModelShopMenu {
    public String id, service_shop_id, service_shop_name, service_shop_icon, service_shop_color, service_shop_type;

    public ModelShopMenu(String id, String service_shop_id, String service_shop_name, String service_shop_icon, String service_shop_color, String service_shop_type) {
        this.id = id;
        this.service_shop_id = service_shop_id;
        this.service_shop_name = service_shop_name;
        this.service_shop_icon = service_shop_icon;
        this.service_shop_color = service_shop_color;
        this.service_shop_type = service_shop_type;
    }

    public String getId() {
        return id;
    }

    public String getService_shop_id() {
        return service_shop_id;
    }

    public String getService_shop_name() {
        return service_shop_name;
    }

    public String getService_shop_icon() {
        return service_shop_icon;
    }

    public String getService_shop_color() {
        return service_shop_color;
    }

    public String getService_shop_type() {
        return service_shop_type;
    }
}
