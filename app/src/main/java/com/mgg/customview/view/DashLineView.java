package com.mgg.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mgg.customview.R;

/**
 * https://www.jianshu.com/p/f365e259eefa?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 */
public class DashLineView extends View {

    private Paint mPaint;
    private Path mPath;
    //虚线颜色
    private int backgroundColor;
    //虚线粗细
    private int strokeWidth;
    //虚线宽度
    private int dashWidth;
    //虚线隔断宽度
    private int dashSpaceWidth;

    public DashLineView(Context context) {
        super(context);
        initView();
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        obtainAttributes(context, attrs);
        initView();
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs);
        initView();
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DashLineView);
        backgroundColor = ta.getColor(R.styleable.DashLineView_pt_backgroundColor, getResources().getColor(R.color.line));
        strokeWidth = ta.getInt(R.styleable.DashLineView_pt_strokeWidth, dip2px(context, 1));
        dashWidth = ta.getInt(R.styleable.DashLineView_pt_dashWidth, dip2px(context, 4));
        dashSpaceWidth = ta.getInt(R.styleable.DashLineView_pt_dashSpaceWidth, dip2px(context, 3));
        ta.recycle();
    }

    private void initView(){
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) {
            return;
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //自定义的View能够使用wrap_content或者是match_parent的属性
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(backgroundColor);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setPathEffect(new DashPathEffect(new float[] { dashWidth, dashSpaceWidth }, 0));

        int centerY = getHeight() / 2;
        mPath.reset();
        mPath.moveTo(0, centerY);
        mPath.lineTo(getWidth(), centerY);
        canvas.drawPath(mPath, mPaint);
    }

    public void setDashLineColor(int bgColor) {
        this.backgroundColor = getResources().getColor(bgColor);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}