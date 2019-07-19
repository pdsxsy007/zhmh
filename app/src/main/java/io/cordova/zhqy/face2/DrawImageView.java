package io.cordova.zhqy.face2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by Administrator on 2019/7/19 0019.
 */

@SuppressLint("AppCompatCustomView")
public class DrawImageView extends ImageView {
    private Paint paint;
    private int mFristPointX = 100, mFristPointY = 200;
    private int mSecondPointX = 400, mSecondPointY = 500;
    private boolean isFirstDown = true;

    private int mOldX = 0, mOldY = 0;

    public DrawImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.5f);
        paint.setAlpha(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawRect(new Rect(getmFristPointX(), getmFristPointY(), getmSecondPointX(), getmSecondPointY()), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() != MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Rect mRect = new Rect(getmFristPointX(), getmFristPointY(), getmSecondPointX(), getmSecondPointY());
            if (mRect.contains(x, y)) {
                if (isFirstDown) {
                    mOldX = x;
                    mOldY = y;
                    isFirstDown = false;
                } else {
                    int mXDis = x - mOldX;
                    int mYDis = y - mOldY;
                    mOldX = x;
                    mOldY = y;
                    ReSetVaue(mXDis, mYDis);
                }
            }
        } else {
            isFirstDown = true;
        }
        return true;
    }

    public void setValue(int x, int y) {
        setmFristPointX(x - 50);
        setmFristPointY(y - 50);
        setmSecondPointX(x + 50);
        setmSecondPointY(y + 50);
        invalidate();
    }

    public void ReSetVaue(int xDis, int yDis) {
        setmFristPointX(getmFristPointX() + xDis);
        setmFristPointY(getmFristPointY() + yDis);
        setmSecondPointX(getmFristPointX() + 100);
        setmSecondPointY(getmFristPointY() + 100);
        invalidate();
    }

    public int getmFristPointX() {
        return mFristPointX;
    }

    public void setmFristPointX(int mFristPointX) {
        this.mFristPointX = mFristPointX;
    }

    public int getmFristPointY() {
        return mFristPointY;
    }

    public void setmFristPointY(int mFristPointY) {
        this.mFristPointY = mFristPointY;
    }

    public int getmSecondPointX() {
        return mSecondPointX;
    }

    public void setmSecondPointX(int mSecondPointX) {
        this.mSecondPointX = mSecondPointX;
    }

    public int getmSecondPointY() {
        return mSecondPointY;
    }

    public void setmSecondPointY(int mSeconPointY) {
        this.mSecondPointY = mSeconPointY;
    }

}
