package com.mgg.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class DrawPathView extends View {
    Paint paintS;

    public DrawPathView(Context context) {
        super(context);
        init();
    }

    public DrawPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paintS = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintS.setStyle(Paint.Style.STROKE);
        paintS.setAntiAlias(true);
        paintS.setStrokeWidth(3);
        paintS.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        canvas.translate(400,500);
        path.lineTo(-100,0);
        path.addCircle(0,0,100, Path.Direction.CCW);
        path.lineTo(-100,200);
        path.lineTo(200,200);
        path.close();
        canvas.drawPath(path,paintS);
    }
}