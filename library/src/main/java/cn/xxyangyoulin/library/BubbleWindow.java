package cn.xxyangyoulin.library;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by xxyangyoulin on 2018/3/24.
 */

public class BubbleWindow extends AutoLocationWindow {

    private BubbleLayout mBubbleLayout;


    public static BubbleWindow with(Activity context) {
        BubbleWindow window = new BubbleWindow();
        window.mContext = context;
        window.init();

        return window;
    }

    @Override
    public BubbleWindow layout(int layoutId) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        contentView(view);
        return this;
    }

    @Override
    public BubbleWindow align(int alignMode) {
        super.align(alignMode);
        return this;
    }

    @Override
    public AutoLocationWindow contentView(View view) {
        view = createBubble(view);
        return super.contentView(view);
    }

    protected View createBubble(View view) {
        mBubbleLayout = new BubbleLayout(mContext);
        mBubbleLayout.addView(view, 0);
        return mBubbleLayout;
    }

    @Override
    protected void showPopupWindow(View parent) {
        initBubbleLocation();
        super.showPopupWindow(parent);
    }

    protected void initBubbleLocation() {
        int bubbleGravity;
        int offset = 0;
        switch (mGravity) {
            case Gravity.TOP:
                bubbleGravity = Gravity.BOTTOM;
                offset = mParentLocation[0] + mParentWidth / 2 - mLocationX;
                mLocationY-=mBubbleLayout.getBubbleHeight();
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
                mLocationX-=mBubbleLayout.getBubbleHeight();
                break;
            default:
                throw new IllegalArgumentException("bubble gravity");
        }

        mBubbleLayout.setBubble(bubbleGravity, offset);
    }
}
