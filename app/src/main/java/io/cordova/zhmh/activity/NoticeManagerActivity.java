package io.cordova.zhmh.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import io.cordova.zhmh.R;
import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;


public class NoticeManagerActivity extends BaseActivity2 {




    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.msg_notice)
    ImageView msgNotice;

    private String flag = "0";

    @Override
    protected int getResourceId() {
        return R.layout.activity_notice_manager;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("通用设置");
        String isOpen = (String) SPUtils.get(MyApp.getInstance(), "isOpen", "");
        if(isOpen.equals("") || isOpen.equals("1")){
            msgNotice.setImageResource(R.mipmap.switch_open_icon);
        }else {
            msgNotice.setImageResource(R.mipmap.switch_close_icon);
        }
        msgNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag.equals("0")){
                    msgNotice.setImageResource(R.mipmap.switch_open_icon);
                    SPUtils.put(NoticeManagerActivity.this,"isOpen","1");
                    flag = "1";
                }else {
                    msgNotice.setImageResource(R.mipmap.switch_close_icon);
                    SPUtils.put(NoticeManagerActivity.this,"isOpen","0");
                    flag = "0";
                }

            }
        });
    }



}
