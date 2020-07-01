package io.cordova.zhmh.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.activity.InfoDetailsActivity;
import io.cordova.zhmh.bean.OAMsgListBean2;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class MyRefrshAdapter2 extends CommonAdapter<OAMsgListBean2.ObjBean> {

    private Context context;
    public MyRefrshAdapter2(Context context, int layoutId, List<OAMsgListBean2.ObjBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }



    @Override
    protected void convert(ViewHolder holder, final OAMsgListBean2.ObjBean s, int position) {
        String str= s.getMessageAppName();
        if(str != null){
            holder.setText(R.id.tv_name,"["+str+"]");
        }else {
            holder.setText(R.id.tv_name,"[系统消息]");
        }
        holder.setText(R.id.tv_present,s.getMessageTitle());
        ImageView imageView = holder.getConvertView().findViewById(R.id.oa_img);

        switch (position%6){
            case 0:
                Glide.with(context)
                        .load(R.mipmap.message_icon2)
                        //.transform(new CircleCrop(mContext))
                        .into(imageView);
                break;
            case 1:
                Glide.with(context)
                        .load(R.mipmap.message_icon1)
                        //.transform(new CircleCrop(mContext))
                        .into(imageView);
                break;
            case 2:
                Glide.with(context)
                        .load(R.mipmap.message_icon2)
                        //.transform(new CircleCrop(mContext))
                        .into(imageView);
                break;
            case 3:
                Glide.with(context)
                        .load(R.mipmap.message_icon4)
                        //.transform(new CircleCrop(mContext))
                        .into(imageView);
                break;
            case 4:
                Glide.with(context)
                        .load(R.mipmap.message_icon3)
                        //.transform(new CircleCrop(mContext))
                        .into(imageView);
                break;
            case 5:
                Glide.with(context)
                        .load(R.mipmap.message_icon1)
                        //.transform(new CircleCrop(mContext))
                        .into(imageView);
                break;

        }


        Log.d("s", s.getMessageId()+".");
        if(s.getPortalMessageDetailList().get(0).getMessageDetailState() == 0){//未读
            holder.setTextColor(R.id.tv_name,Color.parseColor("#000000"));
            holder.setTextColor(R.id.tv_present,Color.parseColor("#000000"));
            holder.setVisible(R.id.rl_jiaobiao,true);
        }else {//已读
            holder.setTextColor(R.id.tv_name,Color.parseColor("#707070"));
            holder.setTextColor(R.id.tv_present,Color.parseColor("#707070"));
            holder.setVisible(R.id.rl_jiaobiao,false);
            holder.setAlpha(R.id.tv_name, (float) 0.6);
            holder.setAlpha(R.id.tv_present, (float) 0.6);
            holder.setAlpha(R.id.oa_img, (float) 0.6);
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

                                intent.putExtra("title2",s.getMessageTitle().toString());
                                intent.putExtra("time",s.getMessageSendTime()+"");
                                intent.putExtra("msgsender",s.getMessageSender()+"");
                                //intent.putExtra("title2",s.getMessageAppName());
                                //null != s.getMessageUrl() ||
                                if ("".equals(s.getMessageUrl())){
                                    intent.putExtra("appUrl2",s.getMessageContent().toString());

                                }else if(null == s.getMessageUrl() ){
                                    intent.putExtra("appUrl2",s.getMessageContent().toString());
                                    //intent.putExtra("appUrl2","【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）");
                                }else {
                                    intent.putExtra("appUrl",s.getMessageUrl().toString());
                                }

                               /* if (!"".equals(s.getMessageUrl())){
                                    intent.putExtra("appUrl",s.getMessageUrl().toString());
                               }else {
                                    String s1 = s.getMessageContent().toString();
                                    intent.putExtra("appUrl2",s.getMessageContent().toString());
                                   //intent.putExtra("appUrl2","【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）");
                               }*/


                                context.startActivity(intent);

                                Intent intent2 = new Intent();
                                intent2.setAction("refreshMsg");
                                intent2.putExtra("state",s.getPortalMessageDetailList().get(0).getMessageDetailState()+"");
                                context.sendBroadcast(intent2);

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
