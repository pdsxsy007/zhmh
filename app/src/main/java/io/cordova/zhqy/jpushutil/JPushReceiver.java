package io.cordova.zhqy.jpushutil;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import io.cordova.zhqy.activity.SplashActivity;
import io.cordova.zhqy.utils.ExampleUtil;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;

/**
 * Created by Administrator on 2019/1/9 0009.
 *  推送接收器
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";
    private static int mNotificationNum = 1;
    int NOTIFICATION_SHOW_SHOW_AT_MOST =5;
    private View view;
    private WindowManager wm;
    private boolean showWm =true;//默认是应该显示悬浮通知栏
    private WindowManager.LayoutParams params;

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
                Log.d(TAG, "[MyReceiver] 接收自定义消息 : "+ bundle.getString(JPushInterface.EXTRA_EXTRA));
                processCustomMessage(context,bundle);

//                createFloatView(context);
                // 自定义消息不是通知，默认不会被SDK展示到通
                // 知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
                // 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知"+ JPushInterface.EXTRA_MESSAGE);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                assert bundle != null;
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知"+ bundle.getString(JPushInterface.EXTRA_EXTRA));
                // 在这里根据 JPushInterface.EXTRA_EXTRA(附加字段) 的内容处理代码，
                // 比如打开新的Activity， 打开一个网页等..
                Intent i = new Intent(context, SplashActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
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
        String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String extrasBean = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String msgId = null;

        if (StringUtils.isEmpty(title)) {
            title = " i 轻工大";
        }
        if (!ExampleUtil.isEmpty(extrasBean)) {
            try {
                JSONObject extraJson = new JSONObject(extrasBean);
                if (extraJson.length() > 0) {
                    Log.e("extras", extrasBean);
                    Log.e("extraJson", extraJson.getString("messageId"));
                    msgId =  extraJson.getString("messageId");
                }
            } catch (JSONException e) {

            }
        }

        if (!StringUtils.isEmpty(msgId)){
            SPUtils.put(context,"msgId",msgId);
        }
        Log.e("msg",msgId + " --空");
//        if(mNotificationNum > 4 ){
//            mNotificationNum = 1;
//        }
//        mNotificationNum =   ++ mNotificationNum;
//        Log.e("msg",msg);
//        Log.e("msg1",mNotificationNum+"");
//            if (!BaseWebActivity.isForeground){
//                /*当前页面后台隐藏*/
//                Intent intent = new Intent(context, BaseWebActivity.class);
//                SPUtils.put(context,"msg",msg);
//                PendingIntent pendingIntent = PendingIntent.getActivity(context, mNotificationNum,intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationShowUtils  s = new NotificationShowUtils(context);
//                s .sendNotification(mNotificationNum,bundle,title,msg,pendingIntent);
//            }

//            else {
//                /*当前页面前台展示*/
//                Intent msgIntent = new Intent(BaseWebActivity.MESSAGE_RECEIVED_ACTION);
//                //解析服务器端推送过来的json
//                if(!ExampleUtil.isEmpty(msg)){
//                    msgIntent.putExtra(BaseWebActivity.KEY_MESSAGE, msg);
//                    msgIntent.putExtra(BaseWebActivity.KEY_EXTRAS, msgId);
//                }
//                LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);

//            }
    }



}



