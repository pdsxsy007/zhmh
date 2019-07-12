package io.cordova.zhqy.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;


import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.util.ArrayList;
import java.util.List;


import cn.jpush.android.api.JPushInterface;
import io.cordova.zhqy.AppException;
import io.cordova.zhqy.bean.Constants;

public class MyApp extends Application {
    private static MyApp instance;
    public static int H,W;
    public static String registrationId;
    public static String isrRunIng;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);

        instance = this;
      //  L.isDebug=false;
        JPushInterface.init(this);
        getScreen(this);
        MyIntentService.start(this);
      //  regToWX();
      //  initImagePicker();
        isrRunIng = "0";
        Log.e("MyApp","执行了");
        SPUtil.init(this, Constants.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        String localVersionName = getLocalVersionName(instance);
        String imei = MobileInfoUtils.getIMEI(instance);
        //ToastUtils.showToast(instance,imei);
        SPUtils.put(instance,"versionName",localVersionName);
        SPUtils.put(instance,"imei",imei);
        AppException appException = AppException.getInstance();
        appException.init(instance);


        //友盟三方登录
        UMShareAPI.get(this);

        UMConfigure.init(this,"5cf7337d4ca357695c000e67","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.setLogEnabled(true);
        UMConfigure.isDebugLog();
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(instance).setShareConfig(config);
        PlatformConfig.setWeixin("wx19f58a8a96c4ce83", "a0d115c76768a1c9e45b9b3e18d987dd");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3266949769", "f0f70eb003538a699737ebf4f9b0f344", "https://api.weibo.com/oauth2/default.html");
        PlatformConfig.setQQZone("101590782", "2f48de267a8c61637487e48fcc9a7f67");

     /*   //阿里云人脸识别
        AligreenSdkManager.getInstance().init(instance);*/

        //本机号码一键登录
       /* JVerificationInterface.setDebugMode(true);
        JVerificationInterface.init(instance);*/
        //AllowX509TrustManager.allowAllSSL();
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

    public static Context getInstance() {
        return instance;
    }

    /**
     * 获取本地软件版本号名称
     */
    public  String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "本软件的版本号。。" + localVersion);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

}
