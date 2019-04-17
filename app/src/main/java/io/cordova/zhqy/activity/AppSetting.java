package io.cordova.zhqy.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.CurrencyBean;
import io.cordova.zhqy.jpushutil.NotificationsUtils;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.MyDataCleanManager;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.T;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AppSetting extends BaseActivity2 {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.msg_notice)
    Switch msgNotice;

    String dataSize = null;
    @Override
    protected int getResourceId() {
        return R.layout.app_setting_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("设置");
        startNoticeON();
        setcachesize();

        Log.e("versionName", getLocalVersionName(this)+"  -防空导弹-");

    }
    /**
     * 获取本地软件版本号名称
     */
    public  String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            Log.d("TAG", "本软件的版本号。。" + localVersion);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
    private void startNoticeON() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!NotificationsUtils.isNotificationEnabled(getApplicationContext())) {
                Log.e("llla", "走这里了");
//                未开启
                msgNotice.setChecked(false);
            }else {
                msgNotice.setChecked(true);
            }
        }
    }

    private void setcachesize() {
        try {
            dataSize = MyDataCleanManager.getTotalCacheSize(MyApp.getInstance());
            tv1.setText(dataSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("DataClean","  ==  "+dataSize);
    }

    private void isMsgNotice() {

        if (msgNotice.isChecked()){
            Log.e("msgNotice","----- "+ msgNotice.isChecked());
        }else {
            Log.e("msgNotice","----- "+ msgNotice.isChecked());
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initListener() {
        super.initListener();
        msgNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
//                    ToastUtils.showToast(MyApp.getInstance(),"选中");
//                    isMsgNotice();

                    NotificationsUtils.requestNotify(AppSetting.this);
                }else {
//                    ToastUtils.showToast(MyApp.getInstance(),"未选中");
//                    isMsgNotice();
                    NotificationsUtils.requestNotify(AppSetting.this);
                }
            }
        });

    }

    @OnClick({R.id.ll_version_information, R.id.ll_clear_cache, R.id.ll_feedback, R.id.ll_about, R.id.ll_sign_out})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_version_information:
                break;
            case R.id.ll_clear_cache:
                MyDataCleanManager.clearAllCache(this);
                try {
                    dataSize = MyDataCleanManager.getTotalCacheSize(MyApp.getInstance());
                    tv1.setText(dataSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_feedback:
                break;
            case R.id.ll_about:
                intent = new Intent(this,VersionMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_sign_out:
                netExit();
                break;
        }
    }

    private void netExit() {
        OkGo.<String>post(UrlRes.HOME2_URL+UrlRes.Exit_Out)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("netExit", response.body());
                        initRelieve();
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .addToBackStack(null)  //将当前fragment加入到返回栈中
//                                .replace(R.id.container, new HomePreFragment()).commit();
                        SPUtils.clear(getApplicationContext());
                        T.showShort(MyApp.getInstance(), "退出成功");
                        finish();
                    }
                });

    }

    CurrencyBean currencyBean;
    private void initRelieve() {
        OkGo.<String>get(UrlRes.HOME4_URL + UrlRes.Relieve_Registration_Id)
                .tag("Jpush")
                .params("userId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .params("portalEquipmentMemberEquipmentId", (String) SPUtils.get(MyApp.getInstance(), "registrationId", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("JPush", response.body());
                        currencyBean = JSON.parseObject(response.body(), CurrencyBean.class);
                        if (currencyBean.isSuccess()) {
                            //解除绑定成功
                            Log.e("JPush", currencyBean.getMsg());
                        } else {
                            //解除绑定失败
                            Log.e("JPush", currencyBean.getMsg());
                        }
                    }
                });
    }

//    class clearCache implements Runnable {
//        @Override
//        public void run() {
//            try {
//                CacheDataManager.clearAllCache(AppSetting.this);
//                Thread.sleep(3000);
//                if (CacheDataManager.getTotalCacheSize(AppSetting.this).startsWith("0")) {
//                    handler.sendEmptyMessage(0);
//                }
//            } catch (Exception e) {
//                return;
//            }
//
//        }
//
//    }
//
//
//    private Handler handler = new Handler() {
//
//        public void handleMessage(android.os.Message msg) {
//
//            switch (msg.what) {
//
//                case 0:
//
//                    Toast.makeText(AppSetting.this, "清理完成", Toast.LENGTH_SHORT).show();
//                    try {
//                        tv1.setText(CacheDataManager.getTotalCacheSize(AppSetting.this));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//            }
//        };
//    };

    @Override
    protected void onResume() {
        super.onResume();

    }
}
