package io.cordova.zhqy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.FaceBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.face.AspectRatio;
import io.cordova.zhqy.face.CameraView;
import io.cordova.zhqy.face.FaceSDK;
import io.cordova.zhqy.face.view.RefreshProgress;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.JsonUtil;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.SystemInfoUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.utils.fingerUtil.MD5Util;

import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class FaceActivity extends BaseActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private static final String TAG = "FaceActivity";
    @BindView(R.id.camera)
    CameraView mCameraView;

    private Handler mBackgroundHandler;
    long lastModirTime;

    @BindView(R.id.iv_wai)
    RefreshProgress iv_wai;

    @BindView(R.id.iv_nei)
    RefreshProgress iv_nei;

    @BindView(R.id.iv_close)
    ImageView iv_close;

    @BindView(R.id.iv_face_pic)
    ImageView iv_face_pic;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            byte[] data = (byte[]) msg.obj;
            // 设置参数
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
              BitmapFactory.decodeByteArray(data, 0, data.length, options);
            int height = options.outHeight;
            int width= options.outWidth;
            int inSampleSize = 1; // 默认像素压缩比例，压缩为原图的1/2
           /* int minLen = Math.min(height, width); // 原图的最小边长
            if(minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
                float ratio = (float)minLen / 100.0f; // 计算像素压缩比例
                inSampleSize = (int)ratio;
            }
            Log.e("minLen",minLen+"");
            Log.e("inSampleSize",inSampleSize+"");
            if(minLen <= 1500){
                inSampleSize = 4;
            }else if(minLen <= 2500 && minLen > 1500){
                inSampleSize = 6;
            }
            else if(minLen <= 3500 && minLen > 2500){
                inSampleSize = 5;
            }else {
                inSampleSize = 10;
            }*/
            options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
            options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
            Bitmap scaledBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);; // 解码文件


            //Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            Log.e("wechat", "压缩后图片的大小"+ (scaledBitmap.getByteCount() / 1024)

                    + "M宽度为"+ scaledBitmap.getWidth() + "高度为"+ scaledBitmap.getHeight());

            //iv_face_pic.setImageBitmap(scaledBitmap);
          /*  String s = bitmapToBase64(scaledBitmap);
            Log.e("bitmap",s);
            Log.e("人脸",s+"");
            SPUtils.put(FaceActivity.this,"bitmap",s);
            Intent intent = new Intent();
            intent.setAction("facerefresh");
            sendBroadcast(intent);*/
            try {
                saveMyBitmap(scaledBitmap,"test");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public boolean saveMyBitmap(Bitmap bmp, String bitName) throws IOException {
        File dirFile = new File("./sdcard/DCIM/Camera/");
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File f = new File("./sdcard/DCIM/Camera/" + bitName + ".png");
        boolean flag = false;
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    protected int getResourceId() {
        return R.layout.activity_face;
    }

    @Override
    protected void initView() {
        super.initView();
        iv_wai.Animation2();
        iv_nei.Animation();
        mCameraView.start();
        if (mCameraView != null) {
            mCameraView.addCallback(mCallback);
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCameraView != null) {
                    mCameraView.stop();
                }
                if (mBackgroundHandler == null) {
                    mBackgroundHandler = null;
                }
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCameraView != null) {
            mCameraView.stop();
        }
        if (mBackgroundHandler == null) {
            mBackgroundHandler = null;
        }


    }

    private CameraView.Callback mCallback = new CameraView.Callback() {

        @Override
        public void onCameraOpened(CameraView cameraView) {
            Log.d(TAG, "onCameraOpened");
          /*  mCameraView.setAspectRatio(AspectRatio.of(640, 480));
            mCameraView.setAutoFocus(true);*/

        }

        @Override
        public void onCameraClosed(CameraView cameraView) {
            Log.d(TAG, "onCameraClosed");
            //mCameraView.stop();
        }

        @Override
        public void onPictureTaken(CameraView cameraView, final byte[] data) {
            Log.d(TAG, "onPictureTaken");
            lastModirTime = System.currentTimeMillis();
            finish();

            mBackgroundHandler = null;

            Log.e("人脸",lastModirTime+"");
            Message message = new Message();
            message.obj = data;
            handler.sendMessage(message);


           /* Log.e("人脸",s);
            SPUtils.put(FaceActivity.this,"bitmap",s);*/

        }

        @Override
        public void onPreviewFrame(final byte[] data, final Camera camera) {
            Log.d(TAG, "onPreviewFrame");
            if (System.currentTimeMillis() - lastModirTime <= 1000 || data == null || data.length == 0 ) {
                return;
            }
            Log.i(TAG, "onPreviewFrame " + (data == null ? null : data.length));
            getBackgroundHandler().post(new FaceThread(data, camera));
            lastModirTime = System.currentTimeMillis();
        }
    };





    private Handler getBackgroundHandler() {
        if (mBackgroundHandler == null) {
            HandlerThread thread = new HandlerThread("background");
            thread.start();
            mBackgroundHandler = new Handler(thread.getLooper());
        }
        return mBackgroundHandler;
    }


    private class FaceThread implements Runnable {
        private byte[] mData;
        private ByteArrayOutputStream mBitmapOutput;
        private Matrix mMatrix;
        private Camera mCamera;

        public FaceThread(byte[] data, Camera camera) {
            mData = data;
            mBitmapOutput = new ByteArrayOutputStream();
            mMatrix = new Matrix();
            int mOrienta = mCameraView.getCameraDisplayOrientation();
            mMatrix.postRotate(0);
            mMatrix.postScale(-1, -1);//默认是前置摄像头，直接写死 -1 。
            mCamera = camera;
            mCamera.cancelAutoFocus();
        }

        @Override
        public void run() {
            Bitmap bitmap = null;
            Bitmap roteBitmap  = null ;
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦

                int width = parameters.getPreviewSize().width;
                int height = parameters.getPreviewSize().height;
                YuvImage yuv = new YuvImage(mData, parameters.getPreviewFormat(), width, height, null);
                mData = null ;
                yuv.compressToJpeg(new Rect(0, 0, width, height), 50, mBitmapOutput);

                byte[] bytes = mBitmapOutput.toByteArray();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;//必须设置为565，否则无法检测
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                mBitmapOutput.reset();
                roteBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mMatrix, false);
                List<Rect> rects = FaceSDK.detectionBitmap(bitmap,getResources().getDisplayMetrics().widthPixels,getResources().getDisplayMetrics().heightPixels) ;

                if(null == rects || rects.size() == 0){
                    Log.i("janecer","没有检测到人脸哦");
                } else {
                    Log.i("janecer","检测到有" + rects.size() +"人脸");
                    if(Build.VERSION.SDK_INT <26){
                        finish();

                        mBackgroundHandler = null;
                        Message message = new Message();
                        message.obj = bytes;
                        handler.sendMessage(message);
                    }else {
                        mCameraView.takePicture();
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mMatrix = null ;
                if (bitmap != null) {
                    bitmap.recycle();
                }
                if (roteBitmap != null) {
                    roteBitmap.recycle();
                }

                if (mBitmapOutput != null) {
                    try {
                        mBitmapOutput.close();
                        mBitmapOutput = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }




    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCameraView.stop();
    }
}
