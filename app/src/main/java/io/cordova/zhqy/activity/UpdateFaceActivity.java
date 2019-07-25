package io.cordova.zhqy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import io.cordova.zhqy.R;
import io.cordova.zhqy.face.AspectRatio;
import io.cordova.zhqy.face.CameraView;
import io.cordova.zhqy.face.FaceSDK;
import io.cordova.zhqy.face.view.RefreshProgress;
import io.cordova.zhqy.face2.FaceView;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.SystemInfoUtils;

/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class UpdateFaceActivity extends BaseActivity {

    io.cordova.zhqy.face2.CameraView cameraView;
    FaceView faceView;
    Bitmap fullBitmap;

    private SensorManager sensorManager;
    private Sensor sensor;
    private MySensorListener mySensorListener;
    private int sensorBright = 0;
    private ImageView iv;

    @BindView(R.id.iv_wai)
    RefreshProgress iv_wai;

    @BindView(R.id.iv_nei)
    RefreshProgress iv_nei;

    @BindView(R.id.iv_close)
    ImageView iv_close;

    @Override
    protected int getResourceId() {
        return R.layout.activity_test;
    }
    long l0;
    @Override
    protected void initView() {
        super.initView();
        if(!hasFrontCamera()) {
            Toast.makeText(this, "没有前置摄像头", Toast.LENGTH_SHORT).show();
            return ;
        }
        iv_wai.Animation2();
        iv_nei.Animation();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mySensorListener = new MySensorListener();
        sensorManager.registerListener(mySensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        cameraView = (io.cordova.zhqy.face2.CameraView) findViewById(R.id.camera_view);
        faceView = (FaceView) findViewById(R.id.face_view);
        iv = (ImageView) findViewById(R.id.iv);
        //cameraView.setFaceView(faceView);
        cameraView.setOnFaceDetectedListener(new io.cordova.zhqy.face2.CameraView.OnFaceDetectedListener() {
            @Override
            public void onFaceDetected(Bitmap bm) {
                Log.e("test","检测2");
                //检测到人脸后的回调方法
                finish();
                //SPUtils.put(getApplicationContext(),"isloading3","113");
                fullBitmap = bm;
                Message message = new Message();
                message.obj = fullBitmap;
                handler.sendMessage(message);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            fullBitmap = (Bitmap) msg.obj;
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    fullBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte [] data =baos.toByteArray();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
                    BitmapFactory.decodeByteArray(data, 0, data.length, options);
                    int inSampleSize = 1; // 默认像素压缩比例，压缩为原图的1/2
                    int height = options.outHeight;
                    int width= options.outWidth;
                    int minLen = Math.min(height, width); // 原图的最小边长
                    if(minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
                        float ratio = (float)minLen / 100.0f; // 计算像素压缩比例
                        inSampleSize = (int)ratio;
                    }
                    if(minLen <= 1500){
                        inSampleSize = 4;
                    }else if(minLen <= 2500 && minLen > 1500){
                        inSampleSize = 6;
                    }
                    else if(minLen <= 3500 && minLen > 2500){
                        inSampleSize = 5;
                    }else {
                        inSampleSize = 10;
                    }

                    options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
                    options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
                    Bitmap scaledBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);; // 解码文件
                    String s = bitmapToBase64(scaledBitmap);
                    SPUtils.put(UpdateFaceActivity.this,"bitmap2",s);
                    Intent intent = new Intent();
                    intent.setAction("facedata");
                    intent.putExtra("UpdateFaceActivity","UpdateFaceActivity");
                    sendBroadcast(intent);
                }
            }.start();


           /* Intent intent = new Intent(FaceNewActivity.this,NewStudentPrgActivity.class);
            startActivity(intent);
            finish();*/

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

    private class MySensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //光线传感器亮度改变
            sensorBright = (int) sensorEvent.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }


    /**
     * 判断是否有前置摄像
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean hasFrontCamera(){
        Camera.CameraInfo info = new Camera.CameraInfo();
        int count = Camera.getNumberOfCameras();
        for(int i = 0; i < count; i++){
            Camera.getCameraInfo(i, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(mySensorListener);
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
}
