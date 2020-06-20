package com.mgg.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DrawBaseView extends View {

    private Paint paintF, paintS, paintTF, paintTS, paintTFAS;

    public DrawBaseView(Context context) {
        super(context);
        init();
    }

    public DrawBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintF = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintF.setStyle(Paint.Style.FILL);
        paintF.setStrokeWidth(3);
        paintF.setColor(Color.RED);
        paintS = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintS.setStyle(Paint.Style.STROKE);
        paintS.setStrokeWidth(3);
        paintS.setColor(Color.RED);
        paintTF = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTF.setStyle(Paint.Style.FILL);
        paintTF.setStrokeWidth(3);
        paintTF.setTextSize(32);
        paintTF.setColor(Color.RED);
        paintTS = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTS.setStyle(Paint.Style.STROKE);
        paintTS.setStrokeWidth(3);
        paintTS.setColor(Color.YELLOW);
        paintTFAS = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTFAS.setStyle(Paint.Style.FILL_AND_STROKE);
        paintTFAS.setStrokeWidth(3);
        paintTFAS.setColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画布颜色
        canvas.drawColor(Color.BLACK);
        //画点
        canvas.drawPoint(40, 40, paintF);
        //画直线
        canvas.drawLine(80, 40, 600, 40, paintF);
        //画实心矩形
        canvas.drawRect(80, 80, 300, 200, paintF);
        //画空心矩形
        canvas.drawRect(380, 80, 600, 200, paintS);
        //画实心圆
        canvas.drawCircle(190, 350, 100, paintF);
        //画空心圆
        canvas.drawCircle(490, 350, 100, paintS);
        //画实心椭圆
        canvas.drawOval(new RectF(80, 500, 300, 650), paintF);
        //画和椭圆一样参数的矩形
        canvas.drawRect(new RectF(80, 500, 300, 650), paintS);
        //画空心椭圆
        canvas.drawOval(new RectF(380, 500, 600, 650), paintS);
        //画实心圆角矩形
        canvas.drawRoundRect(new RectF(80, 700, 300, 830), 30, 30, paintF);
        //画空心圆角矩形
        canvas.drawRoundRect(new RectF(380, 700, 600, 830), 30, 30, paintS);
        //画扇形
        canvas.drawArc(new RectF(80, 850, 300, 1070), -120, 110, true, paintF);
        canvas.drawArc(new RectF(380, 850, 600, 1070), -110, 100, true, paintS);
        //画弓形
        canvas.drawArc(new RectF(80, 1000, 300, 1220), -120, 110, false, paintF);
        canvas.drawArc(new RectF(380, 1000, 600, 1220), -120, 110, false, paintS);
    }
}

