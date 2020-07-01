package io.cordova.zhmh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.bean.LogInTypeBean;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class BrowserAdapter extends CommonAdapter<LogInTypeBean> {

    private Context context;
    public BrowserAdapter(Context context, int layoutId, List<LogInTypeBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }



    @Override
    protected void convert(ViewHolder holder, final LogInTypeBean s, int position) {
        holder.setText(R.id.tv_app_name,s.getName());
        ImageView iv = holder.getConvertView().findViewById(R.id.iv_app_icon);
        Glide.with(mContext).load(s.getImageId()).asBitmap().into(iv);
    }
}
