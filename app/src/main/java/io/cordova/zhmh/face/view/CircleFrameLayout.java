package io.cordova.zhmh.face.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import io.cordova.zhmh.R;


/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class CircleFrameLayout extends FrameLayout {
    private Paint roundPaint;
    private Paint imagePaint;
    private float mBorderWidth;
    private int mBorderColor;
    private float mRadius;

    public CircleFrameLayout(Context context) {
        this(context, null);
    }

    public CircleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleFrameLayout);
            mRadius = ta.getDimension(R.styleable.CircleFrameLayout_radius, 0);
            mBorderWidth = ta.getDimension(R.styleable.CircleFrameLayout_borderwidth,0);
            mBorderColor = ta.getColor(R.styleable.CircleFrameLayout_borderColor, Color.WHITE);
            ta.recycle();
        }
        roundPaint = new Paint();
        roundPaint.setColor(Color.WHITE);
        roundPaint.setAntiAlias(true);
        roundPaint.setStyle(Paint.Style.FILL);
        roundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        imagePaint = new Paint();
        imagePaint.setXfermode(null);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), imagePaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        //绘制外圆环边框圆环
        drawBorder(canvas);
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

    private void drawBorder(Canvas canvas) {
        if(mBorderWidth!=0&&getWidth()==getHeight()&&mRadius==getWidth()/2){
            Paint paint=new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(mBorderWidth);
            paint.setColor(mBorderColor);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,paint);

        }

    }

    private void drawTopLeft(Canvas canvas) {
        if (mRadius > 0) {
            Path path = new Path();
            path.moveTo(0, mRadius);
            path.lineTo(0, 0);
            path.lineTo(mRadius, 0);
            path.arcTo(new RectF(0, 0, mRadius * 2, mRadius * 2),
                    -90, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (mRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, mRadius);
            path.arcTo(new RectF(width - 2 * mRadius, 0, width,
                    mRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - mRadius);
            path.lineTo(0, height);
            path.lineTo(mRadius, height);
            path.arcTo(new RectF(0, height - 2 * mRadius,
                    mRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (mRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - mRadius);
            path.arcTo(new RectF(width - 2 * mRadius, height - 2
                    * mRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, roundPaint);
        }
    }

}


