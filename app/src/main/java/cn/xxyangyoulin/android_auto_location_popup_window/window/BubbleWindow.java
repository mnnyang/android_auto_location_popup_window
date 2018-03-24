package cn.xxyangyoulin.android_auto_location_popup_window.window;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by xxyangyoulin on 2018/3/24.
 */

public class BubbleWindow extends AutoLocationWindow {

    private BubbleLayout mBubbleLayout;

    @Override
    public AutoLocationWindow layout(int layoutId) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        contentView(view);
        return this;
    }

    @Override
    public AutoLocationWindow contentView(View view) {
        mBubbleLayout = new BubbleLayout(mContext);
        mBubbleLayout.addView(view, 0);
        return super.contentView(mBubbleLayout);
    }

    @Override
    protected void showPopupWindow(View parent) {
        int bubbleGravity;
        int offset = 0;
        switch (mGravity) {
            case Gravity.TOP:
                bubbleGravity = Gravity.BOTTOM;
                offset = mParentLocation[0] + mParentWidth / 2 - mLocationX;

                break;
            case Gravity.BOTTOM:
                bubbleGravity = Gravity.TOP;
                offset = mParentLocation[0] + mParentWidth / 2 - mLocationX;
                break;
            case Gravity.RIGHT:
                bubbleGravity = Gravity.LEFT;
                offset = mParentLocation[1] + mParentHeight / 2 - mLocationY;
                break;
            case Gravity.LEFT:
                offset = mParentLocation[1] + mParentHeight / 2 - mLocationY;
                bubbleGravity = Gravity.RIGHT;
                break;
            default:
                throw new IllegalArgumentException("bubble gravity");
        }

        mBubbleLayout.setBubble(bubbleGravity, offset);
        super.showPopupWindow(parent);
    }
}
