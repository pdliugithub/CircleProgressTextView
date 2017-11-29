package com.rossia.life.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * @author pd_liu on 2017/11/8.
 *         <p>
 *         圆形进度控件.
 *         设置进度{@link #setProgress(float)}.
 *         后续更新更多功能
 */

public class CircleProgressTextView extends AppCompatTextView {

    private static final String TAG = "CircleProgressTextView";
    /**
     * 默认的宽、高.{@link CircleProgressTextView}
     */
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;

    /**
     * Paints.
     */
    private Paint mProgressPaint;
    private Paint mCirclePaint;

    /**
     * 默认画笔的颜色.
     */
    @ColorInt
    private int mCircleDefaultPaintColor = Color.GRAY;
    private int mProgressDefaultPaintColor = Color.RED;
    /**
     * 设置画笔默认的宽度
     *
     * @see #mProgressPaint .
     * @see #mCirclePaint .
     */
    private int mCirclePaintDefaultWidth = 10;
    private int mProgressPaintDefaultWidth = 10;

    /**
     * Padding.
     */
    private int mLeftPadding;
    private int mTopPadding;
    private int mRightPadding;
    private int mBottomPadding;
    private int mCircleWidth;

    /**
     * 进度的角度.
     *
     * @see #mProgressPaint .
     * @see #mProgressDefaultPaintColor .
     * @see #mProgressPaintDefaultWidth .
     */
    private float mStartAngle = 0F;
    private float mSweepAngle = 0F;

    public CircleProgressTextView(Context context) {
        this(context, null);
    }

    public CircleProgressTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取style attributes.
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressTextView);
        mSweepAngle = typedArray.getFloat(R.styleable.CircleProgressTextView_progress, 0f);
        //recycle resource.
        typedArray.recycle();

        //initialization.
        initialization();
    }

    /**
     * 初始化信息
     */
    private void initialization() {
        //new
        mProgressPaint = new Paint();
        mCirclePaint = new Paint();
        //去锯齿
        mProgressPaint.setAntiAlias(true);
        mCirclePaint.setAntiAlias(true);
        //画笔颜色
        mProgressPaint.setColor(mProgressDefaultPaintColor);
        mCirclePaint.setColor(mCircleDefaultPaintColor);
        //设置画笔样式为Stroke.
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        //设置画笔的宽度
        mProgressPaint.setStrokeWidth(mProgressPaintDefaultWidth);
        mCirclePaint.setStrokeWidth(mCirclePaintDefaultWidth);
        //设置View内容居中
        setGravity(Gravity.CENTER);
    }

    /**
     * 设置当前进度条的进度角度.
     *
     * @param startAngle 开始角度
     * @param sweepAngle 扫描角度
     */
    private void setProgressAngle(float startAngle, float sweepAngle) {
        mStartAngle = startAngle;
        mSweepAngle = sweepAngle;
        postInvalidate();
    }

    /**
     * 设置进度条
     *
     * @param progress 进度
     */
    public void setProgress(@FloatRange(from = 0, to = 360) float progress) {
        mSweepAngle = progress;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取当前view的Padding、width、height
        int width = getWidth();
        int height = getHeight();
        mLeftPadding = getPaddingLeft();
        mTopPadding = getPaddingTop();
        mRightPadding = getPaddingRight();
        mBottomPadding = getPaddingBottom();

        width = width - mLeftPadding - mRightPadding - (mCirclePaintDefaultWidth * 2);
        height = height - mTopPadding - mBottomPadding - (mCirclePaintDefaultWidth * 2);

        //圆形进度的宽度(取最大值)
        mCircleWidth = width >= height ? width : height;
        int radius = mCircleWidth / 2;

        canvas.drawCircle(mLeftPadding + radius + mCirclePaintDefaultWidth, mTopPadding + radius + mCirclePaintDefaultWidth, radius, mCirclePaint);

        //兼容版本.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //>= LOLLIPOP
            canvas.drawArc(mLeftPadding + mCirclePaintDefaultWidth, mTopPadding + mCirclePaintDefaultWidth, mLeftPadding + mCircleWidth + mCirclePaintDefaultWidth, mTopPadding + mCircleWidth + mCirclePaintDefaultWidth, mStartAngle, mSweepAngle, false
                    , mProgressPaint);
        } else {

            //< LOLLIPOP
            RectF rectF = new RectF(mLeftPadding + mCirclePaintDefaultWidth, mTopPadding + mCirclePaintDefaultWidth, mLeftPadding + mCircleWidth + mCirclePaintDefaultWidth, mTopPadding + mCircleWidth + mCirclePaintDefaultWidth);
            canvas.drawArc(rectF, mStartAngle, mSweepAngle, false
                    , mProgressPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取Size\Mode
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeWidth == MeasureSpec.AT_MOST || modeHeight == MeasureSpec.AT_MOST) {
            //设置默认的View宽和高
            setMeasuredDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            return;
        }
        if (modeWidth != MeasureSpec.AT_MOST || modeHeight != MeasureSpec.AT_MOST){
            //取最大值作为View的宽和高
            int size = sizeWidth >= sizeHeight ? sizeWidth : sizeHeight;
            setMeasuredDimension(size, size);
        }

    }

}
