package io.cordova.zhmh.jpushutil;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import io.cordova.zhmh.MainActivity;
import io.cordova.zhmh.activity.MyShenqingActivity;
import io.cordova.zhmh.activity.OaMsgActivity;
import io.cordova.zhmh.activity.SplashActivity;
import io.cordova.zhmh.activity.SystemMsgActivity;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.StringUtils;


/**
 * Created by Administrator on 2019/1/9 0009.
 *  推送接收器
 */

public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPushReceiver";
    private static int mNotificationNum = 1;
    int NOTIFICATION_SHOW_SHOW_AT_MOST =5;
    private View view;
    private WindowManager wm;
    private boolean showWm =true;//默认是应该显示悬浮通知栏
    private WindowManager.LayoutParams params;
    private int badgeCount = 0;//记录图标的角标数量
    Notification mNotification;
    Notification.Builder mBuilder;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
//          initWindowManager(context);
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                String bundleString = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                Log.d(TAG, " : " + regId);
                Log.d(TAG, " : " + bundleString);
                MyApp.registrationId  = regId;

            } else if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                String message=bundle.getString(JPushInterface.EXTRA_MESSAGE);
                Log.e(TAG, "[MyReceiver] 接收自定义消息 : "+ bundle.getString(JPushInterface.EXTRA_EXTRA));
                processCustomMessage(context,bundle);


//                createFloatView(context);
                // 自定义消息不是通知，默认不会被SDK展示到通
                // 知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
                // 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                    Log.e(TAG, "[MyReceiver] 接收到推送下来的通知"+ JPushInterface.EXTRA_MESSAGE);
               /* badgeCount = (int)SPUtils.get(MyApp.getInstance(),"badgeCount","");
                badgeCount++;
                SPUtils.put(MyApp.getInstance(),"badgeCount",badgeCount);
                if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {
                    String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                    mBuilder = new Notification.Builder(context);
                   *//* mBuilder.setContentTitle(title);
                    mBuilder.setContentText(message);
                    mBuilder.setSmallIcon(R.mipmap.ic_launcher);*//*
                    mNotification = mBuilder.build();
                    ShortcutBadger.applyNotification(context, mNotification, badgeCount);
                } else {
                    ShortcutBadger.applyCount(context, badgeCount);
                }*/

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                assert bundle != null;
                //ToastUtils.showToast(context,"用户点击打开了通知-----MyReceiver");
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知"+ bundle.getString(JPushInterface.EXTRA_EXTRA));

                if (!MainActivity.isForeground){
                    SPUtils.put(MyApp.getInstance(),"InfoType","1");
                    Intent i = new Intent(context, SplashActivity.class);
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    //ToastUtils.showToast(context,"用户点击打开了通知-----MyReceiver111111111111");
                }else {
                    SPUtils.put(MyApp.getInstance(),"InfoType","0");
                    /*Intent i = new Intent(context, OpenClickActivity.class);
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity(i);*/
                    //ToastUtils.showToast(context,"用户点击打开了通知-----MyReceiver22222222222222");
                    processCustomMessage(context,bundle);
                }

             /*   badgeCount = (int)SPUtils.get(MyApp.getInstance(),"badgeCount","");

                badgeCount--;

                if (badgeCount < 0)
                    badgeCount = 0;
                SPUtils.put(MyApp.getInstance(),"badgeCount",badgeCount);
                if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi")) {

                    ShortcutBadger.applyNotification(context, mNotification, badgeCount);
                } else {
                    ShortcutBadger.applyCount(context, badgeCount);

                }*/

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver1]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void processCustomMessage(Context context, Bundle bundle) {
        String msg = null;
        String title = null;

        String extrasBean = null;
        String extrasBean2 = null;
        String msgId = null;
        String msgType = null;

        if(bundle!=null){
            msg =bundle.getString(JPushInterface.EXTRA_MESSAGE);
            title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);

            extrasBean = bundle.getString(JPushInterface.EXTRA_EXTRA);
            extrasBean2 = bundle.getString("JMessageExtra");
            if(!StringUtils.isEmpty(extrasBean)){
                try {
                    JSONObject extraJson = new JSONObject(extrasBean);
                    if (extraJson.length() > 0) {
                        Log.e("extraJson", extrasBean);

                        msgId =  extraJson.getString("messageId");
                        msgType =  extraJson.getString("messageType");
                        Log.e("msgId", msgId);
                        Log.e("msgType", msgType);
                        SPUtils.put(MyApp.getInstance(),"msgId",msgId);
                        SPUtils.put(MyApp.getInstance(),"msgType",msgType);

                    }
                } catch (JSONException ignored) {
                    Log.e("JPush",ignored.getMessage());
                }
            }
           /* else if(!StringUtils.isEmpty(extrasBean2)){

                try {

                    JSONObject extraJson2 = null;
                    extraJson2 = new JSONObject(extrasBean2);
                       *//* Log.e("extras", extrasBean);
                        Log.e("extraJson", extraJson.getString("messageId"));*//*
                    String n_extras = extraJson2.getString("n_extras");
                    JSONObject n_extras2 = new JSONObject(n_extras);
                    msgId =  n_extras2.getString("messageId");
                    msgType =  n_extras2.getString("messageType");
                    Log.e("msgId", msgId);
                    SPUtils.put(MyApp.getInstance(),"msgId",msgId);
                    SPUtils.put(MyApp.getInstance(),"msgType",msgType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }*/
        }
        if (StringUtils.isEmpty(title)) {
            title = " i 轻工大";
        }


            /*当前页面后台隐藏*/
        if (!StringUtils.isEmpty(msgType)){
            Intent intent;
            if (msgType.equals("0")){
                intent = new Intent(MyApp.getInstance(), SystemMsgActivity.class);
                intent.putExtra("msgType","系统消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (msgType.equals("1")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","db");
                intent.putExtra("msgType","待办消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (msgType.equals("2")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","dy");
                intent.putExtra("msgType","待阅消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (msgType.equals("3")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","yb");
                intent.putExtra("msgType","已办消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (msgType.equals("4")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","yy");
                intent.putExtra("msgType","已阅消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else if (msgType.equals("5")){
                intent = new Intent(MyApp.getInstance(), MyShenqingActivity.class);
                intent.putExtra("type","sq");
                intent.putExtra("msgType","我的申请");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            SPUtils.remove(MyApp.getInstance(),"msgType");
            SPUtils.remove(MyApp.getInstance(),"msgId");

        }


    }



}



