package cn.xxyangyoulin.library;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import cn.xxyangyoulin.library.adapter.MenuAdapter;
import cn.xxyangyoulin.library.adapter.RecyclerBaseAdapter;
import cn.xxyangyoulin.library.bean.MenuItem;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by xxyangyoulin on 2018/3/24.
 */

public class MenuWindow extends BubbleWindow {

    private ArrayList<MenuItem> mMenuItems = new ArrayList<>();
    private MenuAdapter mAdapter;

    private MenuClickListener mMenuClickListener;
    private boolean mIsBubble = true;

    public interface MenuClickListener {
        boolean onClick(MenuItem item);
    }

    public MenuWindow setMenuClickListener(MenuClickListener menuClickListener) {
        mMenuClickListener = menuClickListener;
        return this;
    }

    public static MenuWindow with(Activity context) {
        MenuWindow window = new MenuWindow();
        window.mContext = context;
        window.init();

        return window;
    }

    @Override
    public MenuWindow anim(int animStyle) {
        super.anim(animStyle);
        return this;
    }

    @Override
    public MenuWindow align(int alignMode) {
        super.align(alignMode);
        return this;
    }

    public MenuWindow isBubble(boolean isBubble) {
        this.mIsBubble = isBubble;
        return this;
    }

    @Override
    public MenuWindow show(View parent) {
        RecyclerView view = (RecyclerView) View.inflate(mContext, R.layout.popup_menu, null);

        super.contentView(view);

        initRecyclerView(view);

        super.show(parent);
        return this;
    }

    @Override
    protected View createBubble(View view) {
        if (mIsBubble) {
            return super.createBubble(view);
        } else {
            return view;
        }
    }

    @Override
    protected void initBubbleLocation() {
        if (mIsBubble) super.initBubbleLocation();
    }

    @Override
    public MenuWindow setDismissEndListener(DismissEndListener dismissEndListener) {
        super.setDismissEndListener(dismissEndListener);
        return this;
    }

    public MenuWindow addMenuItem(MenuItem item) {
        if (item != null) {
            mMenuItems.add(item);
        }
        return this;
    }

    private void initRecyclerView(RecyclerView view) {
        view.setLayoutManager(new LinearLayoutManager(mContext));


        mAdapter = new MenuAdapter(R.layout.popup_menu_item, mMenuItems);
        view.setAdapter(mAdapter);
        view.setOverScrollMode(OVER_SCROLL_NEVER);

        mAdapter.setItemClickListener(new RecyclerBaseAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerBaseAdapter.ViewHolder holder) {
                if (mMenuClickListener != null) {
                    boolean click = mMenuClickListener
                            .onClick(mAdapter.getData().get(holder.getAdapterPosition()));

                    if (click) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                MenuWindow.this.dismiss();
                            }
                        }, 100);
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, RecyclerBaseAdapter.ViewHolder holder) {

            }
        });
    }
}
