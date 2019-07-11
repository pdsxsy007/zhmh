package io.cordova.zhqy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhqy.R;
import io.cordova.zhqy.bean.LogInTypeBean;
import io.cordova.zhqy.bean.OAMsgListBean;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.ScreenSizeUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.web.BaseWebActivity;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class LoginTypeAdapter extends CommonAdapter<LogInTypeBean> {
    Context mContext;
    boolean allowedScan = false;

    public LoginTypeAdapter(Context context, int layoutId, List<LogInTypeBean> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final LogInTypeBean s, int position) {

        holder.setText(R.id.tv_name,s.getName());
        ImageView iv = holder.getConvertView().findViewById(R.id.iv_bg);
        LinearLayout ll_item = holder.getConvertView().findViewById(R.id.ll_go);
        TextView tv = holder.getConvertView().findViewById(R.id.tv_name);
        Glide.with(mContext).load(s.getImageId()).asBitmap().into(iv);
        ViewGroup.LayoutParams layoutParams = holder.getView(R.id.ll_go).getLayoutParams();
        layoutParams.width = (ScreenSizeUtils.getWidth(mContext) - DensityUtil.dp2px( 70)) / 3;//
        holder.getView(R.id.ll_go).setLayoutParams(layoutParams);
        switch (position){
            case 0:
                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast(mContext,"点击了");
                    }
                });
                break;
            case 1:
                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 2:
                iv.setAlpha(0.5f);
                tv.setAlpha(0.5f);
                ll_item.setClickable(false);
                break;
            case 3:
                iv.setAlpha(0.5f);
                tv.setAlpha(0.5f);
                ll_item.setClickable(false);
                break;
            case 4:
                iv.setAlpha(0.5f);
                tv.setAlpha(0.5f);
                ll_item.setClickable(false);
                break;
        }


    }



}
