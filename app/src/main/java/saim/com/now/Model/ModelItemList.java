package saim.com.now.Model;

/**
 * Created by NREL on 2/14/18.
 */

public class ModelItemList {
    public String id, item_id, item_name, item_price, item_d_price, item_quantity, item_icon, item_vendor, item_vendor_icon, cartQ, total_price;

    public ModelItemList(String id, String item_id, String item_name, String item_price, String item_d_price, String item_quantity, String item_icon, String item_vendor) {
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_d_price = item_d_price;
        this.item_quantity = item_quantity;
        this.item_icon = item_icon;
        this.item_vendor = item_vendor;
    }

    public ModelItemList(String id, String item_id, String item_name, String item_price, String item_d_price, String item_quantity, String item_icon, String item_vendor, String item_vendor_icon) {
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_d_price = item_d_price;
        this.item_quantity = item_quantity;
        this.item_icon = item_icon;
        this.item_vendor = item_vendor;
        this.item_vendor_icon = item_vendor_icon;
    }

    public ModelItemList(String id, String item_id, String item_name, String item_price, String item_d_price, String item_quantity, String item_icon, String item_vendor, String item_vendor_icon, String cartQ) {
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_d_price = item_d_price;
        this.item_quantity = item_quantity;
        this.item_icon = item_icon;
        this.item_vendor = item_vendor;
        this.item_vendor_icon = item_vendor_icon;
        this.cartQ = cartQ;
    }

    public ModelItemList(String id, String item_id, String item_name, String item_price, String item_d_price, String item_quantity, String item_icon, String item_vendor, String item_vendor_icon, String cartQ, String total_price) {
        this.id = id;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_d_price = item_d_price;
        this.item_quantity = item_quantity;
        this.item_icon = item_icon;
        this.item_vendor = item_vendor;
        this.item_vendor_icon = item_vendor_icon;
        this.cartQ = cartQ;
        this.total_price = total_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getCartQ() {
        return cartQ;
    }

    public String getId() {
        return id;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public String getItem_d_price() {
        return item_d_price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public String getItem_icon() {
        return item_icon;
    }

    public String getItem_vendor() {
        return item_vendor;
    }

    public String getItem_vendor_icon() {
        return item_vendor_icon;
    }

}
