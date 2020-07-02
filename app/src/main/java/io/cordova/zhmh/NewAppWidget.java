package io.cordova.zhmh;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;
import java.util.List;

import io.cordova.zhmh.activity.SplashActivity;
import io.cordova.zhmh.bean.AppWidgetBean;
import io.cordova.zhmh.service.UpdateService2;
import io.cordova.zhmh.service.UpdateService3;
import io.cordova.zhmh.service.UpdateService4;
import io.cordova.zhmh.service.UpdateService1;
import io.cordova.zhmh.service.UpdateService5;
import io.cordova.zhmh.service.UpdateService6;
import io.cordova.zhmh.service.UpdateService7;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.DateUtils;
import io.cordova.zhmh.utils.GpsUtils;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.web.ClassWebActivity;

import static io.cordova.zhmh.UrlRes.insertportalPositionUrl;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private final String action1 = "io.cordova.zhmh.appwidgetprovider.click1";
    private final String action2 = "io.cordova.zhmh.appwidgetprovider.click2";
    private final String action3 = "io.cordova.zhmh.appwidgetprovider.click3";
    private final String action4 = "io.cordova.zhmh.appwidgetprovider.click4";
    private final String action5 = "io.cordova.zhmh.appwidgetprovider.click5";
    private final String action6 = "io.cordova.zhmh.appwidgetprovider.click6";
    private final String action7 = "io.cordova.zhmh.appwidgetprovider.click7";
    private final String action8 = "io.cordova.zhmh.appwidgetprovider.click8";


    public NewAppWidget() {

    }

    private ComponentName thisWidget;
    RemoteViews remoteViews;



    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();
        String content = intent.getStringExtra("content");
        AppWidgetBean appWidgetBean = JsonUtil.parseJson(content,AppWidgetBean.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = null;
       // int flag = intent.getIntExtra("flag", 0);
        String noLoginState = intent.getStringExtra("noLoginState");
        if(null != noLoginState ){
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget2);
        }else {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        }

        ComponentName componentName = new ComponentName(context, NewAppWidget.class);
        Log.e("onReceive执行了",action);
        if(action.equals(action1)){//左边的按钮
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                mondayList = appWidgetBean.getAttributes().getMondayList();
                if(mondayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    Intent intent1 = new Intent(context, UpdateService1.class);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }



        }else if(action.equals(action2)){
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                tuesdayList = appWidgetBean.getAttributes().getTuesdayList();
                if(tuesdayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.VISIBLE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.GONE);
                    Intent intent1 = new Intent(context, UpdateService2.class);
                    intent1.putExtra("content",content);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }


        }else if(action.equals(action3)){
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                wednesdayList = appWidgetBean.getAttributes().getWednesdayList();
                if(wednesdayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.VISIBLE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.GONE);
                    Intent intent1 = new Intent(context, UpdateService3.class);
                    intent1.putExtra("content",content);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    //boolean run = isRun(context);
                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);

                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }

        }else if(action.equals(action4)){
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                thursdayList = appWidgetBean.getAttributes().getThursdayList();
                if(thursdayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.VISIBLE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.GONE);
                    Intent intent1 = new Intent(context, UpdateService4.class);
                    intent1.putExtra("content",content);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }



        }else if(action.equals(action5)){
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                fridayList = appWidgetBean.getAttributes().getFridayList();
                if(fridayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.VISIBLE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.GONE);
                    Intent intent1 = new Intent(context, UpdateService5.class);
                    intent1.putExtra("content",content);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    //intent2.putExtra("refreshWeb","refreshWeb");
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }




        }else if(action.equals(action6)){
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#42adbb"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                 saturdayList = appWidgetBean.getAttributes().getSaturdayList();

                if(saturdayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.VISIBLE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.GONE);
                    Intent intent1 = new Intent(context, UpdateService6.class);
                    intent1.putExtra("content",content);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }

        }else if(action.equals(action7)){
            remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
            remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#42adbb"));
            AppWidgetBean.Attributes attributes = appWidgetBean.getAttributes();
            if(attributes != null){
                sundayList = appWidgetBean.getAttributes().getSundayList();
                if(sundayList.size() == 0){
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.VISIBLE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.GONE);
                    Intent intent1 = new Intent(context, UpdateService7.class);
                    intent1.putExtra("content",content);
                    remoteViews.setRemoteAdapter(R.id.lv, intent1);

                    Intent intent2 = new Intent(context,ClassWebActivity.class);
                    PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                }
            }else {
                remoteViews.setViewVisibility(R.id.lv, View.GONE);
                remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
            }


        }else if(action.equals(action8)){
            //ToastUtils.showToast(context,"点击登录按钮");

            Intent intent1 = new Intent(context,SplashActivity.class);
            intent.putExtra("openlogin","openlogin");
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentTemplate);


           /* Intent intent2 = new Intent(context,LoginActivity2.class);
            PendingIntent pendingIntentTemplate2 = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentTemplate2);*/
        }
        else if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")){
            int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
            NewAppWidget myWidget = new NewAppWidget();
            myWidget.onUpdate(context, AppWidgetManager.getInstance(context),ids);
        }


        appWidgetManager.updateAppWidget(componentName, remoteViews);
        super.onReceive(context,intent);
    }

    private int flag = 0;
    List<AppWidgetBean.MondayList> mondayList;
    List<AppWidgetBean.TuesdayList> tuesdayList;
    List<AppWidgetBean.WednesdayList> wednesdayList;
    List<AppWidgetBean.ThursdayList> thursdayList;
    List<AppWidgetBean.FridayList> fridayList;
    List<AppWidgetBean.SaturdayList> saturdayList;
    List<AppWidgetBean.SundayList> sundayList;
    String body;
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

        //super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e("onUpdate","onUpdate");

        try {
            String username = (String) SPUtils.get(context,"usernameeidt","");
            String rolecodes = (String) SPUtils.get(context,"rolecodes","");
            Log.e("username---",username);
            Log.e("rolecodes",rolecodes);
            String type = "0";
            if(rolecodes.contains("student")){
                type = "1";
            }else {
                type ="2";
            }

            String encode = URLEncoder.encode(AesEncryptUtile.encrypt(username, "gilight@#1234567"), "UTF-8");
            final String today = DateUtils.getToday();
            OkGo.<String>get(UrlRes.HOME_URL+UrlRes.getMobileCourseUrl)
                    .tag(this)
                    .params("userName",encode)
                    .params("role",type)
                    .params("currentTime",today)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("appwidgetcontent",response.body());

                            body = response.body();
                            SPUtils.put(context,"appcontent",body);
                            AppWidgetBean appWidgetBean = JsonUtil.parseJson(response.body(),AppWidgetBean.class);
                            boolean success = appWidgetBean.getSuccess();
                            if(success){

                                if(appWidgetBean.getAttributes() != null){
                                    List<String> stringdates = appWidgetBean.getAttributes().getString();

                                    mondayList = appWidgetBean.getAttributes().getMondayList();

                                    tuesdayList = appWidgetBean.getAttributes().getTuesdayList();

                                    wednesdayList = appWidgetBean.getAttributes().getWednesdayList();

                                    thursdayList = appWidgetBean.getAttributes().getThursdayList();

                                    fridayList =  appWidgetBean.getAttributes().getFridayList();

                                    saturdayList = appWidgetBean.getAttributes().getSaturdayList();

                                    sundayList = appWidgetBean.getAttributes().getSundayList();
                                    flag = 0;
                                    for (int i = 0; i < stringdates.size(); i++) {

                                        if(today.equals(stringdates.get(i))){
                                            flag = i+1;

                                            break;
                                        }
                                    }
                                }else {

                                    String s = DateUtils.dateToWeek(today);
                                    if(s.equals("星期一")){
                                        flag = 1;
                                    }else if(s.equals("星期二")){
                                        flag = 2;
                                    }else if(s.equals("星期三")){
                                        flag = 3;
                                    }else if(s.equals("星期四")){
                                        flag = 4;
                                    }else if(s.equals("星期五")){
                                        flag = 5;
                                    }else if(s.equals("星期六")){
                                        flag = 6;
                                    }else if(s.equals("星期日")){
                                        flag = 7;
                                    }

                                }


                            }else {
                                flag = 0;
                            }


                            int count = appWidgetIds.length;
                            for (int i = 0; i < count; i++) {
                                int appWidgetId = appWidgetIds[i];
                                onWidgetUpdate(context, appWidgetManager, appWidgetId);
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


       /* int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }*/


    }



    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        thisWidget = new ComponentName(context, NewAppWidget.class);
        if(flag == 0){//没有登录状态
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget2);
            Intent intentStart4 = new Intent(action8);
            intentStart4.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart4.putExtra("noLoginState","noLoginState");
            PendingIntent pendingIntentStart4 = PendingIntent.getBroadcast(context, 0, intentStart4,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentStart4);

           /* Intent intent1 = new Intent(context,MainActivity.class);
            PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentTemplate);


            Intent intent2 = new Intent(context,LoginActivity2.class);
            PendingIntent pendingIntentTemplate2 = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_login, pendingIntentTemplate2);*/


        }else {
            remoteViews = new RemoteViews(context.getPackageName(),R.layout.new_app_widget);

            String longitude = (String) SPUtils.get(context,"longitude","");
            String latitude = (String) SPUtils.get(context,"latitude","");
            if(longitude.equals("")){
                Location location = GpsUtils.getInstance(context).showLocation();
                longitude = location.getLatitude()+"";
                latitude = location.getLongitude()+"";

            }

            OkGo.<String>post(UrlRes.HOME_URL+insertportalPositionUrl)
                    .tag(this)
                    .params("memberId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                    .params("positionLongitude", longitude)
                    .params("positionLatitude", latitude)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("小组件定位",response.body());

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });
           /* Location location = GpsUtils.getInstance(context).showLocation();
            String address = "纬度：" + location.getLatitude() + "经" + location.getLongitude();
            Log.e( "获取手机定位坐标",address );
            remoteViews.setTextViewText(R.id.tv_jingdu,"经"+location.getLongitude());
            remoteViews.setTextViewText(R.id.tv_weidu,"纬"+location.getLatitude());*/

            if(flag == 1){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#139d7e"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
                List<AppWidgetBean.MondayList> wednesdayLists = getMondayListDatas();
                if(wednesdayLists != null){
                    if(wednesdayLists.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService1.class);
                        intent.putExtra("content",body);
                        //SPUtils.put(context,"appcontent",body);
                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,ClassWebActivity.class);
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }

            if(flag == 2){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#139d7e"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
                List<AppWidgetBean.TuesdayList> wednesdayLists = getTuesdayList();
                if(wednesdayLists != null){
                    if(wednesdayLists.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService2.class);
                        intent.putExtra("content",body);

                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,ClassWebActivity.class);
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }


            if(flag == 3){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#139d7e"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
                List<AppWidgetBean.WednesdayList> wednesdayLists = getWednesdayList();
                if(wednesdayLists != null){
                    if(wednesdayLists.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService3.class);
                        intent.putExtra("content",body);
                        // SPUtils.put(context,"appcontent",body);
                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,ClassWebActivity.class);
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }

            if(flag == 4){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#139d7e"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
                List<AppWidgetBean.ThursdayList> thursdayListDatas = getThursdayListDatas();
                if(thursdayListDatas != null){
                    if(thursdayListDatas.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService4.class);
                        intent.putExtra("content",body);
                        // SPUtils.put(context,"appcontent",body);
                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,ClassWebActivity.class);
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }

            if(flag == 5){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#139d7e"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
                List<AppWidgetBean.FridayList> thursdayListDatas = getFridayListDatas();
                if(thursdayListDatas != null){
                    if(thursdayListDatas.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService5.class);
                        intent.putExtra("content",body);
                        //SPUtils.put(context,"appcontent",body);
                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,MainActivity.class);
                        intent2.putExtra("refreshWeb","refreshWeb");
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }

            if(flag == 6){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#139d7e"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#000000"));
                List<AppWidgetBean.SaturdayList> thursdayListDatas = getSaturdayList();
                if(thursdayListDatas != null){
                    if(thursdayListDatas.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService6.class);
                        intent.putExtra("content",body);
                        //SPUtils.put(context,"appcontent",body);
                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,ClassWebActivity.class);
                        intent2.putExtra("refreshWeb","refreshWeb");
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }

            if(flag == 7){
                remoteViews.setTextColor(R.id.appwidget_text1, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text2, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text3, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text4, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text5, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text6, Color.parseColor("#000000"));
                remoteViews.setTextColor(R.id.appwidget_text7, Color.parseColor("#139d7e"));
                List<AppWidgetBean.SundayList> thursdayListDatas = getSundayList();
                if(thursdayListDatas != null){
                    if(thursdayListDatas.size() == 0){
                        remoteViews.setViewVisibility(R.id.lv, View.GONE);
                        remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                    }else {
                        Intent intent = new Intent(context, UpdateService7.class);
                        intent.putExtra("content",body);
                        //SPUtils.put(context,"appcontent",body);
                        remoteViews.setRemoteAdapter(R.id.lv, intent);

                        Intent intent2 = new Intent(context,ClassWebActivity.class);
                        PendingIntent pendingIntentTemplate = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
                        remoteViews.setPendingIntentTemplate(R.id.lv, pendingIntentTemplate);
                    }
                }else {
                    remoteViews.setViewVisibility(R.id.lv, View.GONE);
                    remoteViews.setViewVisibility(R.id.rl_empty, View.VISIBLE);
                }


            }



        /*    Intent intentStart1 = new Intent(action1);
            intentStart1.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart1.putExtra("content",body);
            intentStart1.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            PendingIntent pendingIntentStart1 = PendingIntent.getBroadcast(context, 0, intentStart1,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_01, pendingIntentStart1);
*/
            Intent topIntent = new Intent(context,NewAppWidget.class).setAction(action1);
            topIntent.putExtra("content",body);
            topIntent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            PendingIntent topPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, topIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_01, topPendingIntent);


            /*Intent intentStart2 = new Intent(action2);
            intentStart2.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart2.putExtra("content",body);
            intentStart2.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            PendingIntent pendingIntentStart2 = PendingIntent.getBroadcast(context, 0, intentStart2,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_02, pendingIntentStart2);*/

            Intent intentStart2 = new Intent(context,NewAppWidget.class).setAction(action2);
            intentStart2.putExtra("content",body);
            intentStart2.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            PendingIntent topPendingIntent2 = PendingIntent.getBroadcast(context, appWidgetId, intentStart2, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_02, topPendingIntent2);

            Intent intentStart3 = new Intent(action3);
            intentStart3.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart3.putExtra("content",body);
            intentStart3.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //SPUtils.put(context,"appcontent",body);
            PendingIntent pendingIntentStart3 = PendingIntent.getBroadcast(context, 0, intentStart3,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_03, pendingIntentStart3);

            Intent intentStart4 = new Intent(action4);
            intentStart4.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart4.putExtra("content",body);
            intentStart4.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //SPUtils.put(context,"appcontent",body);
            PendingIntent pendingIntentStart4 = PendingIntent.getBroadcast(context, 0, intentStart4,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_04, pendingIntentStart4);

            Intent intentStart5 = new Intent(action5);
            intentStart5.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart5.putExtra("content",body);
            intentStart5.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //SPUtils.put(context,"appcontent",body);
            PendingIntent pendingIntentStart5 = PendingIntent.getBroadcast(context, 0, intentStart5,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_05, pendingIntentStart5);

            Intent intentStart6 = new Intent(action6);
            intentStart6.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart6.putExtra("content",body);
            intentStart6.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //SPUtils.put(context,"appcontent",body);
            PendingIntent pendingIntentStart6 = PendingIntent.getBroadcast(context, 0, intentStart6,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_06, pendingIntentStart6);

            Intent intentStart7 = new Intent(action7);
            intentStart7.setComponent(new ComponentName(context,NewAppWidget.class));
            intentStart7.putExtra("content",body);
            intentStart7.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //SPUtils.put(context,"appcontent",body);
            PendingIntent pendingIntentStart7 = PendingIntent.getBroadcast(context, 0, intentStart7,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.rl_07, pendingIntentStart7);
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


    public  List<AppWidgetBean.MondayList> getMondayListDatas() {
        return mondayList;
    }

    public  List<AppWidgetBean.ThursdayList> getThursdayListDatas() {
        return thursdayList;
    }

    public  List<AppWidgetBean.WednesdayList> getWednesdayList() {
        return wednesdayList;
    }


    public  List<AppWidgetBean.FridayList> getFridayListDatas() {
        return fridayList;
    }

    public  List<AppWidgetBean.SaturdayList> getSaturdayList() {
        return saturdayList;
    }
    public  List<AppWidgetBean.SundayList> getSundayList() {
        return sundayList;
    }
    public  List<AppWidgetBean.TuesdayList> getTuesdayList() {
        return tuesdayList;
    }

    public boolean isRun(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = "com.ad";
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                //Log.i("ActivityService isRun()",info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="+info.baseActivity.getPackageName());
                break;
            }
        }
        //Log.i("ActivityService isRun()", "com.ad 程序  ...isAppRunning......"+isAppRunning);
        return isAppRunning;
    }
}

