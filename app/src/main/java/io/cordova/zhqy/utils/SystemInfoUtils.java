package io.cordova.zhqy.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2019/5/9 0009.
 */

public class SystemInfoUtils {

    /**
     * 获取手机型号
     * @return
     */
    public static String getDeviceModel(){
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机版本号
     * @return
     */
    public static String getDISPLAYVersion(){
        return android.os.Build.DISPLAY;
    }

    /**
     * 获取手机系统内核
     * @return
     */
    public static String getDeviceAndroidVersion(){
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机CPU
     * @return
     */
    public static String getDeviceCpu(){
        return android.os.Build.CPU_ABI;
    }


    public static int getWindowWidth(Context context){
    // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        return mScreenWidth;
    }
    public static int getWindowHeigh(Context context){
// 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenHeigh = dm.heightPixels;
        return mScreenHeigh;
    }


}
