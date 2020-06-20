package com.mgg.customview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.mgg.customview.R


class CirclePercentView @JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mCirclePaint =
        Paint(Paint.ANTI_ALIAS_FLAG)
    private var mArcPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPercentTextPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)
    private var mCircleColor = 0
    private var mArcColor = 0
    private var mArcWidth = 0
    private var mPercentTextColor = 0
    private var mPercentTextSize = 0
    private var mRadius = 0
    private var mCurPercent = 0.0f

    private var mTextBound: Rect? = null
    private var mArcRectF: RectF? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            measureDimension(widthMeasureSpec),
            measureDimension(heightMeasureSpec)
        )
    }

    private var mOnClickListener: OnClickListener? = null
    fun setOnCircleClickListener(onClickListener: OnClickListener?) {
        mOnClickListener = onClickListener
    }

    init {
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.CirclePercentView, defStyleAttr, 0)
        mCircleColor = ta.getColor(R.styleable.CirclePercentView_circleBg, -0x71d606)
        mArcColor = ta.getColor(R.styleable.CirclePercentView_arcColor, -0x1200)
        mArcWidth = ta.getDimensionPixelSize(
            R.styleable.CirclePercentView_arcWidth,
            dp2px(context, 16.0f)
        )
        mPercentTextColor = ta.getColor(R.styleable.CirclePercentView_arcColor, -0x1200)
        mPercentTextSize = ta.getDimensionPixelSize(
            R.styleable.CirclePercentView_percentTextSize,
            sp2px(context, 16.0f)
        )
        mRadius = ta.getDimensionPixelSize(
            R.styleable.CirclePercentView_radius,
            dp2px(context, 100.0f)
        )
        ta.recycle()

        mCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mCirclePaint.style = Paint.Style.FILL
        mCirclePaint.color = mCircleColor

        mArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mArcPaint.style = Paint.Style.STROKE
        mArcPaint.strokeWidth = mArcWidth.toFloat()
        mArcPaint.color = mArcColor
        mArcPaint.strokeCap = Paint.Cap.ROUND //使圆弧两头圆滑


        mPercentTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPercentTextPaint.style = Paint.Style.STROKE
        mPercentTextPaint.color = mPercentTextColor
        mPercentTextPaint.textSize = mPercentTextSize.toFloat()

        mArcRectF = RectF() //圆弧的外接矩形


        mTextBound = Rect() //文本的范围矩形


        setOnClickListener {
            if (mOnClickListener != null) {
                mOnClickListener?.onClick(this)
            }
        }
    }

    private fun measureDimension(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) { //精确地，代表宽高为定值或者match_parent时
            result = specSize
        } else {
            result = 2 * mRadius
            if (specMode == MeasureSpec.AT_MOST) { //最大地，代表宽高为wrap_content时
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画圆
        //画圆
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            mRadius.toFloat(), mCirclePaint
        )

        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            mRadius.toFloat(), mCirclePaint
        )

        mArcRectF!![width / 2 - mRadius + mArcWidth / 2.toFloat(), height / 2 - mRadius + mArcWidth / 2
            .toFloat(), width / 2 + mRadius - mArcWidth / 2.toFloat()] =
            height / 2 + mRadius - mArcWidth / 2.toFloat()
        val temp = Paint(Paint.ANTI_ALIAS_FLAG)
        temp.style = Paint.Style.STROKE
        temp.strokeWidth = 3.0f
        temp.color = mArcColor
        temp.strokeCap = Paint.Cap.ROUND //使圆弧两头圆滑
        canvas.drawRect(mArcRectF!!,temp)
        canvas.drawArc(mArcRectF!!, 270F, 360 * mCurPercent / 100, false, mArcPaint)
        val text = "$mCurPercent%"
        mPercentTextPaint.getTextBounds(text, 0, text.length, mTextBound)
        canvas.drawText(
            text, (width / 2 - mTextBound!!.width() / 2).toFloat()
            , (height / 2 + mTextBound!!.height() / 2).toFloat(), mPercentTextPaint
        )
    }

    fun setCurPercent(curPercent: Float) {
        val anim = ValueAnimator.ofFloat(mCurPercent, curPercent)
        anim.duration = (Math.abs(mCurPercent - curPercent) * 20).toLong()
        anim.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            mCurPercent =
                Math.round(value * 10).toFloat() / 10 //四舍五入保留到小数点后两位
            invalidate()
        }
        anim.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal, context.resources.displayMetrics
        ).toInt()
    }

    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal, context.resources.displayMetrics
        ).toInt()
    }

    fun px2dp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale
    }

    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }
}