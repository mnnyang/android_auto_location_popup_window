package cn.xxyangyoulin.android_auto_location_popup_window.window;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import cn.xxyangyoulin.android_auto_location_popup_window.util.ScreenUtils;


/**
 * Created by xxyangyoulin on 2018/3/23.
 */

public class AutoLocationWindow {

    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_MIDDLE = 2;
    public static final int ALIGN_RIGHT = 3;
    public static final int ALIGN_TOP = 9;
    public static final int ALIGN_BOTTOM = 10;


    protected Context mContext;

    /**
     * popupWindow
     */
    protected PopupWindow mPopupWindow;

    /**
     * 对齐模式
     */
    protected int mAlign = ALIGN_MIDDLE;

    /**
     * 显示位置
     */
    protected int mGravity = Gravity.NO_GRAVITY;

    /**
     * 显示窗口阴影
     */
    private boolean mShowWindowShadow = true;

    /**
     * 窗口阴影值
     */
    protected float windowShadowAlpha = 0.85f;

    /**
     * 内容视图
     */
    protected View mContentView;

    /**
     * 限定窗口最大高度
     */
    protected int maxHeight;

    /**
     * y轴偏移
     */
    protected int mYOffset;

    /**
     * 显示隐藏的动画
     */
    protected int mAnimStyle;

    protected int mLocationX;
    protected int mLocationY;

    protected int[] mPopupWindowSize;
    protected int[] mParentLocation;
    protected int mParentWidth;
    protected int mParentHeight;
    protected int mScreenHeight;

    public AutoLocationWindow with(Activity context) {
        mContext = context;
        init();
        return this;
    }

    private void init() {
        maxHeight = ScreenUtils.getSHeight();
    }

    /**
     * 设置窗口布局
     */
    public AutoLocationWindow layout(int layoutId) {
        mContentView = LayoutInflater.from(mContext).inflate(layoutId, null);
        return this;
    }

    /**
     * 设置窗口视图
     */
    public AutoLocationWindow contentView(View view) {
        mContentView = view;
        return this;
    }

    /**
     * 窗口最大高度
     */
    public AutoLocationWindow maxHeight(int height) {
        this.maxHeight = height;
        return this;
    }

    /**
     * 对齐方式
     */
    public AutoLocationWindow align(int alignMode) {
        this.mAlign = alignMode;
        return this;
    }

    /**
     * 强制显示位置
     */
    public AutoLocationWindow location(int location) {
        this.mGravity = location;
        return this;
    }

    /**
     * 显示窗口阴影
     */
    public AutoLocationWindow showWindowShadow(boolean show) {
        this.mShowWindowShadow = show;
        return this;
    }

    /**
     * 距离parent View 的y轴偏移量
     */
    public AutoLocationWindow yOffset(int offset) {
        this.mYOffset = offset;
        return this;
    }

    /**
     * 设置显示和隐藏动画
     */
    public AutoLocationWindow anim(int animStyle) {
        this.mAnimStyle = animStyle;
        return this;
    }

    /**
     * 显示
     */
    public AutoLocationWindow show(View parent) {
        if (mPopupWindow == null) {
            mPopupWindow = getPopupWindow();
        }

        initLocation(parent);

        showPopupWindow(parent);

        return this;
    }


    protected void showPopupWindow(View parent) {
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(parent, Gravity.TOP | Gravity.START,
                    mLocationX, mLocationY);
            showWindowShadow();
        }
    }

    /**
     * 隐藏
     */
    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            dismissWindowShadow();
        }
    }

    /**
     * 初始化显示位置
     */
    private void initLocation(View parent) {
        mParentLocation = new int[2];
        parent.getLocationOnScreen(mParentLocation);

        mScreenHeight = ScreenUtils.getSHeight();

        mParentWidth = parent.getMeasuredWidth();
        mParentHeight = parent.getMeasuredHeight();

        int surplusHeight = mScreenHeight - mParentHeight - mParentLocation[1];

        //强制指定显示位置
        if (mGravity != Gravity.NO_GRAVITY) {
            switch (mGravity) {
                case Gravity.TOP:
                    mLocationY = mParentLocation[1] - mPopupWindowSize[1] - mYOffset;
                    break;
                case Gravity.RIGHT:
                    mLocationX = mParentLocation[0] + mParentWidth;
                    break;
                case Gravity.BOTTOM:
                    mLocationY = mParentLocation[1] + mParentHeight + mYOffset;
                    break;
                case Gravity.LEFT:
                    mLocationX = mParentLocation[0] - mPopupWindowSize[0];
                    break;

                default:
                    break;
            }

            switch (mAlign) {
                case ALIGN_MIDDLE:
                    if (mGravity == Gravity.TOP || mGravity == Gravity.BOTTOM) {
                        mLocationX = mParentLocation[0] + mParentWidth / 2 - mPopupWindowSize[0] / 2;
                    } else {
                        mLocationY = mParentLocation[1] + mParentHeight / 2 - mPopupWindowSize[1] / 2;
                    }
                    break;
                case ALIGN_LEFT:
                    if (mGravity == Gravity.TOP || mGravity == Gravity.BOTTOM) {
                        mLocationX = mParentLocation[0];
                    }
                    break;
                case ALIGN_RIGHT:
                    if (mGravity == Gravity.TOP || mGravity == Gravity.BOTTOM) {
                        mLocationX = mParentLocation[0] + mParentWidth - mPopupWindowSize[0];
                    }
                    break;
                case ALIGN_TOP:
                    if (mGravity == Gravity.LEFT || mGravity == Gravity.RIGHT) {
                        mLocationY = mParentLocation[1] + mYOffset;
                    }
                    break;
                case ALIGN_BOTTOM:
                    if (mGravity == Gravity.LEFT || mGravity == Gravity.RIGHT) {
                        mLocationY = mParentLocation[1] + mParentHeight - mPopupWindowSize[1] - mYOffset;
                    }
                    break;
                default:
                    break;

            }

        } else {
            //自动选择显示位置 todo 横屏
            if (surplusHeight > mPopupWindowSize[1]) {
                //show in bottom
                mLocationY = mParentLocation[1] + mParentHeight + mYOffset;
                mGravity = Gravity.BOTTOM;
            } else {
                mLocationY = mParentLocation[1] - mPopupWindowSize[1] - mYOffset;
                mGravity = Gravity.TOP;
            }

            switch (mAlign) {
                case ALIGN_LEFT:
                    mLocationX = mParentLocation[0];
                    break;
                case ALIGN_RIGHT:
                    mLocationX = mParentLocation[0] + mParentWidth - mPopupWindowSize[0];
                    break;
                case ALIGN_MIDDLE:
                    mLocationX = mParentLocation[0] + mParentWidth / 2 - mPopupWindowSize[0] / 2;
                    break;
            }
        }
    }

    private PopupWindow getPopupWindow() {

        mPopupWindowSize = unDisplayViewSize(mContentView);

        setEvent(mContentView);

        PopupWindow window = new PopupWindow(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (maxHeight != 0 && mPopupWindowSize[1] > maxHeight) {
            mPopupWindowSize[1] = maxHeight;
            window.setHeight(maxHeight);
        }

        window.setContentView(mContentView);
        //点击空白位置消失 必须设置setBackgroundDrawable 否则点击无效
        window.setBackgroundDrawable(new ColorDrawable());

        window.setOutsideTouchable(true);
        //动画
        window.setAnimationStyle(mAnimStyle);

        //设置可以获得焦点
        window.setFocusable(true);
        //设置弹窗内可点击
        window.setTouchable(true);

        //是否允许popup超出屏幕范围
        window.setClippingEnabled(false);
        //消失监听
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dismissWindowShadow();
            }
        });

        return window;
    }

    /**
     * 显示窗口阴影
     */
    private void showWindowShadow() {
        if (!mShowWindowShadow) {
            if (mDismissEndListener != null) {
                mDismissEndListener.onEnd();
            }
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, windowShadowAlpha);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    /**
     * 隐藏窗口阴影
     */
    private void dismissWindowShadow() {
        if (!mShowWindowShadow) {
            return;
        }

        ValueAnimator animator = ValueAnimator.ofFloat(windowShadowAlpha, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mDismissEndListener != null) {
                    mDismissEndListener.onEnd();
                }
            }
        });
        animator.start();
    }


    /**
     * 设置背景透明度
     */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }

    /**
     * 手动测量view的宽高
     */
    public int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }


    private DismissEndListener mDismissEndListener;

    public interface DismissEndListener {
        void onEnd();
    }

    public AutoLocationWindow setDismissEndListener(DismissEndListener dismissEndListener) {
        mDismissEndListener = dismissEndListener;
        return this;
    }

    public void setEvent(View popupView) {

    }
}
