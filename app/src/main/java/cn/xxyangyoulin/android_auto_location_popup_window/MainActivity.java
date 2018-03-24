package cn.xxyangyoulin.android_auto_location_popup_window;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


import cn.xxyangyoulin.android_auto_location_popup_window.util.BitmapUtils;
import cn.xxyangyoulin.android_auto_location_popup_window.util.ScreenUtils;

import static android.view.View.OVER_SCROLL_NEVER;

public class MainActivity extends AppCompatActivity {


    private ImageView mIvAvatar;
    private ImageView mIvAdd;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        transparentStatusBar();
        ScreenUtils.setSystemBarTransparent(this);
        setContentView(R.layout.activity_main);

        ScreenUtils.init(this);
        initView();
    }

    private void initView() {
        mIvAvatar = findViewById(R.id.iv_avatar);
        mIvAdd = findViewById(R.id.iv_add);
        mRecyclerView = findViewById(R.id.recycler_view);

        repairTransparentBug();
        setAvatar();
        setRecyclerView();
        setMenuEvent();
    }

    private void setRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add("content" + i);
        }

        ContentAdapter adapter = new ContentAdapter(R.layout.item, strings, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private void setMenuEvent() {
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }

    private void showMenu() {
        AutoLocationWindow autoLocationWindow = new AutoLocationWindow().with(this)
                .maxHeight(200)
                .anim(R.style.window_anim)
                .yOffset(40)
                .align(AutoLocationWindow.ALIGN_RIGHT)
                .layout(R.layout.popup).show(mIvAdd);
    }

    private void setAvatar() {
        Bitmap circleImage = BitmapUtils.createCircleImage(
                BitmapFactory.decodeResource(getResources(), R.drawable.head_img));
        mIvAvatar.setImageBitmap(circleImage);
    }

    private void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void repairTransparentBug() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout layout = findViewById(R.id.layout_toolbar);

            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
            int statusBarHeight = getStatusBarHeight();
            layoutParams.height += statusBarHeight;
            layout.setLayoutParams(layoutParams);
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

}
