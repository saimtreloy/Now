package saim.com.now.Utilities;

/**
 * Created by NREL on 2/5/18.
 */

public class ApiURL {

    public static String link_header = "http://www.globalearnmoney.com/now_api/";

    public static String Location = link_header + "location.php";                 //user_email, user_pass
    public static String Login = link_header + "login.php";                 //user_email, user_pass
    public static String Register = link_header + "register.php";           //user_name, user_email, user_mobile, user_pass
    public static String Service_List = link_header + "service_list.php";
    public static String Vendor_List = link_header + "service_vendor_list.php";                       //service_shop_vendor_location
    public static String Service_Shop_List = link_header + "service_shop_list.php";
    public static String Service_Shop_Cat_List = link_header + "service_shop_cat_list.php";     //service_shop_id
    public static String Service_Shop_Item_List = link_header + "service_shop_item_list.php";     //service_shop_id, service_shop_type
    public static String Service_Shop_Place_Order = link_header + "service_shop_order_user.php";     //service_shop_id, service_shop_type
    public static String Service_Shop_Place_Order_Image = link_header + "service_shop_order_image.php";     //service_shop_id, service_shop_type
    public static String Service_Shop_Item_Search = link_header + "service_shop_item_search.php";     //service_shop_search, item_vendor
    public static String Service_Shop_Order_List = link_header + "service_shop_order_list.php";     //service_shop_search,
    public static String Service_Shop_Order_Complete = link_header + "service_shop_order_complete.php";     //service_shop_search,
}
