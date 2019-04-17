package io.cordova.zhqy.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import io.cordova.zhqy.R;
import io.cordova.zhqy.utils.BaseActivity2;

/**
 * 版本信息
 */

public class VersionMsgActivity extends BaseActivity2 {



    @BindView(R.id.tv_version_number)
    TextView tvVersionNumber;

    @Override
    protected int getResourceId() {
        return R.layout.activity_version_msg;
    }

    @Override
    protected void initView() {
        super.initView();

//        getLocalVersion(this);
        getLocalVersionName(this);

    }



    /**
     * 获取本地软件版本号
     */
    public  int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "本软件的版本号。。" + localVersion);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
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
            tvVersionNumber.setText("Version ： "+localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
