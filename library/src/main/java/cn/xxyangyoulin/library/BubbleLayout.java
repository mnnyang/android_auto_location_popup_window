package cn.xxyangyoulin.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

/**
 * Created by xxyangyoulin on 2018/3/24.
 */

public class BubbleLayout extends FrameLayout {

    private Paint mPaint;
    private Path mPath;
    private Path mBubblePath;

    private int bubbleHeight = 30;
    private int bubbleWidth = 58;

    private int gravity = Gravity.TOP;
    private int bubbleOffset;

    private int radius = 15;
    private int mOffset = Integer.MAX_VALUE;

    public BubbleLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public BubbleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BubbleLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        setWillNotDraw(false);
    }


    public void setBubble(int gravity, int offset) {
        this.gravity = gravity;
        this.mOffset = offset;

        setPadding(gravity == Gravity.LEFT ? bubbleHeight : getPaddingLeft(),
                gravity == Gravity.TOP ? bubbleHeight : getPaddingTop(),
                gravity == Gravity.RIGHT ? bubbleHeight : getPaddingRight(),
                gravity == Gravity.BOTTOM ? bubbleHeight : getPaddingBottom()
        );
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPath == null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            initBubbleOffset(width);
            initPath(width, height);
        }

        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(mBubblePath, mPaint);
    }

    private void initBubbleOffset(int width) {
        if (mOffset == Integer.MAX_VALUE) {
            //默认为中间
            this.bubbleOffset = (width - bubbleWidth - radius * 2) / 2;
        } else {
            this.bubbleOffset = mOffset - radius - bubbleWidth / 2;
        }
    }

    private void initPath(int width, int height) {
        mPath = new Path();
        int left = gravity == Gravity.LEFT ? bubbleHeight : 0;
        int top = gravity == Gravity.TOP ? bubbleHeight : 0;
        int right = gravity == Gravity.RIGHT ? width - bubbleHeight : width;
        int bottom = gravity == Gravity.BOTTOM ? height - bubbleHeight : height;

        mPath.addRoundRect(new RectF(left, top, right, bottom), radius, radius, Path.Direction.CW);

        mPath.close();

        mBubblePath = new Path();
        switch (gravity) {
            case Gravity.TOP:
                mBubblePath.moveTo(bubbleOffset + radius, bubbleHeight);
                mBubblePath.lineTo(bubbleOffset + radius + bubbleWidth / 2, 0);
                mBubblePath.lineTo(bubbleOffset + radius + bubbleWidth, bubbleHeight);
                mBubblePath.close();
                break;
            case Gravity.BOTTOM:
                mBubblePath.moveTo(bubbleOffset + radius, height - bubbleHeight);
                mBubblePath.lineTo(bubbleOffset + radius + bubbleWidth / 2, height);
                mBubblePath.lineTo(bubbleOffset + radius + bubbleWidth, height - bubbleHeight);
                mBubblePath.close();
                break;
            case Gravity.LEFT:
                mBubblePath.moveTo(bubbleHeight, radius + bubbleOffset);
                mBubblePath.lineTo(0, bubbleWidth / 2 + radius + bubbleOffset);
                mBubblePath.lineTo(bubbleHeight, bubbleWidth + radius + bubbleOffset);
                mBubblePath.close();

                break;
            case Gravity.RIGHT:
                mBubblePath.moveTo(width - bubbleHeight, radius + bubbleOffset);
                mBubblePath.lineTo(width, bubbleWidth / 2 + radius + bubbleOffset);
                mBubblePath.lineTo(width - bubbleHeight, bubbleWidth + radius + bubbleOffset);
                mBubblePath.close();
                break;
            default:
                break;
        }

    }

    public int getBubbleHeight() {
        return bubbleHeight;
    }

    public BubbleLayout setBubbleHeight(int bubbleHeight) {
        this.bubbleHeight = bubbleHeight;
        return this;
    }
}
