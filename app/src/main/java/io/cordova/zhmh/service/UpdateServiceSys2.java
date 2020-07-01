package io.cordova.zhmh.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.bean.OAMsgListBean;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Created by admin on 2020/1/8.
 */

public class UpdateServiceSys2 extends RemoteViewsService {

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
        List<OAMsgListBean.ObjBean> obj;


        public ListRemoteViewsFactory(Context context, Intent intent){
            mContext = context;
            String content = (String) SPUtils.get(mContext,"dbData","");

            OAMsgListBean sysMsgBean = JsonUtil.parseJson(content,OAMsgListBean.class);

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
            String content = (String) SPUtils.get(mContext,"dbData","");
            OAMsgListBean sysMsgBean = JsonUtil.parseJson(content,OAMsgListBean.class);
            List<OAMsgListBean.ObjBean> obj = sysMsgBean.getObj();
            if(position<0 || position>=obj.size())
                return null;
            OAMsgListBean.ObjBean objBean = obj.get(position);
            final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item2);
            Intent intent = new Intent();
            intent.putExtra("appUrl",objBean.getTodourl());
            rv.setOnClickFillInIntent(R.id.widget_list_item_layout, intent);

            String rolecodes = (String) SPUtils.get(getApplicationContext(),"rolecodes","");

            rv.setTextViewText(R.id.tv_content, objBean.getTitle());

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
