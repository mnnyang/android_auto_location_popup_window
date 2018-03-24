package cn.xxyangyoulin.android_auto_location_popup_window;

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

    public static final int LOCATION_TOP = 4;
    public static final int LOCATION_BOTTOM = 5;
    public static final int LOCATION_LEFT = 6;
    public static final int LOCATION_RIGHT = 7;
    public static final int LOCATION_NONE = 8;

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
     * 强制显示位置
     */
    protected int mLocation = LOCATION_NONE;

    /**
     * 显示窗口阴影
     */
    private boolean mShowWindowShadow = true;

    /**
     * 窗口阴影值
     */
    private float windowShadowAlpha = 0.85f;

    /**
     * 内容视图
     */
    private View mContentView;

    /**
     * 限定窗口最大高度
     */
    private int maxHeight;

    /**
     * y轴偏移
     */
    private int mYOffset;

    /**
     * 显示隐藏的动画
     */
    private int mAnimStyle;

    private int locationX;
    private int locationY;

    private int[] mPopupWindowSize;

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
        this.mLocation = location;
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

        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(parent, Gravity.TOP | Gravity.START,
                    locationX, locationY);
            showWindowShadow();
        }

        return this;
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
        int parentLocation[] = new int[2];
        parent.getLocationOnScreen(parentLocation);

        int screenHeight = ScreenUtils.getSHeight();

        int parentWidth = parent.getMeasuredWidth();
        int parentHeight = parent.getMeasuredHeight();

        int surplusHeight = screenHeight - parentHeight - parentLocation[1];

        //强制指定显示位置
        if (mLocation != LOCATION_NONE) {
            switch (mLocation) {
                case LOCATION_TOP:
                    locationY = parentLocation[1] - mPopupWindowSize[1] - mYOffset;
                    break;
                case LOCATION_RIGHT:
                    locationX = parentLocation[0] + parentWidth;
                    break;
                case LOCATION_BOTTOM:
                    locationY = parentLocation[1] + parentHeight + mYOffset;
                    break;
                case LOCATION_LEFT:
                    locationX = parentLocation[0] - mPopupWindowSize[0];
                    break;

                default:
                    break;
            }

            switch (mAlign) {
                case ALIGN_MIDDLE:
                    if (mLocation == LOCATION_TOP || mLocation == LOCATION_BOTTOM) {
                        locationX = parentLocation[0] + parentWidth / 2 - mPopupWindowSize[0] / 2;
                    } else {
                        locationY = parentLocation[1] + parentHeight / 2 - mPopupWindowSize[1] / 2;
                    }
                    break;
                case ALIGN_LEFT:
                    if (mLocation == LOCATION_TOP || mLocation == LOCATION_BOTTOM) {
                        locationX = parentLocation[0];
                    }
                    break;
                case ALIGN_RIGHT:
                    if (mLocation == LOCATION_TOP || mLocation == LOCATION_BOTTOM) {
                        locationX = parentLocation[0] + parentWidth - mPopupWindowSize[0];
                    }
                    break;
                case ALIGN_TOP:
                    if (mLocation == LOCATION_LEFT || mLocation == LOCATION_RIGHT) {
                        locationY = parentLocation[1]+mYOffset;
                    }
                    break;
                case ALIGN_BOTTOM:
                    if (mLocation == LOCATION_LEFT || mLocation == LOCATION_RIGHT) {
                        locationY = parentLocation[1]-mPopupWindowSize[1]-mYOffset;
                    }
                    break;
                default:
                    break;

            }

        } else {
            //自动选择显示位置 todo 横屏
            if (surplusHeight > mPopupWindowSize[1]) {
                //show in bottom
                locationY = parentLocation[1] + parentHeight + mYOffset;
            } else {
                locationY = parentLocation[1] - mPopupWindowSize[1] - mYOffset;
            }

            switch (mAlign) {
                case ALIGN_LEFT:
                    locationX = parentLocation[0];
                    break;
                case ALIGN_RIGHT:
                    locationX = parentLocation[0] + parentWidth - mPopupWindowSize[0];
                    break;
                case ALIGN_MIDDLE:
                    locationX = parentLocation[0] + parentWidth / 2 - mPopupWindowSize[0] / 2;
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

    public void setEvent(View popupView) {

    }
}
