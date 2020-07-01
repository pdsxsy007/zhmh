package io.cordova.zhmh.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import io.cordova.zhmh.R;
import io.cordova.zhmh.face.view.RefreshProgress;
import io.cordova.zhmh.face2.FaceView;
import io.cordova.zhmh.utils.BaseActivity3;
import io.cordova.zhmh.utils.BitmapUtils;
import io.cordova.zhmh.utils.SPUtils;


/**
 * Created by Administrator on 2019/7/4 0004.
 */

public class FaceMeColletActivity extends BaseActivity3 {

    io.cordova.zhmh.face2.CameraView cameraView;
    FaceView faceView;
    Bitmap fullBitmap;

    private SensorManager sensorManager;
    private Sensor sensor;
    private FaceMeColletActivity.MySensorListener mySensorListener;
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
        cameraView = (io.cordova.zhmh.face2.CameraView) findViewById(R.id.camera_view);
        faceView = (FaceView) findViewById(R.id.face_view);
        iv = (ImageView) findViewById(R.id.iv);
        //cameraView.setFaceView(faceView);
        cameraView.setOnFaceDetectedListener(new io.cordova.zhmh.face2.CameraView.OnFaceDetectedListener() {
            @Override
            public void onFaceDetected(Bitmap bm) {

                Log.e("test","检测2");
                //检测到人脸后的回调方法
                //SPUtils.put(getApplicationContext(),"isloading2","112");
                finish();
                fullBitmap = bm;
                Message message = new Message();
                message.obj = fullBitmap;
                if(handler !=null){
                    handler.sendMessage(message);
                }




              /*  try {
                    saveMyBitmap(scaledBitmap,"test");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

      iv_close.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              finish();
              SPUtils.put(getApplicationContext(),"isClose","1");
              Intent intent = new Intent();
              intent.setAction("facereYiQingClose02");
              intent.putExtra("FaceActivity","FaceActivity2");
              sendBroadcast(intent);
              handler = null;

          }
      });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            fullBitmap = (Bitmap) msg.obj;
            SPUtils.put(getApplicationContext(),"isloading2","112");
            new Thread(){
                @Override
                public void run() {
                    super.run();
                   /* ByteArrayOutputStream baos=new ByteArrayOutputStream();
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
                    Bitmap scaledBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);; // 解码文件*/
                    Bitmap scaledBitmap = BitmapUtils.compressByWidth(fullBitmap, 150);
                    String s = bitmapToBase64(scaledBitmap);

                     Log.e("人脸",s+"");
                    SPUtils.put(FaceMeColletActivity.this,"bitmap",s);
                    Intent intent = new Intent();
                    intent.setAction("faceMeCollet");
                    intent.putExtra("FaceActivity","FaceActivity");
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

    private void showDialog(){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("计算结果");
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_win_layout, null);
        ImageView imageView = (ImageView) contentView.findViewById(R.id.imageview);
        TextView textView = (TextView) contentView.findViewById(R.id.textview);
        builder.setView(contentView);
        Bitmap bm = faceView.getFaceArea();
        imageView.setImageBitmap(bm);
        iv.setImageBitmap(bm);
        textView.setText("人脸区域亮度：" + getBright(bm) + "\n整幅图片亮度：" + getBright(fullBitmap) + "\n光线传感器的值：" + sensorBright);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cameraView.reset();
            }

        });
        builder.setCancelable(false);
        builder.create().show();*/
    }

    public int getBright(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int r, g, b;
        int count = 0;
        int bright = 0;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                count++;
                int localTemp = bm.getPixel(i, j);
                r = (localTemp | 0xff00ffff) >> 16 & 0x00ff;
                g = (localTemp | 0xffff00ff) >> 8 & 0x0000ff;
                b = (localTemp | 0xffffff00) & 0x0000ff;
                bright = (int) (bright + 0.299 * r + 0.587 * g + 0.114 * b);
            }
        }
        return bright / count;
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


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

}
