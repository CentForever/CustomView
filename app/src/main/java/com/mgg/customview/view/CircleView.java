package com.mgg.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.mgg.customview.R;

/**
 * https://www.jianshu.com/p/b75b15777458?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 */
public class CircleView extends View {

    private static final int DEFAULT_SIZE = 200;

    private static final int DEFAULT_RADIUS = 20;

    private static final int TYPE_ROUND = 1;

    private static final int TYPE_RECT = 2;

    private int mSize;

    private int mResourceId;

    private int mType;

    private Paint mPaint;

    private Bitmap mSrcBitmap;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mResourceId = ta.getResourceId(R.styleable.CircleView_src, R.mipmap.ic_launcher);
        mType = ta.getInt(R.styleable.CircleView_type, TYPE_ROUND);
        ta.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMeasureSize(widthMeasureSpec);
        int height = getMeasureSize(heightMeasureSpec);
        mSize = Math.min(width, height);
        setMeasuredDimension(mSize, mSize);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        if (mSrcBitmap == null) {
            mSrcBitmap = getScaleBitmap();
        }
        if (mType == TYPE_ROUND) {
            canvas.drawCircle(mSize / 2, mSize / 2, mSize / 2, mPaint);
        } else if (mType == TYPE_RECT) {
            canvas.drawRoundRect(0, 0, mSize, mSize, DEFAULT_RADIUS, DEFAULT_RADIUS, mPaint);
        }
        //设置模式为:显示背景层和上层的交集，且显示上层图像
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制要显示的图像
        canvas.drawBitmap(mSrcBitmap, 0, 0, mPaint);
        //重置Xfermode
        mPaint.setXfermode(null);
    }

    private void init() {
        //禁用硬件加速,否则可能无法绘制圆形
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private int getMeasureSize(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        return mode == MeasureSpec.EXACTLY ? size : DEFAULT_SIZE;
    }

    /**
     * 获取缩放后的Bitmap
     *
     * @return
     */
    private Bitmap getScaleBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), mResourceId, options);
        options.inSampleSize = calcSampleSize(options, mSize, mSize);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(getResources(), mResourceId, options);
    }

    /**
     * 计算缩放比例
     *
     * @param option
     * @param width
     * @param height
     * @return
     */
    private int calcSampleSize(BitmapFactory.Options option, int width, int height) {
        int originWidth = option.outWidth;
        int originHeight = option.outHeight;
        int sampleSize = 1;
        while ((originWidth = originWidth >> 1) > width && (originHeight = originHeight >> 1) > height) {
            sampleSize = sampleSize << 1;
        }
        return sampleSize;
    }
}