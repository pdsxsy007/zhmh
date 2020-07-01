package io.cordova.zhmh.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.bean.AppWidgetBean;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Created by admin on 2020/1/8.
 */

public class UpdateService7 extends RemoteViewsService {

    @Override
    public void onStart(Intent intent, int startId){
        super.onCreate();
        Log.e("onStart","onStart");
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("onBind","onBind");
        return super.onBind(intent);
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("onGetViewFactory","onGetViewFactory");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsFactory{

        private final Context mContext;
        private List<AppWidgetBean.SundayList> fridayLists;


        public ListRemoteViewsFactory(Context context, Intent intent){
            mContext = context;
            String content = (String) SPUtils.get(mContext,"appcontent","");
            AppWidgetBean appWidgetBean = JsonUtil.parseJson(content,AppWidgetBean.class);
            fridayLists = appWidgetBean.getAttributes().getSundayList();
            Log.e("ListRemoteViewsFactory",fridayLists.get(0).getUndergraduateJuniorCourseClassName());
            if(Looper.myLooper() == null){
                Looper.prepare();
            }
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onDestroy() {
            fridayLists.clear();
        }

        @Override
        public int getCount() {
            return fridayLists.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            String content = (String) SPUtils.get(mContext,"appcontent","");
            AppWidgetBean appWidgetBean = JsonUtil.parseJson(content,AppWidgetBean.class);
            fridayLists = appWidgetBean.getAttributes().getSundayList();
            if(position<0 || position>=fridayLists.size())
                return null;
            AppWidgetBean.SundayList fridayList = fridayLists.get(position);
            final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
            Intent intent = new Intent();

            rv.setOnClickFillInIntent(R.id.widget_list_item_layout, intent);

            String rolecodes = (String) SPUtils.get(getApplicationContext(),"rolecodes","");

            if(rolecodes.contains("student")){
                if(fridayList.getCourseName() != null){
                    rv.setTextViewText(R.id.widget_list_item_tv, fridayList.getCourseName());
                }

                if(fridayList.getCourseSection() != null){
                    rv.setTextViewText(R.id.jieci, fridayList.getCourseSection());
                }

                if(fridayList.getCourseAdressCode() != null){
                    rv.setTextViewText(R.id.place, fridayList.getCourseAdressCode());
                }

                if(fridayList.getTeacherName() != null){
                    rv.setTextViewText(R.id.tv_teacherName, fridayList.getTeacherName());
                }
            }else {
                if(fridayList.getUndergraduateJuniorCourseClassName() != null){
                    rv.setTextViewText(R.id.widget_list_item_tv, fridayList.getUndergraduateJuniorCourseName());
                }

                if(fridayList.getUndergraduateJuniorCourseSection() != null){
                    rv.setTextViewText(R.id.jieci, fridayList.getUndergraduateJuniorCourseSection());
                }

                String undergraduateJuniorCourseAdressName = fridayList.getUndergraduateJuniorCourseAdressName();
                String undergraduateJuniorCourseClassRoom = fridayList.getUndergraduateJuniorCourseClassRoom();
                if(undergraduateJuniorCourseAdressName != null && undergraduateJuniorCourseClassRoom != null){
                    rv.setTextViewText(R.id.place, undergraduateJuniorCourseAdressName+"-"+undergraduateJuniorCourseClassRoom);
                }

            }


            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
