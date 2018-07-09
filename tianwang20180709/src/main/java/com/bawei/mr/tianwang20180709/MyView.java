package com.bawei.mr.tianwang20180709;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author:Created by WangZhiQiang on 2018/7/9.
 */

public class MyView extends View {

    Path mPath;
    private Paint mOutterPaint;
    Canvas mCanvas;
    Bitmap mBitmap;

    int LastX;
    int LastY;
    Bitmap mOutterBitmap;

    int color;
    String text = "刮刮看咯~";
    private int pixelSize;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
//        text = typedArray.getString(R.styleable.MyView_Text);
//        color = typedArray.getColor(R.styleable.MyView_TextColor, Color.GREEN);
//        pixelSize = typedArray.getDimensionPixelSize(R.styleable.MyView_TextSize, 36);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        setupOutPaint();
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setTextSize(36);
//        canvas.drawColor(Color.GRAY);
//
//        float v = paint.measureText(text);
//        float x = (getWidth() - v) / 2;
//        float y = (getHeight() - v) / 2;
//
//        canvas.drawText(text, x, y, paint);
//
        mOutterPaint.setStyle(Paint.Style.STROKE);
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(mOutterBitmap, 0, 0, null);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        mCanvas.drawPath(mPath, mOutterPaint);
    }

    private void setupOutPaint() {
        mOutterPaint.setColor(Color.RED);
        mOutterPaint.setAntiAlias(true);
        mOutterPaint.setDither(true);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutterPaint.setStyle(Paint.Style.FILL);
        mOutterPaint.setStrokeWidth(60);
    }

    //初始化信息
    private void init() {
        mOutterPaint = new Paint();
        mPath = new Path();
        mOutterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                LastX = x;
                LastY = y;
                mPath.moveTo(LastX, LastX);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = Math.abs(x - LastY);
                int dy = Math.abs(y - LastY);

                if (dx > 3 || dy > 3) {
                    mPath.lineTo(x, y);
                }
                LastX = x;
                LastY = y;
                break;
        }
        invalidate();
        return true;
    }
}

