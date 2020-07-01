package io.cordova.zhmh;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import io.cordova.zhmh.activity.InfoDetailsActivity;
import io.cordova.zhmh.activity.SplashActivity;
import io.cordova.zhmh.bean.CountBean;
import io.cordova.zhmh.bean.OAMsgListBean;
import io.cordova.zhmh.bean.SysMsgBean;
import io.cordova.zhmh.service.UpdateServiceSys1;
import io.cordova.zhmh.service.UpdateServiceSys2;
import io.cordova.zhmh.service.UpdateServiceSys3;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppTwoWidget extends AppWidgetProvider {

    private final String action1 = "io.cordova.zhmh.appwidgetprovider.ll01";
    private final String action2 = "io.cordova.zhmh.appwidgetprovider.ll02";
    private final String action3 = "io.cordova.zhmh.appwidgetprovider.ll03";
    private final String action8 = "io.cordova.zhmh.appwidgetprovider.click8";


    public NewAppTwoWidget() {

    }

    private ComponentName thisWidget;
    RemoteViews remoteViews;



    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("NewAppTwoWidget",intent.getAction());
        String action = intent.getAction();
        String content = intent.getStringExtra("content");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, NewAppTwoWidget.class);
        RemoteViews remoteViews = null;
        // int flag = intent.getIntExtra("flag", 0);
        String noLoginState = intent.getStringExtra("noLoginState");
        if(null != noLoginState ){
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget_two2);
        }else {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget_two);
        }


        if(action.equals(action1)){
            remoteViews.setTextColor(R.id.tv_01, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.tv1, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.tv_02, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv_03, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv3, Color.parseColor("#000000"));
            SysMsgBean sysMsgBean = JsonUtil.parseJson(content,SysMsgBean.class);
            Intent intent1 = new Intent(context, UpdateServiceSys1.class);
            intent1.putExtra("content",systemData);
            //SPUtils.put(context,"appcontent",body);
            remoteViews.setRemoteAdapter(R.id.lv, intent1);

            Intent intent2 = new Intent(context,InfoDetailsActivity.class);
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);


        }else if(action.equals(action2)){
            remoteViews.setTextColor(R.id.tv_01, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv_02, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.tv2, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.tv_03, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv3, Color.parseColor("#000000"));

            Intent intent1 = new Intent(context, UpdateServiceSys2.class);
            intent1.putExtra("content",dbData);
            //SPUtils.put(context,"appcontent",body);
            remoteViews.setRemoteAdapter(R.id.lv, intent1);

           /* Intent intent2 = new Intent(context,BaseWebCloseActivity.class);
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);*/

        }else if(action.equals(action3)){
            remoteViews.setTextColor(R.id.tv_01, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv_02, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.tv_03, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.tv3, Color.parseColor("#42adbb"));
            OAMsgListBean oaMsgListBean = JSON.parseObject(content, OAMsgListBean.class);
            Intent intent1 = new Intent(context, UpdateServiceSys3.class);
            intent1.putExtra("content",dyData);
            //SPUtils.put(context,"appcontent",body);
            remoteViews.setRemoteAdapter(R.id.lv, intent1);

           /* Intent intent2 = new Intent(context,BaseWebCloseActivity.class);

            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);*/

        }else if(action.equals(action8)){
            //ToastUtils.showToast(context,"点击登录按钮");

            Intent intent1 = new Intent(context,SplashActivity.class);
            intent.putExtra("openlogin","openlogin");
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentTemplate);


           /* Intent intent2 = new Intent(context,LoginActivity2.class);
            PendingIntent pendingIntentTemplate2 = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentTemplate2);*/
        }else if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")){
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, NewAppTwoWidget.class));
            NewAppTwoWidget myWidget = new NewAppTwoWidget();
            myWidget.onUpdate(context, AppWidgetManager.getInstance(context),ids);
        }

        appWidgetManager.updateAppWidget(componentName, remoteViews);
        super.onReceive(context,intent);
    }

    String systemData;
    String dbData;
    String dyData;
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

        //super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e("onUpdate","onUpdate");

        String username = (String) SPUtils.get(context,"usernameeidt","");
        if(username.equals("")){
            int count = appWidgetIds.length;
            for (int i = 0; i < count; i++) {
                int appWidgetId = appWidgetIds[i];
                onWidgetUpdate(context, appWidgetManager, appWidgetId);
            }
        }else {
            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_countUnreadMessagesForCurrentUser)
                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("系统数量",response.body());
                            CountBean countBean = JSON.parseObject(response.body(), CountBean.class);

                            systemCount = countBean.getObj();


                            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                                    .params("type", "db")
                                    .params("workType", "workdb")
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            Log.e("待办数量",response.body());
                                            CountBean countBean = JSON.parseObject(response.body(), CountBean.class);

                                            dbCount = countBean.getCount() + "";


                                            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                                                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                                                    .params("type", "dy")
                                                    .params("workType", "workdb")
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(Response<String> response) {
                                                            Log.e("待阅数量",response.body());
                                                            CountBean countBean = JSON.parseObject(response.body(), CountBean.class);

                                                            dyCount = countBean.getCount() + "";

                                                            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.System_Msg_List)
                                                                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                                                                    .params("pageSize", 20)
                                                                    .params("pageNum",1)
                                                                    .execute(new StringCallback() {
                                                                        @Override
                                                                        public void onSuccess(Response<String> response) {

                                                                            SysMsgBean sysMsgBean2 = JSON.parseObject(response.body(), SysMsgBean.class);

                                                                            systemData = response.body();
                                                                            SPUtils.put(context,"systemData",systemData);

                                                                            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_workFolwDbList)
                                                                                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                                                                                    .params("size", 15*1)
                                                                                    .params("type","db")
                                                                                    .params("workType","workdb")
                                                                                    .execute(new StringCallback() {
                                                                                        @Override
                                                                                        public void onSuccess(Response<String> response) {
                                                                                            //Log.e("s",response.toString());
                                                                                            OAMsgListBean oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);

                                                                                            dbData = response.body();
                                                                                            SPUtils.put(context,"dbData",dbData);
                                                                                            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_workFolwDbList)
                                                                                                    .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                                                                                                    .params("size", 15*1)
                                                                                                    .params("type","dy")
                                                                                                    .params("workType","workdb")
                                                                                                    .execute(new StringCallback() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Response<String> response) {
                                                                                                            //Log.e("s",response.toString());
                                                                                                            OAMsgListBean oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);

                                                                                                            dyData = response.body();
                                                                                                            SPUtils.put(context,"dyData",dyData);
                                                                                                            int count = appWidgetIds.length;
                                                                                                            for (int i = 0; i < count; i++) {
                                                                                                                int appWidgetId = appWidgetIds[i];
                                                                                                                onWidgetUpdate(context, appWidgetManager, appWidgetId);
                                                                                                            }
                                                                                                        }
                                                                                                        @Override
                                                                                                        public void onError(Response<String> response) {
                                                                                                            super.onError(response);


                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                        @Override
                                                                                        public void onError(Response<String> response) {
                                                                                            super.onError(response);


                                                                                        }
                                                                                    });
                                                                        }
                                                                        @Override
                                                                        public void onError(Response<String> response) {
                                                                            super.onError(response);

                                                                        }
                                                                    });


                                                        }

                                                        @Override
                                                        public void onError(Response<String> response) {
                                                            super.onError(response);

                                                        }
                                                    });

                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);

                                        }
                                    });


                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    String dyCount = "0";

    String dbCount= "0";

    String systemCount = "0";

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        thisWidget = new ComponentName(context, NewAppTwoWidget.class);
        String username = (String) SPUtils.get(context,"usernameeidt","");
        if(username.equals("")){
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget_two2);
            Intent intentStart4 = new Intent(action8);
            intentStart4.setComponent(new ComponentName(context,NewAppTwoWidget.class));
            intentStart4.putExtra("noLoginState","noLoginState");
            PendingIntent pendingIntentStart4 = PendingIntent.getBroadcast(context, 0, intentStart4,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentStart4);
        }else {
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget_two);
            Intent topIntent = new Intent(context,NewAppTwoWidget.class).setAction(action1);
            PendingIntent topPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, topIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.ll_01, topPendingIntent);


            Intent topIntent2 = new Intent(context,NewAppTwoWidget.class).setAction(action2);
            PendingIntent topPendingIntent2 = PendingIntent.getBroadcast(context, appWidgetId, topIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.ll_02, topPendingIntent2);


            Intent topIntent3 = new Intent(context,NewAppTwoWidget.class).setAction(action3);
            PendingIntent topPendingIntent3 = PendingIntent.getBroadcast(context, appWidgetId, topIntent3, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.ll_03, topPendingIntent3);


            remoteViews.setTextViewText(R.id.tv_01,systemCount);

            remoteViews.setTextViewText(R.id.tv_02,dbCount);

            remoteViews.setTextViewText(R.id.tv_03,dyCount);

            Intent intent = new Intent(context, UpdateServiceSys1.class);
            intent.putExtra("content",systemData);
            //SPUtils.put(context,"appcontent",body);
            remoteViews.setRemoteAdapter(R.id.lv, intent);

            Intent intent2 = new Intent(context,InfoDetailsActivity.class);
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);

/*
            Intent intent2 = new Intent(context, UpdateServiceSys1.class);
            intent.putExtra("content",systemData);
            //SPUtils.put(context,"appcontent",body);
            remoteViews.setRemoteAdapter(R.id.lv, intent2);

            Intent intent2 = new Intent(context,InfoDetailsActivity.class);
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);*/

        }


        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }



    @Override
    public void onEnabled(Context context) {

        Log.e("onEnabled","onEnabled");
       /* PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("io.cordova.zhmh","io.cordova.zhmh.NewAppWidget.class"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/
    }



    @Override
    public void onDisabled(Context context) {
        Log.e("onDisabled","onDisabled");

    }


}

