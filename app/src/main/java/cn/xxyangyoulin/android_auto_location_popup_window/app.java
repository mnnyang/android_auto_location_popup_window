package cn.xxyangyoulin.android_auto_location_popup_window;

import android.app.Application;
import android.content.Context;

/**
 * Created by xxyangyoulin on 2018/3/24.
 */

public class app extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
