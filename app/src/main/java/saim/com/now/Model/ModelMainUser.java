package saim.com.now.Model;

/**
 * Created by NREL on 2/8/18.
 */

public class ModelMainUser {

    String user_id, user_name, user_email, user_mobile, user_pass, user_image;

    public ModelMainUser(String user_id, String user_name, String user_email, String user_mobile, String user_pass, String user_image) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_mobile = user_mobile;
        this.user_pass = user_pass;
        this.user_image = user_image;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public String getUser_image() {
        return user_image;
    }
}
