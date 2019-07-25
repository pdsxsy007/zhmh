package io.cordova.zhqy.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.ToastUtils;


import static io.cordova.zhqy.utils.MyApp.isrRunIng;

/**
 * Created by Administrator on 2019/4/26 0026.
 */

public class OpenClickActivity extends Activity  {
    /**华为 OPPO  Fac*/
    private static final String TAG = "HuaWei";
    /**消息Id**/
    private static final String KEY_MSGID = "msg_id";
    private static final String KEY_TYPE = "msg_type";
    /**该通知的下发通道**/
    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
    /**通知标题**/
    private static final String KEY_TITLE = "n_title";
    /**通知内容**/
    private static final String KEY_CONTENT = "n_content";
    /**通知附加字段**/
    private static final String KEY_EXTRAS = "n_extras";
    private TextView mTextView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView = new TextView(this);
        setContentView(mTextView);

        handleOpenClick();



    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    /**
     * 处理点击事件，当前启动配置的Activity都是使用
     * Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
     * 方式启动，只需要在onCreat中调用此方法进行处理
     */
    private void handleOpenClick() {
        Log.e("OpenClickActivity", "用户点击打开了通知");
        //ToastUtils.showToast(OpenClickActivity.this,"用户点击打开了通知");
        String data = null;
        //获取华为平台附带的jpush信息
        if (getIntent().getData() != null) {
            data = getIntent().getData().toString();
        }
        //获取fcm或oppo平台附带的jpush信息
        if (TextUtils.isEmpty(data) && getIntent().getExtras() != null) {
            data = getIntent().getExtras().getString("JMessageExtra");
        }

        Log.e(TAG, " -消息体- " + String.valueOf(data));
        if (TextUtils.isEmpty(data)) return;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String msgId = jsonObject.optString(KEY_MSGID);
            String msgType = jsonObject.optString(KEY_TYPE);
            byte whichPushSDK = (byte) jsonObject.optInt(KEY_WHICH_PUSH_SDK);
            String title = jsonObject.optString(KEY_TITLE);
            String content = jsonObject.optString(KEY_CONTENT);
            String extras = jsonObject.optString(KEY_EXTRAS);
            if (StringUtils.isEmpty(title)){
                title = "i 轻工大";
            }
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (extraJson.length() > 0) {
                    Log.e("extras", extras);
                    Log.e("extraJson", extraJson.getString("messageId"));
                    msgId =  extraJson.getString("messageId");
                    msgType =  extraJson.getString("messageType");
                    SPUtils.put(MyApp.getInstance(),"msgId",msgId);
                    SPUtils.put(MyApp.getInstance(),"msgType",msgType);
                }

//                if (Main2Activity.isForeground){
//                    setGoPushMsg(msgType);
//                }else {
//                    Intent i = new Intent(MyApp.getInstance(), SplashActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
//                }

                if (isrRunIng.equals("1")){
                    setGoPushMsg(msgType);
                    SPUtils.put(MyApp.getInstance(),"InfoType","0");
                } else if (isrRunIng.equals("0")) {
                    Intent i = new Intent(MyApp.getInstance(), SplashActivity.class);
                    startActivity(i);
                    finish();
                    SPUtils.put(MyApp.getInstance(),"InfoType","0");
                }


            } catch (JSONException ignored) {
                Log.e("JPush",ignored.getMessage());
                //ToastUtils.showToast(OpenClickActivity.this,ignored.getMessage());
            }

            //上报点击事件
            JPushInterface.reportNotificationOpened(this, msgId, whichPushSDK);


        } catch (JSONException e) {
            Log.e(TAG, "parse notification error");
        }
    }

    /**
     * 推送消息跳转
     * msgType 消息类型(-1:删除，0:系统消息,1:待办,2:待阅,3:已办,4:已阅,5:我的申请)
     *
     *
     * @param msgType*/
    private void setGoPushMsg(String msgType) {
        if (!StringUtils.isEmpty(msgType)){
            Intent intent;
            if (msgType.equals("0")){
                intent = new Intent(MyApp.getInstance(), SystemMsgActivity.class);
                intent.putExtra("msgType","系统消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (msgType.equals("1")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","db");
                intent.putExtra("msgType","待办消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (msgType.equals("2")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","dy");
                intent.putExtra("msgType","待阅消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (msgType.equals("3")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","yb");
                intent.putExtra("msgType","已办消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (msgType.equals("4")){
                intent = new Intent(MyApp.getInstance(), OaMsgActivity.class);
                intent.putExtra("type","yy");
                intent.putExtra("msgType","已阅消息");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (msgType.equals("5")){
                intent = new Intent(MyApp.getInstance(), MyShenqingActivity.class);
                intent.putExtra("type","sq");
                intent.putExtra("msgType","我的申请");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            SPUtils.remove(MyApp.getInstance(),"msgType");
            SPUtils.remove(MyApp.getInstance(),"msgId");
            finish();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleOpenClick();
    }
}
