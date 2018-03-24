package cn.xxyangyoulin.android_auto_location_popup_window.window;

/**
 * Created by xxyangyoulin on 2018/3/25.
 */

public class MenuItem {
    private String title;
    private int iconRes;

    public String getTitle() {
        return title;
    }

    public MenuItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getIconId() {
        return iconRes;
    }

    public MenuItem setIconRes(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }
}
