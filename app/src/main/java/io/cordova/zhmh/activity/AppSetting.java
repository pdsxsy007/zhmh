package io.cordova.zhmh.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


import butterknife.BindView;
import butterknife.OnClick;

import io.cordova.zhmh.MainActivity;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.Constants;
import io.cordova.zhmh.bean.CurrencyBean;
import io.cordova.zhmh.bean.UpdateBean;
import io.cordova.zhmh.fingerprint.FingerprintHelper;
import io.cordova.zhmh.jpushutil.NotificationsUtils;

import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.DensityUtil;
import io.cordova.zhmh.utils.FinishActivity;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.MyDataCleanManager;
import io.cordova.zhmh.utils.SPUtil;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.ScreenSizeUtils;
import io.cordova.zhmh.utils.T;
import io.cordova.zhmh.utils.ToastUtils;
import io.cordova.zhmh.utils.fingerUtil.FingerprintUtil;
import io.cordova.zhmh.web.BaseWebActivity4;
import io.cordova.zhmh.widget.MyDialog;
import io.cordova.zhmh.widget.finger.CommonTipDialog;
import io.cordova.zhmh.widget.finger.FingerprintVerifyDialog;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;

/**
 * Created by Administrator on 2019/2/18 0018.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class AppSetting extends BaseActivity2 implements FingerprintHelper.SimpleAuthenticationCallback, View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.msg_notice)
    ImageView msgNotice;

    @BindView(R.id.iv_fingerprint_login_switch)
    ImageView iv_fingerprint_login_switch;

    @BindView(R.id.tv_version)
    TextView tv_version;

    @BindView(R.id.ll_clear_cache)
    LinearLayout ll_clear_cache;

    @BindView(R.id.ll_finger)
    LinearLayout ll_finger;

    @BindView(R.id.ll_shengwu)
    LinearLayout ll_shengwu;
    @BindView(R.id.ll_notice)
    LinearLayout ll_notice;


    @BindView(R.id.tv_clear)
            TextView tv_clear;

    String dataSize = null;
    String localVersionName;
    private int flag = 0;
    private boolean isOpen;
    private FingerprintHelper helper;
    private FingerprintVerifyDialog fingerprintVerifyDialog;
    private CommonTipDialog fingerprintVerifyErrorTipDialog;
    private CommonTipDialog closeFingerprintTipDialog;
    private int type = 0;
    @Override
    protected int getResourceId() {
        return R.layout.app_setting_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper = FingerprintHelper.getInstance();
            helper.init(getApplicationContext());
            helper.setCallback(this);
            if (helper.checkFingerprintAvailable(this) != -1) {
                //设备支持指纹登录
                iv_fingerprint_login_switch.setEnabled(true);
            }else {
                ToastUtils.showToast(this,"设备不支持指纹登录");
            }
        }else {
            ll_finger.setVisibility(View.GONE);
        }


        tvTitle.setText("设置");
        //startNoticeON();
        setcachesize();
        checkVersion2();
        //Log.e("versionName", getLocalVersionName(this)+"  -防空导弹-");

        localVersionName = getLocalVersionName(this);
        tv_version.setText(localVersionName);
        isOpen = SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN);
        setSwitchStatus();
        //setGuideView();

        ll_shengwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppSetting.this,ShengWuActivity.class));
            }
        });
    }


    private void setGuideView() {

        // 使用图片
        Lighter.with(this)
                .setBackgroundColor(0xB9000000)
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {


                    }

                    @Override
                    public void onDismiss() {

                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        .setHighlightedViewId(R.id.ll_clear_cache)
                        .setTipLayoutId(R.layout.fragment_home_gl1)
                        //.setLighterShape(new RectShape(80, 80, 50))
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(0, 10, 0, 20))
                        .build()).show();
    }

    private void checkVersion2() {
        OkGo.<String>get(UrlRes.HOME_URL+UrlRes.getNewVersionInfo)
                .params("system","android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("ss",response.body());
                        UpdateBean updateBean = JSON.parseObject(response.body(),UpdateBean.class);

                        portalVersionNumber = updateBean.getObj().getportalVersionNumber();

                    }
                });
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
                //Log.e("llla", "走这里了");
//                未开启
               // msgNotice.setChecked(false);
                //iv_fingerprint_login_switch.setImageResource(isOpen ? R.mipmap.switch_open_icon : R.mipmap.switch_close_icon);
                msgNotice.setImageResource(R.mipmap.switch_close_icon);
                flag = 0;
            }else {
               // msgNotice.setChecked(true);
                msgNotice.setImageResource(R.mipmap.switch_open_icon);
                flag = 1;
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
        //Log.e("DataClean","  ==  "+dataSize);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initListener() {
        super.initListener();
        msgNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == 0){
                    //msgNotice.setChecked(true);
                    msgNotice.setImageResource(R.mipmap.switch_open_icon);
                }else {
                    //msgNotice.setChecked(false);
                    msgNotice.setImageResource(R.mipmap.switch_close_icon);
                }
                NotificationsUtils.requestNotify(AppSetting.this);
            }
        });


        iv_fingerprint_login_switch.setOnClickListener(this);

    }

    @OnClick({R.id.ll_version_information, R.id.ll_clear_cache, R.id.ll_feedback, R.id.ll_about, R.id.ll_sign_out,R.id.ll_device,R.id.ll_phone_change,R.id.ll_mail_change,R.id.ll_notice})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_version_information:
                checkVersion();
                break;
            case R.id.ll_clear_cache:
                MyDataCleanManager.clearAllCache(this);
                try {
                    dataSize = MyDataCleanManager.getTotalCacheSize(MyApp.getInstance());
                    tv1.setText(dataSize);
                    ToastUtils.showToast(this,"已清除");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ll_feedback:
                intent = new Intent(this,UpdatePwdInfoActivity.class);
                startActivity(intent);
                FinishActivity.addActivity(this);
                break;
            case R.id.ll_about:
                intent = new Intent(this,VersionMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_sign_out:
                logOut();
                break;
            case R.id.ll_device:
                intent = new Intent(this,DeviceManagerActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_phone_change:
                intent = new Intent(this, BaseWebActivity4.class);
                intent.putExtra("appUrl",UrlRes.HOME_URL + UrlRes.changePhoneUrl);
                startActivity(intent);
                break;
            case R.id.ll_mail_change:
                intent = new Intent(this, BaseWebActivity4.class);
                intent.putExtra("appUrl",UrlRes.HOME_URL + UrlRes.changeEmailUrl);
                startActivity(intent);
                break;
            case R.id.ll_notice:
                intent = new Intent(this,NoticeManagerActivity.class);
                startActivity(intent);
                break;
        }
    }
    String portalVersionNumber;
    private void checkVersion() {
        OkGo.<String>get(UrlRes.HOME_URL+UrlRes.getNewVersionInfo)
                .params("system","android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("ss",response.body());
                        UpdateBean updateBean = JSON.parseObject(response.body(),UpdateBean.class);

                        portalVersionNumber = updateBean.getObj().getportalVersionNumber();
                        int portalVersionUpdate = updateBean.getObj().getportalVersionUpdate();
                        String portalVersionDownloadAdress = updateBean.getObj().getportalVersionDownloadAdress();
                        if(localVersionName.equals(portalVersionNumber)){
                            ToastUtils.showToast(AppSetting.this,"当前已是最新版本!");
                        }else {

                            String[] split = portalVersionNumber.split("\\.");
                            String[] splitLocal = localVersionName.split("\\.");
                            if(split.length == 3 && splitLocal.length == 3) {
                                int sp0 = Integer.parseInt(split[0]);//服务器版本号
                                int sp1 = Integer.parseInt(split[1]);
                                int sp2 = Integer.parseInt(split[2]);
                                int sl0 = Integer.parseInt(splitLocal[0]);//本地版本号
                                int sl1 = Integer.parseInt(splitLocal[1]);
                                int sl2 = Integer.parseInt(splitLocal[2]);

                                if (sp0 == sl0) {//主版本号相等
                                    //判断第二位
                                    if (sp1 == sl1) {//服务器第二位版本号等于于本地第二位版本号 不执行更新操作

                                        if (sp2 <= sl2) {//服务器第三位版本号等于于本地第三位版本号 不执行更新操作

                                        } else {
                                            m_Dialog.show();
                                            Intent intent = new Intent(AppSetting.this,InfoDetailsActivity2.class);
                                            intent.putExtra("appUrl","https://imtt.dd.qq.com/16891/apk/F7C4943F9B8A9B3F3B8B61FD9FA5AA0B.apk");
                                            intent.putExtra("title2","下载地址");
                                            startActivity(intent);
                                        }


                                    } else if (sp1 < sl1) {//服务器第二位版本号小于本地第二位版本号 不执行更新操作

                                    } else {//服务器第二位版本号大于本地第二位版本号 不执行更新操作
                                        m_Dialog.show();
                                        Intent intent = new Intent(AppSetting.this,InfoDetailsActivity2.class);
                                        intent.putExtra("appUrl","https://imtt.dd.qq.com/16891/apk/F7C4943F9B8A9B3F3B8B61FD9FA5AA0B.apk");
                                        intent.putExtra("title2","下载地址");
                                        startActivity(intent);
                                    }

                                } else if (sp0 < sl0) {//服务器主版本号小于本地主版本号 不执行更新操作

                                } else {//服务器主版本号大于本地版本号 执行更新操作
                                    m_Dialog.show();
                                    Intent intent = new Intent(AppSetting.this,InfoDetailsActivity2.class);
                                    intent.putExtra("appUrl","https://imtt.dd.qq.com/16891/apk/F7C4943F9B8A9B3F3B8B61FD9FA5AA0B.apk");
                                    intent.putExtra("title2","下载地址");
                                    startActivity(intent);
                                }
                            }


                        }
                    }
                });
    }
    private String s1;


    private MyDialog m_Dialog;
    private void logOut() {
        m_Dialog = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_logout, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        RelativeLayout rl_sure1 = view.findViewById(R.id.rl_sure1);

        int width = ScreenSizeUtils.getWidth(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(this,24),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        m_Dialog.setContentView(view, layoutParams);
        m_Dialog.show();
        m_Dialog.setCanceledOnTouchOutside(true);
        rl_sure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
            }
        });
        rl_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
                netExit();
            }
        });
    }

    private void netExit() {

        OkGo.<String>post(UrlRes.HOME2_URL+UrlRes.Exit_Out)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("netExit", response.body());
                        initRelieve();
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .addToBackStack(null)  //将当前fragment加入到返回栈中
//                                .replace(R.id.container, new HomePreFragment()).commit();SPUtils.clear(getApplicationContext());
                        String update = (String) SPUtils.get(MyApp.getInstance(), "update", "");
                        String home01 = (String) SPUtils.get(MyApp.getInstance(), "home01", "");
                        String home02 = (String) SPUtils.get(MyApp.getInstance(), "home02", "");
                        String home03 = (String) SPUtils.get(MyApp.getInstance(), "home03", "");
                        String home04 = (String) SPUtils.get(MyApp.getInstance(), "home04", "");
                        String home05 = (String) SPUtils.get(MyApp.getInstance(), "home05", "");
                        String home06 = (String) SPUtils.get(MyApp.getInstance(), "home06", "");

                        //SPUtils.clear(getApplicationContext());
                        SPUtils.put(getApplicationContext(),"username","");
                        SPUtils.put(getApplicationContext(),"TGC","");
                        SPUtils.put(getApplicationContext(),"userId","");
                        SPUtils.put(getApplicationContext(),"rolecodes","");
                        SPUtils.put(getApplicationContext(),"count","0");
                        SPUtils.put(getApplicationContext(),"bitmap","");
                        SPUtils.put(getApplicationContext(),"bitmap2","");
                        SPUtils.put(getApplicationContext(),"bitmapnewsd","");
                        SPUtils.put(getApplicationContext(),"usernameeidt","");
                        SPUtils.put(getApplicationContext(),"appcontent","");
                        String ss = portalVersionNumber;
                        if(!update.equals("")){
                            SPUtils.put(MyApp.getInstance(),"update",portalVersionNumber);
                        }
                        if(home01.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home01","1");
                        }
                        if(home02.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home02","1");
                        }
                        if(home03.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home03","1");
                        }
                        if(home04.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home04","1");
                        }
                        if(home05.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home05","1");
                        }
                        if(home06.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home06","1");
                        }
                        T.showShort(MyApp.getInstance(), "退出成功");
                        //closeFingerprintLogin();
                        isOpen = false;
                        SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false);
                        setSwitchStatus();
                        if(helper != null){
                            helper.closeAuthenticate();
                        }



                        Intent intent = new Intent();
                        intent.putExtra("refreshService","dongtai");
                        intent.setAction("refresh");
                        sendBroadcast(intent);

                        Intent intent2 = new Intent();
                        intent2.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                        sendBroadcast(intent2);


                        CookieManager.getInstance().removeAllCookies(null);
                        CookieManager.getInstance().flush();
                        Intent intent1 = new Intent(AppSetting.this,MainActivity.class);
                        intent1.putExtra("splash","splash");
                        startActivity(intent1);
                        //finish();

                        FinishActivity.clearActivity();

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
                        //Log.e("JPush", response.body());
                        currencyBean = JSON.parseObject(response.body(), CurrencyBean.class);
                        if (currencyBean.isSuccess()) {
                            //解除绑定成功
                            //Log.e("JPush", currencyBean.getMsg());
                        } else {
                            //解除绑定失败
                            //Log.e("JPush", currencyBean.getMsg());
                        }
                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        startNoticeON();
    }

    private void dealOnOff(boolean isOpen) {
        if (isOpen) {
            type = 0;
            showCloseFingerprintTipDialog();
        } else {
            openFingerprintLogin();
        }
    }

    private void showCloseFingerprintTipDialog() {
        if (closeFingerprintTipDialog == null) {
            closeFingerprintTipDialog = new CommonTipDialog(this);
        }
        closeFingerprintTipDialog.setContentText("确定关闭指纹登录?");
        closeFingerprintTipDialog.setSingleButton(false);
        closeFingerprintTipDialog.setOnDialogButtonsClickListener(new CommonTipDialog.OnDialogButtonsClickListener() {
            @Override
            public void onCancelClick(View v) {

            }

            @Override
            public void onConfirmClick(View v) {
                closeFingerprintLogin();
            }
        });
        closeFingerprintTipDialog.show();
    }


    /**
     * @description 开启指纹登录功能
     * @author HaganWu
     * @date 2019/1/29-10:20
     */
    private void openFingerprintLogin() {
        //Log.e("hagan", "openFingerprintLogin");

        helper.generateKey();
        if (fingerprintVerifyDialog == null) {
            fingerprintVerifyDialog = new FingerprintVerifyDialog(this);
        }
        fingerprintVerifyDialog.setContentText("请验证指纹");
        fingerprintVerifyDialog.setOnCancelButtonClickListener(new FingerprintVerifyDialog.OnDialogCancelButtonClickListener() {
            @Override
            public void onCancelClick(View v) {
                helper.stopAuthenticate();
            }
        });
        fingerprintVerifyDialog.show();
        helper.setPurpose(KeyProperties.PURPOSE_ENCRYPT);
        helper.authenticate();
    }

    @Override
    public void onAuthenticationSucceeded(String value) {
        SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, true);
        if(type == 0){
            if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
                fingerprintVerifyDialog.dismiss();
                Toast.makeText(this, "指纹登录已开启", Toast.LENGTH_SHORT).show();
                isOpen = true;
                setSwitchStatus();
                saveLocalFingerprintInfo();
            }
        }else {
            if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
                fingerprintVerifyDialog.dismiss();
                Toast.makeText(this, "指纹登录已关闭", Toast.LENGTH_SHORT).show();
                isOpen = false;
                SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false);
                setSwitchStatus();
                helper.closeAuthenticate();
            }
        }

    }

    @Override
    public void onAuthenticationFail() {
        showFingerprintVerifyErrorInfo("指纹不匹配");
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.dismiss();
        }
        showTipDialog(errorCode, errString.toString());
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        showFingerprintVerifyErrorInfo(helpString.toString());
    }

    private void setSwitchStatus() {
        iv_fingerprint_login_switch.setImageResource(isOpen ? R.mipmap.switch_open_icon : R.mipmap.switch_close_icon);
    }

    /**
     * @description 关闭指纹登录功能
     * @author HaganWu
     * @date 2019/1/24-14:41
     */
    private void closeFingerprintLogin() {
        type = 1;
        openFingerprintLogin();
       /* isOpen = false;
        SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false);
        setSwitchStatus();
        helper.closeAuthenticate();*/
    }




    private void saveLocalFingerprintInfo() {
        SPUtil.getInstance().putString(Constants.SP_LOCAL_FINGERPRINT_INFO, FingerprintUtil.getFingerprintInfoString(getApplicationContext()));
    }

    private void showFingerprintVerifyErrorInfo(String info) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.setContentText(info);
        }
    }

    private void showTipDialog(int errorCode, CharSequence errString) {
        if (fingerprintVerifyErrorTipDialog == null) {
            fingerprintVerifyErrorTipDialog = new CommonTipDialog(this);
        }
        //fingerprintVerifyErrorTipDialog.setContentText("errorCode:" + errorCode + "," + errString);
        fingerprintVerifyErrorTipDialog.setContentText(errString+"");
        fingerprintVerifyErrorTipDialog.setSingleButton(true);
        fingerprintVerifyErrorTipDialog.setOnSingleConfirmButtonClickListener(new CommonTipDialog.OnDialogSingleConfirmButtonClickListener() {
            @Override
            public void onConfirmClick(View v) {
                helper.stopAuthenticate();
            }
        });
        fingerprintVerifyErrorTipDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fingerprint_login_switch:
                dealOnOff(isOpen);
                break;
        }
    }
}
