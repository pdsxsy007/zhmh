package io.cordova.zhqy.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIClickCallback;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.PreLoginListener;
import cn.jiguang.verifysdk.api.VerifyListener;
import io.cordova.zhqy.R;
import io.cordova.zhqy.utils.BaseActivity;

/**
 * Created by Administrator on 2019/6/20 0020.
 */

public class NewLoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NewLoginActivity";
    private TextView tvLog;
    private Button btnGetToken;
    private EditText etPhone;
    private Button btnValidate;
    private Button btn_login;
    private Button btnPreLogin;
    private boolean autoFinish = true;
    private static final int CENTER_ID = 1000;

    @BindView(R.id.btn_go)
    Button btn_go;

    @BindView(R.id.tv_phone)
    TextView tv_phone;



    @Override
    protected int getResourceId() {
        return R.layout.activity_new_login;
    }

    @Override
    protected void initView() {
        super.initView();
        etPhone = (EditText) findViewById(R.id.et_phone);
        btnValidate = (Button) findViewById(R.id.btn_validate);

        btnValidate.setOnClickListener(this);
        tvLog = (TextView) findViewById(R.id.tv_log);
        tvLog.setOnClickListener(this);
        btnGetToken = (Button) findViewById(R.id.btn_get_token);
        btnGetToken.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btnPreLogin = (Button) findViewById(R.id.btn_pre_login);
        btnPreLogin.setOnClickListener(this);
        findViewById(R.id.cb_auto_finish).setOnClickListener(this);

        preLogin();


        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JVerifyUIConfig uiConfig = new JVerifyUIConfig.Builder()
                        .setAuthBGImgPath("main_bg")
                        .setNavColor(0xff1d4481)
                        .setNavText("登录")
                        .setNavTextColor(0xffffffff)
                        .setNavReturnImgPath("umcsdk_return_bg")
                        .setLogoWidth(70)
                        .setLogoHeight(70)
                        .setLogoHidden(false)
                        .setNumberColor(0xff333333)
                        .setLogBtnText("本机号码一键登录")
                        .setLogBtnTextColor(0xffffffff)
                        .setLogBtnImgPath("umcsdk_login_btn_bg")
                        .setAppPrivacyOne("应用自定义服务条款一","https://www.jiguang.cn/about")
                        .setAppPrivacyTwo("应用自定义服务条款二","https://www.jiguang.cn/about")
                        .setAppPrivacyColor(0xff666666,0xff0085d0)
                        .setUncheckedImgPath("umcsdk_uncheck_image")
                        .setCheckedImgPath("umcsdk_check_image")
                        .setSloganTextColor(0xff999999)
                        .setLogoOffsetY(50)
                        .setLogoImgPath("logo_cm")
                        .setNumFieldOffsetY(190)
                        .setSloganOffsetY(220)
                        .setLogBtnOffsetY(254)

                        .setPrivacyOffsetY(35).build();
                JVerificationInterface.setCustomUIWithConfig(uiConfig);
//        JVerificationInterface.setLoginAuthLogo("umcsdk_mobile_logo","umcsdk_mobile_logo","umcsdk_mobile_logo");
                JVerificationInterface.loginAuth(NewLoginActivity.this, autoFinish, new VerifyListener() {
                    @Override
                    public void onResult(final int code, final String content, final String operator) {
                        tvLog.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"[" + code + "]message=" +  content + ", operator=" + operator);
                                tvLog.setText("[" + code + "]message=" +  content + ", operator=" + operator);
                                dismissLoadingDialog();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_validate:
                verifyNumber();
                break;
            case R.id.btn_get_token:
                getToken();
                break;
            case R.id.btn_pre_login:
                preLogin();
                break;
            case R.id.btn_login:
                loginAuth();
                break;
            case R.id.cb_auto_finish:
                autoFinish = ((CheckBox)view).isChecked();
        }
    }

    private String token;

    private void getToken() {
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if(!verifyEnable){
            tvLog.setText("[2016],msg = 当前网络环境不支持认证");
            return;
        }
        tvLog.setText(null);
        showLoadingDialog();
        JVerificationInterface.getToken(this,5000, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        if (code == 2000){
                            token = content;
                            tvLog.setText("[" + code + "]token=" +  content + ", operator=" + operator);
                        } else {
                            tvLog.setText("[" + code + "]message=" +  content);
                        }
                        dismissLoadingDialog();

                    }
                });
            }
        });
    }

    private void verifyNumber() {
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if(!verifyEnable){
            tvLog.setText("[2016],msg = 当前网络环境不支持认证");
            return;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        tvLog.setText(null);
        showLoadingDialog();
        JVerificationInterface.verifyNumber(this, token, phone, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        tvLog.setText("[" + code + "]message=" +  content + ", operator=" + operator);
                        dismissLoadingDialog();
                    }
                });
            }
        });
    }

    private void preLogin(){
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if(!verifyEnable){
            tv_phone.setText("获取手机号失败");
            return;
        }
        showLoadingDialog();
        JVerificationInterface.preLogin(this, 5000,new PreLoginListener() {
            @Override
            public void onResult(final int code, final String content) {
                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        /*Log.d(TAG,"[" + code + "]message=" +  content );
                        tvLog.setText("[" + code + "]message=" +  content );*/
                        dismissLoadingDialog();
                    }
                });
            }
        });
    }

    private void loginAuth(){
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if(!verifyEnable){
            tvLog.setText("[2016],msg = 当前网络环境不支持认证");
            return;
        }
        showLoadingDialog();


        JVerifyUIConfig uiConfig = new JVerifyUIConfig.Builder()
                .setAuthBGImgPath("main_bg")
                .setNavColor(0xff1d4481)
                .setNavText("登录")
                .setNavTextColor(0xffffffff)
                .setNavReturnImgPath("umcsdk_return_bg")
                .setLogoWidth(70)
                .setLogoHeight(70)
                .setLogoHidden(false)
                .setNumberColor(0xff333333)
                .setLogBtnText("本机号码一键登录")
                .setLogBtnTextColor(0xffffffff)
                .setLogBtnImgPath("umcsdk_login_btn_bg")
                .setAppPrivacyOne("应用自定义服务条款一","https://www.jiguang.cn/about")
                .setAppPrivacyTwo("应用自定义服务条款二","https://www.jiguang.cn/about")
                .setAppPrivacyColor(0xff666666,0xff0085d0)
                .setUncheckedImgPath("umcsdk_uncheck_image")
                .setCheckedImgPath("umcsdk_check_image")
                .setSloganTextColor(0xff999999)
                .setLogoOffsetY(50)
                .setLogoImgPath("logo_cm")
                .setNumFieldOffsetY(190)
                .setSloganOffsetY(220)
                .setLogBtnOffsetY(254)

                .setPrivacyOffsetY(35).build();
        JVerificationInterface.setCustomUIWithConfig(uiConfig);
//        JVerificationInterface.setLoginAuthLogo("umcsdk_mobile_logo","umcsdk_mobile_logo","umcsdk_mobile_logo");
        JVerificationInterface.loginAuth(this, autoFinish, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        //content   返回的token
                        dismissLoadingDialog();
                    }
                });
            }
        });
    }

    private void submit() {
        // validate
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

    private AlertDialog alertDialog;

    public void showLoadingDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private int dp2Pix(Context context, float dp) {
        try {
            float density = context.getResources().getDisplayMetrics().density;
            return (int)(dp * density + 0.5F);
        } catch (Exception e) {
            return (int)dp;
        }
    }
}
