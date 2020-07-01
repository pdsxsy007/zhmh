package io.cordova.zhmh.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.bean.SysMsgBean;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Created by admin on 2020/1/8.
 */

public class UpdateServiceSys1 extends RemoteViewsService {

    @Override
    public void onStart(Intent intent, int startId){
        super.onCreate();

    }
    @Override
    public IBinder onBind(Intent intent) {

        return super.onBind(intent);
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ListRemoteViewsFactory implements RemoteViewsFactory{

        private final Context mContext;
        List<SysMsgBean.ObjBean> obj;


        public ListRemoteViewsFactory(Context context, Intent intent){
            mContext = context;
            String content = (String) SPUtils.get(mContext,"systemData","");

            SysMsgBean sysMsgBean = JsonUtil.parseJson(content,SysMsgBean.class);

            obj = sysMsgBean.getObj();
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
            obj.clear();
        }

        @Override
        public int getCount() {
            return obj.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            String content = (String) SPUtils.get(mContext,"systemData","");
            SysMsgBean sysMsgBean = JsonUtil.parseJson(content,SysMsgBean.class);
            List<SysMsgBean.ObjBean> obj = sysMsgBean.getObj();
            if(position<0 || position>=obj.size())
                return null;
            SysMsgBean.ObjBean objBean = obj.get(position);
            final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item2);
            Intent intent = new Intent();
            //Intent intent = new Intent(MyApp.getInstance(), InfoDetailsActivity.class);

            intent.putExtra("title2",objBean.getMessageTitle());
            intent.putExtra("time",objBean.getMessageSendTime()+"");
            intent.putExtra("msgsender",objBean.getMessageSender()+"");
            //intent.putExtra("title2",s.getMessageAppName());
            //null != s.getMessageUrl() ||
            if ("".equals(objBean.getMessageUrl())){
                intent.putExtra("appUrl2",objBean.getMessageContent().toString());

            }else if(null == objBean.getMessageUrl() ){
                intent.putExtra("appUrl2",objBean.getMessageContent().toString());
                //intent.putExtra("appUrl2","【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）");
            }else {
                intent.putExtra("appUrl",objBean.getMessageUrl().toString());
            }
            rv.setOnClickFillInIntent(R.id.widget_list_item_layout, intent);

            String rolecodes = (String) SPUtils.get(getApplicationContext(),"rolecodes","");

            rv.setTextViewText(R.id.tv_content, objBean.getMessageTitle());

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
