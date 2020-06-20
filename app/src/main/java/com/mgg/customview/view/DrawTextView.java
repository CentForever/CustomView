package com.mgg.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class DrawTextView extends View {
    private Paint paintTS, paintTFAS, paintTF, paintSL, paintC, paintX, paintSH;
    private Path path;

    public DrawTextView(Context context) {
        super(context);
        init();
    }

    public DrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paintTF = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTF.setStyle(Paint.Style.FILL);
        paintTF.setStrokeWidth(3);
        paintTF.setTextSize(34);
        paintTF.setColor(Color.RED);

        paintTS = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTS.setStyle(Paint.Style.STROKE);
        paintTS.setStrokeWidth(3);
        paintTS.setTextSize(34);
        paintTS.setColor(Color.RED);

        paintTFAS = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTFAS.setStyle(Paint.Style.FILL_AND_STROKE);
        paintTFAS.setStrokeWidth(3);
        paintTFAS.setTextSize(34);
        paintTFAS.setColor(Color.RED);

        paintSL = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSL.setStyle(Paint.Style.FILL);
        paintSL.setStrokeWidth(3);
        paintSL.setTextSize(32);
        paintSL.setColor(Color.RED);


        paintC = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintC.setStyle(Paint.Style.FILL);
        paintC.setStrokeWidth(3);
        paintC.setTextSize(32);
        paintC.setColor(Color.RED);

        paintX = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintX.setStyle(Paint.Style.FILL);
        paintX.setStrokeWidth(3);
        paintX.setTextSize(32);
        paintX.setColor(Color.RED);

        path = new Path();

        paintSH = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSH.setStyle(Paint.Style.FILL);
        paintSH.setStrokeWidth(3);
        int[] colors = new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
        LinearGradient linearGradient = new LinearGradient(40, 700, 500, 700, colors, null, Shader.TileMode.CLAMP);
        paintSH.setShader(linearGradient);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawText("实心不描边字：Paint.Style.FILL", 40, 150, paintTF);
        canvas.drawText("空心只描边字：Paint.Style.STROKE", 40, 250, paintTS);
        canvas.drawText("实心加描边字：Paint.Style.FILL_AND_STROKE", 40, 350, paintTFAS);

        paintSL.setShadowLayer(5, 5, 5, Color.YELLOW);
        canvas.drawText("阴影文字", 40, 450, paintSL);

        paintX.setTextSkewX(-0.5f);
        canvas.drawText("斜体文字", 40, 530, paintX);

        path.moveTo(40, 600);
        //path.rCubicTo(200,150,400,-150,600,0);
        path.cubicTo(240, 750, 440, 450, 640, 600);
        canvas.drawTextOnPath("在Path上写的字11111111112222", path, 50, 0, paintTFAS);
        //画出Path
        canvas.drawPath(path, paintTS);
        //画渐变
        canvas.drawRect(40, 700, 500, 1000, paintSH);
    }
}