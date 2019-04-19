package io.cordova.zhqy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.InfoDetailsActivity;
import io.cordova.zhqy.activity.SystemMsgActivity;
import io.cordova.zhqy.bean.SysMsgBean;
import io.cordova.zhqy.utils.CircleCrop;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class MyRefrshAdapter extends CommonAdapter<SysMsgBean.ObjBean> {

    private Context context;
    public MyRefrshAdapter(Context context, int layoutId, List<SysMsgBean.ObjBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }



    @Override
    protected void convert(ViewHolder holder, final SysMsgBean.ObjBean s, int position) {
        String str= s.getMessageAppName();
        if(str != null){
            holder.setText(R.id.tv_name,str);
        }else {
            holder.setText(R.id.tv_name,"系统消息");
        }
        holder.setText(R.id.tv_present,s.getMessageTitle());
        ImageView imageView = holder.getConvertView().findViewById(R.id.oa_img);
        Glide.with(mContext)
                .load(R.mipmap.message_icon1)
                .transform(new CircleCrop(mContext))
                .into(imageView);

        Log.d("s", s.getMessageDetailId()+".");
        if(s.getMessageDetailState() == 0){//未读
            holder.setTextColor(R.id.tv_name,Color.parseColor("#000000"));
            holder.setTextColor(R.id.tv_present,Color.parseColor("#000000"));
        }else {//已读
            holder.setTextColor(R.id.tv_name,Color.parseColor("#e7e6e6"));
            holder.setTextColor(R.id.tv_present,Color.parseColor("#e7e6e6"));
        }


        holder.setOnClickListener(R.id.ll_msg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkGo.<String>post(UrlRes.HOME_URL + UrlRes.searchMessageById)
                        .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                        .params("messageDetailId", s.getMessageId())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.e("SysMsg",response.body());
                                Intent intent = new Intent(MyApp.getInstance(), InfoDetailsActivity.class);

                                intent.putExtra("title",s.getMessageTitle().toString());
                                intent.putExtra("title2",s.getMessageAppName());

                                if (null != s.getMessageUrl()){
                                    intent.putExtra("appUrl",s.getMessageUrl().toString());
                               }else {
                                   intent.putExtra("appUrl2",s.getMessageContent().toString());
                                   //intent.putExtra("appUrl2","【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）");
                               }
                                context.startActivity(intent);
                            }
                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);

                            }
                        });

            }
        });
    }
}
