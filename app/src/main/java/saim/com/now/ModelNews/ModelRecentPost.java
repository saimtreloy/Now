package saim.com.now.ModelNews;

/**
 * Created by NREL on 4/4/18.
 */

public class ModelRecentPost {
    String category, category_link, image, date, title, title_link, detail;

    public ModelRecentPost(String category, String category_link, String image, String date, String title, String title_link, String detail) {
        this.category = category;
        this.category_link = category_link;
        this.image = image;
        this.date = date;
        this.title = title;
        this.title_link = title_link;
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public String getCategory_link() {
        return category_link;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle_link() {
        return title_link;
    }

    public String getDetail() {
        return detail;
    }
}
