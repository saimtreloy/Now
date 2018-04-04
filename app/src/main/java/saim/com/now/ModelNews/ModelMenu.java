package saim.com.now.ModelNews;

/**
 * Created by NREL on 4/4/18.
 */

public class ModelMenu {
    String menu, menu_link;

    public ModelMenu(String menu, String menu_link) {
        this.menu = menu;
        this.menu_link = menu_link;
    }

    public String getMenu() {
        return menu;
    }

    public String getMenu_link() {
        return menu_link;
    }
}
