package saim.com.now.Model;

/**
 * Created by NREL on 2/20/18.
 */

public class ModelShopVendor {

    String id, service_shop_vendor_id, service_shop_vendor_name, service_shop_vendor_icon, service_shop_vendor_location, service_shop_vendor_mobile;

    public ModelShopVendor(String id, String service_shop_vendor_id, String service_shop_vendor_name, String service_shop_vendor_icon, String service_shop_vendor_location, String service_shop_vendor_mobile) {
        this.id = id;
        this.service_shop_vendor_id = service_shop_vendor_id;
        this.service_shop_vendor_name = service_shop_vendor_name;
        this.service_shop_vendor_icon = service_shop_vendor_icon;
        this.service_shop_vendor_location = service_shop_vendor_location;
        this.service_shop_vendor_mobile = service_shop_vendor_mobile;
    }

    public String getId() {
        return id;
    }

    public String getService_shop_vendor_id() {
        return service_shop_vendor_id;
    }

    public String getService_shop_vendor_name() {
        return service_shop_vendor_name;
    }

    public String getService_shop_vendor_icon() {
        return service_shop_vendor_icon;
    }

    public String getService_shop_vendor_location() {
        return service_shop_vendor_location;
    }

    public String getService_shop_vendor_mobile() {
        return service_shop_vendor_mobile;
    }
}
