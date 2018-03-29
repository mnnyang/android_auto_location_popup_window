package cn.xxyangyoulin.library.adapter;

import android.support.annotation.NonNull;

import java.util.List;

import cn.xxyangyoulin.library.R;
import cn.xxyangyoulin.library.bean.MenuItem;

/**
 * Created by xxyangyoulin on 2018/3/25.
 */

public class MenuAdapter extends RecyclerBaseAdapter<MenuItem> {

    public MenuAdapter(int itemLayoutId, @NonNull List<MenuItem> data) {
        super(itemLayoutId, data);
    }

    @Override
    protected void convert(ViewHolder holder, int position) {
        holder.setText(R.id.tv_title,getData().get(position).getTitle());
        holder.setImageResource(R.id.iv_icon,getData().get(position).getIconId());
    }
}
