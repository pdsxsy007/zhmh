package io.cordova.zhqy.face2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import android.hardware.Camera;
import android.media.FaceDetector;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

import io.cordova.zhqy.utils.ToastUtils;

/**
 * Created by Administrator on 2019/7/19 0019.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private Context context;
    private Camera camera;
    private FaceView faceView;

    private OnFaceDetectedListener onFaceDetectedListener;

    private int widthSize;
    private int height; // 圆的半径

    /**
     * 摄像头最大的预览尺寸
     */
    private Camera.Size maxPreviewSize;

    /**
     * 预览时Frame的计数器
     */
    private int frameCount;

    /**
     * 是否正在检测人脸
     */
    private boolean isDetectingFace = false;

    /**
     * 是否已检测到人脸
     */
    private boolean detectedFace = false;



    public CameraView(Context context) {
        super(context);
        init(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        getHolder().addCallback(this);
    }

    public void setFaceView(FaceView faceView) {
        if (faceView != null) {
            this.faceView = faceView;
        }
    }

    /** 摄像头重新开始预览 */
    public void reset() {
        if(faceView != null) {
            faceView.setVisibility(View.GONE);
        }
        if(camera != null) {
            camera.setPreviewCallback(this);
            camera.startPreview();
        }
        frameCount = 0;
        detectedFace = false;
        isDetectingFace = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            if(camera != null) {
                camera.setDisplayOrientation(90);
                camera.setPreviewDisplay(holder);
                camera.setPreviewCallback(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera != null) {
            maxPreviewSize = getMaxPreviewSize(camera);


            if (maxPreviewSize != null) {
                ViewGroup.LayoutParams params = getLayoutParams();
                Point point = getScreenSize();
                params.width = point.x/2;
                params.height = (maxPreviewSize.width * point.x / maxPreviewSize.height)/2;
                setLayoutParams(params);
                if(faceView != null) {
                    faceView.setLayoutParams(params);
                }
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewSize(maxPreviewSize.width, maxPreviewSize.height);
                camera.setParameters(parameters);
            }
            camera.startPreview();
            frameCount = 0;
            detectedFace = false;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            try {
                camera.stopPreview();
                camera.setPreviewDisplay(null);
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取手机屏幕的尺寸
     *
     * @return
     */
    private Point getScreenSize() {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return new Point(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    /**
     * 获取摄像头最大的预览尺寸
     *
     * @param camera
     * @return
     */
    private Camera.Size getMaxPreviewSize(Camera camera) {
        List<Camera.Size> list = camera.getParameters().getSupportedPreviewSizes();
        if (list != null) {
            int max = 0;
            Camera.Size maxSize = null;
            for (Camera.Size size : list) {
                int n = size.width * size.height;
                if (n > max) {
                    max = n;
                    maxSize = size;
                }
            }
            return maxSize;
        }
        return null;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.e("yubo", "onPreviewFrame...");
        frameCount++;
        //前面15帧丢弃
        if (frameCount > 30 && !isDetectingFace && !detectedFace) {
            Camera.Size size = camera.getParameters().getPreviewSize();
            final byte[] byteArray = ImageUtils.yuv2Jpeg(data, size.width, size.height);
            isDetectingFace = true;
            camera.stopPreview();
            new Thread() {
                @Override
                public void run() {
                    detectFaces(byteArray);
                }
            }.start();
        }
    }

    /**
     * 检测data数据中是否有人脸，这里需要先旋转一下图片，该方法执行在子线程中
     *
     * @param data
     */
    private void detectFaces(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmap = ImageUtils.rotateBitmap(bitmap, -90);
        bitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
        FaceDetectorUtils.detectFace(bitmap, new FaceDetectorUtils.Callback() {
            @Override
            public void onFaceDetected(final FaceDetector.Face[] faces, final Bitmap bm) {
                isDetectingFace = false;
                Log.e("yubo", "face detected...");
                if (!detectedFace) {
                    detectedFace = true;
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (camera != null) {
                                camera.stopPreview();
                            }
                            if (faceView != null) {
                                float scaleRate = bm.getWidth() * 1.0f / getScreenSize().x;
                                faceView.setScaleRate(scaleRate);
                                faceView.setFaces(faces, bm);
                                faceView.setVisibility(View.VISIBLE);
                            }
                            if (onFaceDetectedListener != null) {
                                onFaceDetectedListener.onFaceDetected(bm);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFaceNotDetected(Bitmap bm) {
                bm.recycle();
                if (faceView != null) {
                    faceView.clear();
                }
                isDetectingFace = false;
            }
        });
    }

    /**
     * 检测到人脸的监听器
     */
    public interface OnFaceDetectedListener {
        void onFaceDetected(Bitmap bm);
    }

    /**
     * 设置监听器，监听检测到人脸的动作
     */
    public void setOnFaceDetectedListener(OnFaceDetectedListener listener) {
        if (listener != null) {
            this.onFaceDetectedListener = listener;
        }
    }

    double screenWidth1;//即屏幕的宽度*0.55，绘制的surfaceview的宽度
    double screenHeight1;//即屏幕的高度*0.55，绘制的surfaceView的高度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);//父类的宽度，
        height = MeasureSpec.getSize(heightMeasureSpec);//父类的高度，
        int screenWidth = CommonUtils.getScreenWidth(getContext());//屏幕的宽度
        int screenHeight = CommonUtils.getScreenHeight(getContext());//屏幕的高度
        //height = screenHeight/2+20;//适配HUAWEIP20的高度
        //height=1000;
        //可以理解为红色的背景盖住了大部分的区域，我们只能看到圆框里面的，如果还是按照原来的比例绘制surfaceview
        //需要把手机拿的很远才可以显示出整张脸，故而我用了一个比较取巧的办法就是，按比例缩小，试验了很多数后，感觉0.55
        //是最合适的比例

        screenWidth1 = 0.55*screenWidth;
      /*  ToastUtils.showToast(context,screenWidth+"");
        ToastUtils.showToast(context,screenHeight+"");*/
        if(screenHeight > 2000){
            screenHeight1 = 0.5*screenHeight;
        }else {
            screenHeight1 = 0.55*screenHeight;
        }

        Log.e("onMeasure", "widthSize="+widthSize);
        Log.e("onMeasure", "draw: widthMeasureSpec = " +screenWidth + "  heightMeasureSpec = " + screenHeight);
        //绘制的输入参数必须是整数型，做浮点型运算后为float型数据，故需要做取整操作
        setMeasuredDimension((int) screenWidth1, (int) screenHeight1);
        //setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    //绘制一个圆形的框，并设置圆框的坐标和半径大小
    public void draw(Canvas canvas) {
        Log.e("onDraw", "draw: test");
        Path path = new Path();
        //path.addCircle(widthSize / 2, height/ 2, widthSize / 2, Path.Direction.CCW);
        path.addCircle((int) screenWidth1 / 2, (int)screenHeight1/ 2, (int) screenWidth1 / 2, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("onDraw", "onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        int screenWidth = CommonUtils.getScreenWidth(getContext());
        int screenHeight = CommonUtils.getScreenHeight(getContext());
        Log.d("screenWidth",Integer.toString(screenWidth));
        Log.d("screenHeight",Integer.toString(screenHeight));
        w = screenWidth;
        h = screenHeight;
        super.onSizeChanged(w, h, oldw, oldh);

    }
}

