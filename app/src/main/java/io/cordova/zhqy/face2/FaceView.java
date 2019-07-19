package io.cordova.zhqy.face2;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by yubo on 2015/9/5.
 * 画人脸区域的View
 */
@SuppressLint("AppCompatCustomView")
public class FaceView extends ImageView {

    private FaceDetector.Face[] faces;
    private Paint paint;
    private Bitmap bitmap;

    private float left;
    private float top;
    private float right;
    private float bottom;

    private int x;
    private int y;
    private int width;

    public FaceView(Context context) {
        super(context);
        init(context);
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);//设置话出的是空心方框而不是实心方块
    }

    public void setFaces(FaceDetector.Face[] faces, Bitmap bitmap) {
        if(faces != null && faces.length > 0) {
            Log.e("yubo", "FaceView setFaces, face size = " + faces.length);
            this.faces = faces;
            this.bitmap = bitmap;
            setImageBitmap(bitmap);
            calculateFaceArea();
            invalidate();
        }else{
            Log.e("yubo", "FaceView setFaces, faces == null");
        }
    }

    /** 计算人脸区域 */
    private void calculateFaceArea(){
        float eyesDistance = 0;//两眼间距
        for(int i = 0; i < faces.length; i++){
            FaceDetector.Face face = faces[i];
            if(face != null){
                PointF pointF = new PointF();
                face.getMidPoint(pointF);//获取人脸中心点
                eyesDistance = face.eyesDistance();//获取人脸两眼的间距
                //计算人脸的区域
                float delta = eyesDistance / 2;
                left = (pointF.x - eyesDistance) / scaleRate;
                top = (pointF.y - eyesDistance + delta) / scaleRate;
                right = (pointF.x + eyesDistance) / scaleRate;
                bottom = (pointF.y + eyesDistance + delta) / scaleRate;

                x = (int) (pointF.x - eyesDistance);
                y = (int) (pointF.y - eyesDistance + delta);
                width = (int) (eyesDistance * 2);
            }
        }
    }

    private float scaleRate = 1.0f;

    public void setScaleRate(float rate) {
        this.scaleRate = rate;
    }

    /** 清除数据 */
    public void clear(){
        this.faces = null;
        postInvalidate();
    }

    /** 获取人脸区域，适当扩大了一点人脸区域 */
    public Bitmap getFaceArea(){
        if(this.bitmap != null) {
            int bmWidth = bitmap.getWidth();
            int bmHeight = bitmap.getHeight();
            int delta = 50;
            width += 50;
            int height = width;
            x = (int) (left - delta);
            y = (int) (top - delta);
            if(x < 0) {
                x = 0;
            }
            if(y < 0) {
                y = 0;
            }
            if(width > bmWidth) {
                width = bmWidth;
            }
            if(height > bmHeight) {
                height = bmHeight;
            }
            return Bitmap.createBitmap(bitmap, x, y, width, height);
        }
        return null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.faces == null || faces.length == 0) {
            return ;
        }
        canvas.drawRect(left, top, right, bottom, paint);
    }
}
