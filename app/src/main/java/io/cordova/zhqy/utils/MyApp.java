package io.cordova.zhqy.utils;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import cn.jpush.android.api.JPushInterface;

public class MyApp extends Application {
    private static MyApp instance;
    public static int H,W;
    public static String registrationId;

    @Override
    public void onCreate() {
        super.onCreate();
//        JPushInterface.setDebugMode(true);

        instance = this;
      //  L.isDebug=false;
        JPushInterface.init(this);
        getScreen(this);
        MyIntentService.start(this);
      //  regToWX();
      //  initImagePicker();

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public void getScreen(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H=dm.heightPixels;
        W=dm.widthPixels;
    }

//    public void initImagePicker() {
////        //ImagePicker的配置
////        ImagePicker imagePicker = ImagePicker.getInstance();
////        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
////        imagePicker.setShowCamera(true);  //显示拍照按钮
////        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
////        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
////        imagePicker.setSelectLimit(1);    //选中数量限制
////        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
////        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
////        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
////        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
////        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
//    }

//    private void regToWX() {
////        api = WXAPIFactory.createWXAPI(this, APP_ID,true);
//      //  api.registerApp(APP_ID);
//    }

    public static Context getInstance() {
        return instance;
    }


}
