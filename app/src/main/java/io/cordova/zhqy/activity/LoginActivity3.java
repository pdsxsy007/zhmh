package io.cordova.zhqy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.security.keystore.KeyProperties;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.GetUserIdBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.fingerprint.FingerprintHelper;
import io.cordova.zhqy.fingerprint.LoginPageOperationListener;
import io.cordova.zhqy.fragment.FingerprintLoginFragment;
import io.cordova.zhqy.fragment.PassLoginFragment;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.SystemBarTintUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.fingerUtil.FingerprintUtil;
import io.cordova.zhqy.utils.fingerUtil.MD5Util;
import io.cordova.zhqy.widget.XCRoundImageView;
import io.cordova.zhqy.widget.finger.CommonTipDialog;
import io.cordova.zhqy.widget.finger.FingerprintVerifyDialog;
import io.cordova.zhqy.widget.finger.PromptButton;
import io.cordova.zhqy.widget.finger.PromptButtonListener;
import io.cordova.zhqy.widget.finger.PromptDialog;

import static io.cordova.zhqy.activity.SplashActivity.PASSWORD_LOGIN_FLAG;
import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2018/11/15 0015.
 */

public class LoginActivity3 extends BaseActivity implements View.OnClickListener, FingerprintHelper.SimpleAuthenticationCallback {

    private LoginPageOperationListener loginPageOperationListener;
    private TextView tv_account_info;
    private PromptDialog promptDialog;
    private final int LOGOUT_ID = 0x0001;
    private final int SMS_LOGIN_ID = 0x0002;
    private final int PASSWORD_ID = 0x0003;
    private FingerprintHelper helper;
    private FingerprintVerifyDialog fingerprintVerifyDialog;
    private CommonTipDialog errorTipDialog;
    private CommonTipDialog fingerprintChangeTipDialog;
    private ImageView iv_fingerprint_login;
    private TextView tv_title;
    private XCRoundImageView iv_user_head;


    @Override
    protected int getResourceId() {
        return R.layout.login_activity2;
    }


    @Override
    protected void initView() {
        super.initView();
        tv_account_info = (TextView) findViewById(R.id.tv_account_info);
        iv_fingerprint_login = (ImageView) findViewById(R.id.iv_fingerprint_login);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_user_head = (XCRoundImageView) findViewById(R.id.iv_user_head);
        tv_title.setText("指纹登录");
        iv_fingerprint_login.setEnabled(true);
        String account = SPUtil.getInstance().getString(Constants.SP_ACCOUNT);
       /* if (!TextUtils.isEmpty(account)) {
            if (account.length() == 11) {
                account = account.substring(0, 3) + "****" + account.substring(7, account.length());
            }
            tv_account_info.setText(String.format(getString(R.string.fingerprint_account), account));
        }*/
        String phone = (String) SPUtils.get(MyApp.getInstance(), "phone", "");
        String url = (String) SPUtils.get(MyApp.getInstance(), "logoUrl", "");
        Glide.with(this)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.tabbar_user_pre)
                .into(iv_user_head);
        tv_account_info.setText(phone);
        findViewById(R.id.tv_fingerprint_login_more).setOnClickListener(this);
        iv_fingerprint_login.setOnClickListener(this);
        helper = FingerprintHelper.getInstance();
        helper.init(this);
        helper.setCallback(this);
        tv_account_info.postDelayed(new Runnable() {
            @Override
            public void run() {
                openFingerprintLogin();
            }
        }, 500);
       /* fingerprintLoginFragment = new FingerprintLoginFragment();
        fingerprintLoginFragment.setLoginPageOperationListener(this);
        if (SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN)) {
            home_container.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);
            switchFragment(fingerprintLoginFragment).commit();
        }else {
            ll_login.setVisibility(View.VISIBLE);
            home_container.setVisibility(View.GONE);
        }*/
        setLoginPageOperationListener(new LoginPageOperationListener() {
            @Override
            public void onFinish() {

            }

            @Override
            public void changePage(int flag) {
                if (flag == PASSWORD_LOGIN_FLAG){
                    Intent intent = new Intent(LoginActivity3.this,LoginActivity4.class);
                    startActivity(intent);
                    finish();
                    helper.stopAuthenticate();
                }

            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fingerprint_login_more:
                moreClick();
                break;
            case R.id.iv_fingerprint_login:
                openFingerprintLogin();
                break;
        }
    }
    private void openFingerprintLogin() {
        Log.e("hagan", "FingerprintLoginFragment->openFingerprintLogin");

        //验证指纹库信息是否发生变化
        if (FingerprintUtil.isLocalFingerprintInfoChange(this)) {
            //指纹库信息发生变化
            showFingerprintChangeTipDialog();
            return;
        }

        if (fingerprintVerifyDialog == null) {
            fingerprintVerifyDialog = new FingerprintVerifyDialog(this);
        }
        fingerprintVerifyDialog.setContentText("请验证指纹");
        fingerprintVerifyDialog.setOnCancelButtonClickListener(new FingerprintVerifyDialog.OnDialogCancelButtonClickListener() {
            @Override
            public void onCancelClick(View v) {
                helper.stopAuthenticate();
                //fingerprintVerifyDialog.dismiss();
            }
        });
        fingerprintVerifyDialog.show();
        helper.setPurpose(KeyProperties.PURPOSE_DECRYPT);
        helper.authenticate();
    }

    private void showFingerprintChangeTipDialog() {
        if (fingerprintChangeTipDialog == null) {
            fingerprintChangeTipDialog = new CommonTipDialog(this);
        }
        fingerprintChangeTipDialog.setContentText(getResources().getString(R.string.fingerprintChangeTip));
        fingerprintChangeTipDialog.setSingleButton(true);
        fingerprintChangeTipDialog.setOnSingleConfirmButtonClickListener(new CommonTipDialog.OnDialogSingleConfirmButtonClickListener() {
            @Override
            public void onConfirmClick(View v) {
                iv_fingerprint_login.setEnabled(false);
                SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false);
                helper.closeAuthenticate();
            }
        });
        fingerprintChangeTipDialog.show();
    }


    /**
     * @description 更多点击(切换到密码登录)
     * @author HaganWu
     * @date 2019/1/24-11:00
     */
    private void moreClick() {
        showMoreDialog();
    }

    private void showMoreDialog() {
        if (promptDialog == null) {
            promptDialog = new PromptDialog(this);
        }
        PromptButton cancel = new PromptButton("取消", null);
        cancel.setTextColor(Color.parseColor("#0076ff"));
        PromptButtonListener promptButtonListener = new PromptButtonListener() {
            @Override
            public void onClick(PromptButton button) {
                switch (button.getId()) {
                    case LOGOUT_ID:
                        Toast.makeText(LoginActivity3.this, "切换/注册账号", Toast.LENGTH_SHORT).show();
                        break;
                    case SMS_LOGIN_ID:
                        Toast.makeText(LoginActivity3.this, "短信安全登录", Toast.LENGTH_SHORT).show();
                        break;
                    case PASSWORD_ID:
                        if (loginPageOperationListener != null) {
                            loginPageOperationListener.changePage(SplashActivity.PASSWORD_LOGIN_FLAG);
                        }
                        break;
                }
            }
        };
        promptDialog.showAlertSheet("", true, cancel,
                //new PromptButton(LOGOUT_ID, "切换/注册账号", promptButtonListener),
                //new PromptButton(SMS_LOGIN_ID, "短信安全登录", promptButtonListener),
                new PromptButton(PASSWORD_ID, "密码登录", promptButtonListener));
    }

    public void setLoginPageOperationListener(LoginPageOperationListener loginPageOperationListener) {
        this.loginPageOperationListener = loginPageOperationListener;
    }

    @Override
    public void onAuthenticationSucceeded(String value) {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationSucceeded-> value:" + value);
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.dismiss();
        }
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        if (loginPageOperationListener != null) {
            loginPageOperationListener.onFinish();
        }
    }


    @Override
    public void onAuthenticationFail() {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationFail");
        showFingerprintVerifyErrorInfo("指纹不匹配");
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationSucceeded-> errString:" + errString.toString());
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.dismiss();
        }
        //showTipDialog(errorCode, errString.toString());
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Log.e("hagan", "FingerprintLoginFragment->onAuthenticationHelp-> helpString:" + helpString);
        showFingerprintVerifyErrorInfo(helpString.toString());
    }

    private void showTipDialog(int errorCode, CharSequence errString) {
        if (errorTipDialog == null) {
            errorTipDialog = new CommonTipDialog(this);
        }
        errorTipDialog.setContentText("errorCode:" + errorCode + "," + errString);
        errorTipDialog.setSingleButton(true);
        errorTipDialog.setOnSingleConfirmButtonClickListener(new CommonTipDialog.OnDialogSingleConfirmButtonClickListener() {
            @Override
            public void onConfirmClick(View v) {
                helper.stopAuthenticate();
            }
        });
        errorTipDialog.show();
    }

    private void showFingerprintVerifyErrorInfo(String info) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.setContentText(info);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
