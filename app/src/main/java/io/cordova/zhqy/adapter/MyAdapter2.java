package io.cordova.zhqy.adapter;

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

import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.InfoDetailsActivity;
import io.cordova.zhqy.bean.OAMsgListBean2;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.web.BaseWebActivity;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class MyAdapter2 extends CommonAdapter<OAMsgListBean2.ObjBean> {
    Context mContext;
    public MyAdapter2(Context context, int layoutId, List<OAMsgListBean2.ObjBean> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final OAMsgListBean2.ObjBean s, int position) {

        holder.setText(R.id.tv_name,s.getMessageAppName());
        holder.setTextColor(R.id.tv_name, Color.parseColor("#000000"));
        holder.setText(R.id.tv_present,s.getMessageTitle());
        holder.setTextColor(R.id.tv_present,Color.parseColor("#000000"));

        ImageView iv = holder.getConvertView().findViewById(R.id.oa_img);
        switch (position%6) {
            case 0:
                Glide.with(mContext)
                        .load(R.mipmap.message_icon2)
                        //.transform(new CircleCrop(mContext))
                        .into(iv);
                break;
            case 1:
                Glide.with(mContext)
                        .load(R.mipmap.message_icon1)
                        //.transform(new CircleCrop(mContext))
                        .into(iv);
                break;
            case 2:
                Glide.with(mContext)
                        .load(R.mipmap.message_icon2)
                        //.transform(new CircleCrop(mContext))
                        .into(iv);
                break;
            case 3:
                Glide.with(mContext)
                        .load(R.mipmap.message_icon4)
                        //.transform(new CircleCrop(mContext))
                        .into(iv);
                break;
            case 4:
                Glide.with(mContext)
                        .load(R.mipmap.message_icon3)
                        //.transform(new CircleCrop(mContext))
                        .into(iv);
                break;
            case 5:
                Glide.with(mContext)
                        .load(R.mipmap.message_icon5)
                        //.transform(new CircleCrop(mContext))
                        .into(iv);
                break;
        }
//        if(s.getPortalMessageDetailList().get(0).getMessageDetailState() == 0){//未读
//            holder.setTextColor(R.id.tv_name,Color.parseColor("#000000"));
//            holder.setTextColor(R.id.tv_present,Color.parseColor("#000000"));
//            holder.setVisible(R.id.rl_jiaobiao,true);
//        }else {//已读
//            holder.setTextColor(R.id.tv_name,Color.parseColor("#707070"));
//            holder.setTextColor(R.id.tv_present,Color.parseColor("#707070"));
//            holder.setVisible(R.id.rl_jiaobiao,false);
//            holder.setAlpha(R.id.tv_name, (float) 0.6);
//            holder.setAlpha(R.id.tv_present, (float) 0.6);
//            holder.setAlpha(R.id.oa_img, (float) 0.6);
//        }
//        holder.setOnClickListener(R.id.ll_msg, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity.class);
//                intent.putExtra("oaMsg","oaMsg");
//                intent.putExtra("appName",s.getMessageAppName());
//
//                if ("".equals(s.getMessageUrl())){
//                    intent.putExtra("appUrl",s.getMessageContent().toString());
//
//                }else if(null == s.getMessageUrl() ){
//                    intent.putExtra("appUrl",s.getMessageContent().toString());
//                    //intent.putExtra("appUrl2","【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）");
//                }else {
//                    intent.putExtra("appUrl",s.getMessageUrl().toString());
//                }
//                mContext.startActivity(intent);
//            }
//        });


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


                                mContext.startActivity(intent);

                                Intent intent2 = new Intent();
                                intent2.setAction("refreshMsg");
                                intent2.putExtra("state",s.getPortalMessageDetailList().get(0).getMessageDetailState()+"");
                                mContext.sendBroadcast(intent2);

                            }
                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);

                            }
                        });

            }
        });
//        if(s.getPortalMessageDetailList().size() != 0){
//            Intent intent2 = new Intent();
//            intent2.setAction("refreshMsg2");
//            intent2.putExtra("state",s.getPortalMessageDetailList().get(0).getMessageDetailState()+"");
//            mContext.sendBroadcast(intent2);
//        }

    }

}
