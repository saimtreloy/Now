package saim.com.now.Utilities;

/**
 * Created by NREL on 2/5/18.
 */

public class ApiURL {

    public static String link_header = "http://www.globalearnmoney.com/now_api/";

    public static String Login = link_header + "login.php";                 //user_email, user_pass
    public static String Register = link_header + "register.php";           //user_name, user_email, user_mobile, user_pass
    public static String Service_List = link_header + "service_list.php";
    public static String Service_Shop_List = link_header + "service_shop_list.php";
    public static String Service_Shop_Cat_List = link_header + "service_shop_cat_list.php";     //service_shop_id

}
