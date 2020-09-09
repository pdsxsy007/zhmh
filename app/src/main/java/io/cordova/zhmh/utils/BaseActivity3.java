package io.cordova.zhmh.utils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import io.cordova.zhmh.R;


/**
 * Activity的基类
 *
 */

public abstract class BaseActivity3 extends AppCompatActivity {
    private ProgressDialog dialog;
    public int netStateType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceId());
        ButterKnife.bind(this);
        initView();
        initHandler();
        initData();
        initListener();
        initSystemBar();
        ActivityUtils.getActivityManager().addActivity(this);
        if (!NetStateUtils.isConnect(BaseActivity3.this) ){
            ToastUtils.showToast(BaseActivity3.this,"网络连接异常!");
            //netStateType = 1;
        }
    }

    /**
     * 初始化通知栏
     */
    protected void initSystemBar() {
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init();
    }

    /**
     * 初始化监听
     */
    protected void initListener() {};

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int getResourceId();

    /**
     * 初始化控件
     */
    protected void initView() {}

    /**
     * 初始化数据
     */
    protected void initData() {}

    /**
     * 初始化handler
     */
    protected void initHandler() {}
    /**
     * 显示dialog
     */
    protected void showDialog(String s) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(s);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }



    /**
     * dialog停止
     */
    protected void stopDialog() {
        if (null != dialog ) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       stopDialog();
        ActivityUtils.getActivityManager().finishActivity(this);
        OkGo.getInstance().cancelTag(this);

    }



    public void onBack(View view){
        finish();
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);
        return ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        OkGo.getInstance().cancelTag(this);
    }
}
