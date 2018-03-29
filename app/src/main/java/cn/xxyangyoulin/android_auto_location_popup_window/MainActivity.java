package cn.xxyangyoulin.android_auto_location_popup_window;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import cn.xxyangyoulin.android_auto_location_popup_window.adapter.ContentAdapter;
import cn.xxyangyoulin.android_auto_location_popup_window.util.BitmapUtils;
import cn.xxyangyoulin.android_auto_location_popup_window.util.ScreenUtils;
import cn.xxyangyoulin.library.AutoLocationWindow;
import cn.xxyangyoulin.library.MenuWindow;
import cn.xxyangyoulin.library.bean.MenuItem;

import static android.view.View.OVER_SCROLL_NEVER;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvAvatar;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private ImageView mIvMenu;
    private MenuWindow mMenuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.setSystemBarTransparent(this);
        setContentView(R.layout.activity_main);

        ScreenUtils.init(this);
        initView();
    }

    private void initView() {
        mIvAvatar = findViewById(R.id.iv_avatar);
        mIvMenu = findViewById(R.id.iv_menu);
        mRecyclerView = findViewById(R.id.recycler_view);
        mToolbar = findViewById(R.id.toolbar);

        repairTransparentBug();

        setAvatar();
        setRecyclerView();
        setMenuEvent();
    }

    private void showMenu() {
        if (mMenuWindow!=null){
            mMenuWindow.show(mIvMenu);
            return;
        }
        mMenuWindow = MenuWindow.with(this)
                .isBubble(true)
                .anim(R.style.window_anim)
                .align(AutoLocationWindow.ALIGN_RIGHT)
                .addMenuItem(new MenuItem()
                        .setTitle("创建群聊").setIconRes(R.drawable.conversation_options_multichat))
                .addMenuItem(new MenuItem()
                        .setTitle("加好友/群").setIconRes(R.drawable.conversation_options_addmember))
                .addMenuItem(new MenuItem()
                        .setTitle("扫一扫").setIconRes(R.drawable.conversation_options_qr))
                .addMenuItem(new MenuItem()
                        .setTitle("面对面快传").setIconRes(R.drawable.conversation_facetoface_qr))
                .addMenuItem(new MenuItem()
                        .setTitle("付款").setIconRes(R.drawable.conversation_options_charge_icon))
                .addMenuItem(new MenuItem()
                        .setTitle("拍摄").setIconRes(R.drawable.conversation_options_camera))
                .addMenuItem(new MenuItem()
                        .setTitle("高能舞室").setIconRes(R.drawable.conversation_options_multichat))
                .setMenuClickListener(new MenuWindow.MenuClickListener() {
                    @Override
                    public boolean onClick(MenuItem item) {
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    }
                }).show(mIvMenu);
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
        mIvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }


    private void setAvatar() {
        Bitmap circleImage = BitmapUtils.createCircleImage(
                BitmapFactory.decodeResource(getResources(), R.drawable.head_img));
        mIvAvatar.setImageBitmap(circleImage);
    }


    private void repairTransparentBug() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RelativeLayout layout = findViewById(R.id.layout_toolbar);

            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
            int statusBarHeight = ScreenUtils.getStatusBarHeight(this);
            layoutParams.height += statusBarHeight;
            layout.setLayoutParams(layoutParams);
        }
    }
}
