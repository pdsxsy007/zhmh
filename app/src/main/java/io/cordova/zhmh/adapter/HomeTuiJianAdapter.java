package io.cordova.zhmh.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.bean.OAMsgListBean;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.web.BaseWebActivity;



public class HomeTuiJianAdapter extends CommonAdapter<String> {
    Context mContext;
    public HomeTuiJianAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final String s, int position) {

        holder.setText(R.id.name,s);

    }

}
