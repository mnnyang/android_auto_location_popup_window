package cn.xxyangyoulin.android_auto_location_popup_window;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

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
        holder.setText(R.id.tv_left, getData().get(position));


        holder.getView(R.id.tv_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLocationWindow autoLocationWindow = new AutoLocationWindow()
                        .with(mActivity)
                        .layout(R.layout.popup)
                        .align(AutoLocationWindow.ALIGN_BOTTOM)
                        .location(AutoLocationWindow.LOCATION_RIGHT)
                        .yOffset(50)
                        .show(view);
            }
        });

        holder.getView(R.id.tv_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLocationWindow autoLocationWindow = new AutoLocationWindow()
                        .with(mActivity)
                        .layout(R.layout.popup)
                        .align(AutoLocationWindow.ALIGN_BOTTOM)
                        .location(AutoLocationWindow.LOCATION_LEFT)
                        .show(view);
            }
        });

        holder.getView(R.id.tv_middle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLocationWindow autoLocationWindow = new AutoLocationWindow()
                        .with(mActivity)
                        .layout(R.layout.popup)
                        .align(AutoLocationWindow.ALIGN_LEFT)
                        .location(AutoLocationWindow.LOCATION_TOP)
                        .showWindowShadow(false)
                        .show(view);
            }
        });
    }
}
