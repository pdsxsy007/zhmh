package io.cordova.zhqy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.cordova.zhqy.R;
import io.cordova.zhqy.activity.InfoDetailsActivity;
import io.cordova.zhqy.bean.SysMsgBean;
import io.cordova.zhqy.utils.CircleCrop;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.web.BaseWebActivity;

/**
 * Created by Administrator on 2019/4/11 0011.
 */

public class RefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    List<SysMsgBean.ObjBean> mDatas;

    public RefreshAdapter(Context context, List<SysMsgBean.ObjBean> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_to_do_my_msg, parent, false);
        return new ItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        String str= mDatas.get(position).getMessageAppName();
        if(str != null){
            itemViewHolder.mTvContent.setText(str);
        }else {
            itemViewHolder.mTvContent.setText("系统消息");
        }

        itemViewHolder.tvPresent.setText(mDatas.get(position).getMessageTitle());
        Glide.with(mContext)
                .load(R.mipmap.message_icon1)
                .transform(new CircleCrop(mContext))
                .into(itemViewHolder.msgImg);
        int messageDetailId = mDatas.get(position).getMessageDetailId();
        if(messageDetailId == 0){//未读
            itemViewHolder.mTvContent.setTextColor(Color.parseColor("#CCCCCC"));
            itemViewHolder.tvPresent.setTextColor(Color.parseColor("#CCCCCC"));
        }else {//已读
            itemViewHolder.mTvContent.setTextColor(Color.parseColor("#000000"));
            itemViewHolder.tvPresent.setTextColor(Color.parseColor("#000000"));
        }
        itemViewHolder.llMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApp.getInstance(), InfoDetailsActivity.class);

                intent.putExtra("title",mDatas.get(position).getMessageTitle().toString());

//                if (null != mDatas.get(position).getMessageUrl()){
//                    intent.putExtra("appUrl",mDatas.get(position).getMessageUrl().toString());
//                }else {
//                    intent.putExtra("appUrl2",mDatas.get(position).getMessageContent().toString());
                    intent.putExtra("appUrl2","【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）");
//                }
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        //RecyclerView的count设置为数据总条数+ 1（footerView）
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvContent;
        @BindView(R.id.ll_msg)
        LinearLayout llMsg;
        @BindView(R.id.tv_present)
        TextView tvPresent;
        @BindView(R.id.oa_img)
        ImageView msgImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            initListener(itemView);
        }

        private void initListener(View itemView) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.oa_img:
                            Toast.makeText(mContext, "图片 " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                            break;

                    }
//                    Toast.makeText(mContext, "poistion " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}