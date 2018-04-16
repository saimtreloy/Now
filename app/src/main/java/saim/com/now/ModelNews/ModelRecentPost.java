package saim.com.now.ModelNews;

/**
 * Created by NREL on 4/4/18.
 */

public class ModelRecentPost {
    String id, title, detail, image, title_link, date, menu, menu_link, source, like, comments, user_id;

    public ModelRecentPost(String id, String title, String detail, String image, String title_link, String date, String menu, String menu_link, String source, String like, String comments, String user_id) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.image = image;
        this.title_link = title_link;
        this.date = date;
        this.menu = menu;
        this.menu_link = menu_link;
        this.source = source;
        this.like = like;
        this.comments = comments;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getImage() {
        return image;
    }

    public String getTitle_link() {
        return title_link;
    }

    public String getDate() {
        return date;
    }

    public String getMenu() {
        return menu;
    }

    public String getMenu_link() {
        return menu_link;
    }

    public String getSource() {
        return source;
    }

    public String getLike() {
        return like;
    }

    public String getComments() {
        return comments;
    }

    public String getUser_id() {
        return user_id;
    }
}
