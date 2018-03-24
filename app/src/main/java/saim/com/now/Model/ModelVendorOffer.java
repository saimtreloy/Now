package saim.com.now.Model;

/**
 * Created by NREL on 3/24/18.
 */

public class ModelVendorOffer {
    String id, service_shop_vendor_id, offer_url;

    public ModelVendorOffer(String id, String service_shop_vendor_id, String offer_url) {
        this.id = id;
        this.service_shop_vendor_id = service_shop_vendor_id;
        this.offer_url = offer_url;
    }

    public String getId() {
        return id;
    }

    public String getService_shop_vendor_id() {
        return service_shop_vendor_id;
    }

    public String getOffer_url() {
        return offer_url;
    }
}
