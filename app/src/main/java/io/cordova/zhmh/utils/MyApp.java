package io.cordova.zhmh.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
//import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;


import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;


import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jpush.android.api.JPushInterface;
import io.cordova.zhmh.AppException;
import io.cordova.zhmh.bean.Constants;

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
        JPushInterface.init(this);
        getScreen(this);
        //MyIntentService.start(this);

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
        String registrationID = JPushInterface.getRegistrationID(instance);


        //友盟三方登录
        UMShareAPI.get(this);

        UMConfigure.init(this,"5eec580c167edd30ab0000ad","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.setLogEnabled(true);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.isDebugLog();
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(instance).setShareConfig(config);
        PlatformConfig.setWeixin(MyConstants.wxAppId, MyConstants.wxAppSecret);
        //PlatformConfig.setSinaWeibo("3266949769", "f0f70eb003538a699737ebf4f9b0f344", "https://api.weibo.com/oauth2/default.html");
        PlatformConfig.setQQZone("101590782", "2f48de267a8c61637487e48fcc9a7f67");

     /*   //阿里云人脸识别
        AligreenSdkManager.getInstance().init(instance);*/

        //本机号码一键登录
        JVerificationInterface.setDebugMode(true);
        JVerificationInterface.init(instance);


        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {


                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });

   }





    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
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
