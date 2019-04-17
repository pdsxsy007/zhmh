package io.cordova.zhqy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhqy.R;
import io.cordova.zhqy.bean.OAMsgListBean;
import io.cordova.zhqy.utils.CircleCrop;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class MyAdapter extends CommonAdapter<OAMsgListBean.ObjBean> {

    public MyAdapter(Context context, int layoutId, List<OAMsgListBean.ObjBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OAMsgListBean.ObjBean s, int position) {
        Glide.with(mContext)
                .load(R.mipmap.message_icon1)
                .transform(new CircleCrop(mContext))
                .into((ImageView) holder.getView(R.id.oa_img));
////                holder.setText(R.id.tv_name,oaMsgListBean.getObj().get(position).getTitle());
//                holder.setText(R.id.tv_content,oaMsgListBean.getObj().get(position).getYwlx());
        holder.setText(R.id.tv_name,s.getTitle());
        holder.setTextColor(R.id.tv_name,R.color.black);
        holder.setText(R.id.tv_present,s.getYwlx());
        holder.setTextColor(R.id.tv_present,R.color.black);
    }
}
