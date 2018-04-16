package saim.com.now.ModelNews;

/**
 * Created by NREL on 4/16/18.
 */

public class ModelNewsComment {
    String user_name, user_image, comment, date;

    public ModelNewsComment(String user_name, String user_image, String comment, String date) {
        this.user_name = user_name;
        this.user_image = user_image;
        this.comment = comment;
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }
}
