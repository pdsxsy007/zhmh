package io.cordova.zhmh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.bean.ItemNewsBean2;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.web.BaseWebActivity4;
import io.cordova.zhmh.web.BaseWebCloseActivity;


/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class NewsAdapter extends CommonAdapter<ItemNewsBean2> {
    Context mContext;
    String mtitle;
    public NewsAdapter(Context context, int layoutId, List<ItemNewsBean2> datas, String title) {
        super(context, layoutId, datas);
        mContext = context;
        mtitle = title;
    }

    @Override
    protected void convert(ViewHolder holder, final ItemNewsBean2 s, int position) {

        holder.setText(R.id.tv_title,s.getNewsTitle());
        holder.setText(R.id.tv_left,mtitle);
        holder.setText(R.id.tv_right,s.getNewsDate());
        TextView tv_left = holder.getConvertView().findViewById(R.id.tv_left);
        String newsDate = s.getNewsDate();
        if(null == newsDate){
            holder.setVisible(R.id.rl_right,false);

        }else {
            // tv_left.setMaxEms(17);
        }
        holder.setOnClickListener(R.id.ll_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = null;
                String isOpen = (String) SPUtils.get(MyApp.getInstance(), "isOpen", "");
                if(isOpen.equals("") || isOpen.equals("1")){
                    intent = new Intent(MyApp.getInstance(), BaseWebCloseActivity.class);
                    intent.putExtra("appUrl",s.getNewsHref());
                    intent.putExtra("appName",s.getNewsTitle());
                }else {
                    intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                    intent.putExtra("appUrl",s.getNewsHref());
                    intent.putExtra("appName",s.getNewsTitle());
                }

                mContext.startActivity(intent);
            }
        });




    }

}
