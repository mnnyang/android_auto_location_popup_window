package cn.xxyangyoulin.android_auto_location_popup_window.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;

import java.util.List;

import cn.xxyangyoulin.android_auto_location_popup_window.R;
import cn.xxyangyoulin.android_auto_location_popup_window.window.AutoLocationWindow;
import cn.xxyangyoulin.android_auto_location_popup_window.window.BubbleWindow;

/**
 * Created by xxyangyoulin on 2018/3/24.
 */

public class ContentAdapter extends RecyclerBaseAdapter<String> {

    Activity mActivity;
    public ContentAdapter(int itemLayoutId, @NonNull List<String> data, Activity activity) {
        super(itemLayoutId, data);
        mActivity = activity;
    }

    @Override
    protected void convert(ViewHolder holder, int position) {

        holder.getView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLocationWindow autoLocationWindow = new BubbleWindow()
                        .with(mActivity)
                        .layout(R.layout.popup)
                        .align(AutoLocationWindow.ALIGN_MIDDLE)
                        .location(Gravity.RIGHT)
                        .show(view);
            }
        });

        holder.getView(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLocationWindow autoLocationWindow = new BubbleWindow()
                        .with(mActivity)
                        .layout(R.layout.popup)
                        .align(AutoLocationWindow.ALIGN_MIDDLE)
                        .location(Gravity.LEFT)
                        .show(view);
            }
        });

        holder.getView(R.id.tv_middle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLocationWindow autoLocationWindow = new BubbleWindow()
                        .with(mActivity)
                        .layout(R.layout.popup)
//                        .align(AutoLocationWindow.ALIGN_MIDDLE)
//                        .enforceLocation(Gravity.BOTTOM)
                        .show(view);
            }
        });
    }
}
