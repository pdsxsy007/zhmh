package io.cordova.zhqy.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;

import butterknife.BindView;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.AddFaceBean;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.bean.VerCodeBean;
import io.cordova.zhqy.fingerprint.FingerprintHelper;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.JsonUtil;
import io.cordova.zhqy.utils.LQRPhotoSelectUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.utils.fingerUtil.FingerprintUtil;
import io.cordova.zhqy.utils.fingerUtil.MD5Util;
import io.cordova.zhqy.widget.finger.CommonTipDialog;
import io.cordova.zhqy.widget.finger.FingerprintVerifyDialog;


import static io.cordova.zhqy.UrlRes.verificationUrl;
import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2019/6/19 0019.
 * 人脸识别验证码校验页面
 */

public class CodeBindActivity extends BaseActivity2 implements View.OnClickListener {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.webView)
    WebView webView;

    String userId;
    String phone;
    String username;
    String password;
    @Override
    protected int getResourceId() {
        return R.layout.activity_codebind;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("安全验证");

        userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");

        phone = getIntent().getStringExtra("phone");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        layout_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_phone.setText("本次刷脸服务需要进行短信验证,已向("+phone+")发送验证码");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_back:
                finish();
                break;
            case R.id.btn_login:
                String content = et_content.getText().toString().trim();
                if(content.equals("")){
                    ToastUtils.showToast(CodeBindActivity.this,"请输入验证码!");
                    return;
                }

                checkCode(content);
                break;

        }
    }

    private void checkCode(String content) {
        try {
            //final String username = URLEncoder.encode(AesEncryptUtile.encrypt(content, key), "UTF-8");

            final String type0 = URLEncoder.encode(AesEncryptUtile.encrypt("7", key), "UTF-8");
            String contact = URLEncoder.encode(AesEncryptUtile.encrypt(phone, key), "UTF-8");
            final String vcode = URLEncoder.encode(AesEncryptUtile.encrypt(content, key), "UTF-8");
            String userName = AesEncryptUtile.decrypt(username,key) ;
            String userid = AesEncryptUtile.encrypt(userName + "_" + Calendar.getInstance().getTimeInMillis(), key);
            OkGo.<String>get(UrlRes.HOME2_URL +verificationUrl)
                    .params("openid","123456")
                    .params("memberId",userid)
                    .params("type",type0)
                    .params("verificationCode",vcode)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("校验验证吗",response.body());

                            VerCodeBean verCodeBean = JsonUtil.parseJson(response.body(),VerCodeBean.class);
                            boolean success = verCodeBean.getSuccess();
                            if(success){

                                netWorkLogin2(username,password);

                            }else {
                                ToastUtils.showToast(CodeBindActivity.this,verCodeBean.getMsg());
                            }

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });

        } catch (Exception e) {

        }
    }
    String s1;
    String s2;
    LoginBean loginBean;
    String tgt;
    private void netWorkLogin2(final String uname, final String pwd) {
        try {
//            URLEncoder.encode( ,"UTF-8")

            s1 = URLEncoder.encode(uname,"UTF-8");
            s2 = URLEncoder.encode(pwd,"UTF-8");
            SPUtils.put(MyApp.getInstance(),"phone",AesEncryptUtile.decrypt(uname)+"");
            SPUtils.put(MyApp.getInstance(),"pwd",AesEncryptUtile.decrypt(pwd)+"");
            String imei =  AesEncryptUtile.encrypt((String) SPUtils.get(this, "imei", ""), key);
            OkGo.<String>get(UrlRes.HOME2_URL +"/cas/casApiLoginController")
                    .params("openid","123456")
                    .params("username",uname)
                    .params("password",pwd)
                    .params("type","9")
                    .params("equipmentId",imei)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("result1",response.body());

                            loginBean = JSON.parseObject(response.body(),LoginBean.class);
                            if (loginBean.isSuccess() ) {

                                try {
                                    CookieManager cookieManager =  CookieManager.getInstance();
                                    cookieManager.removeAllCookie();
                                    tgt = AesEncryptUtile.decrypt(loginBean.getAttributes().getTgt(),key);

                                    String userName = AesEncryptUtile.decrypt(loginBean.getAttributes().getUsername(),key) ;

                                    webView.setWebViewClient(mWebViewClient);
                                    webView.loadUrl("http://iapp.zzuli.edu.cn/portal/login/appLogin");
                                    String userId  = AesEncryptUtile.encrypt(userName+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
                                    SPUtils.put(MyApp.getInstance(),"time",Calendar.getInstance().getTimeInMillis()+"");
                                    SPUtils.put(MyApp.getInstance(),"userId",userId);
                                    SPUtils.put(MyApp.getInstance(),"personName",userName);
                                    SPUtils.put(getApplicationContext(),"TGC",tgt);
                                    SPUtils.put(MyApp.getInstance(),"username",AesEncryptUtile.decrypt(uname)+"");
                                    SPUtils.put(MyApp.getInstance(),"password",AesEncryptUtile.decrypt(pwd)+"");


                                    FinishActivity.clearActivity();
                                    finish();
                                    Intent intent = new Intent();
                                    intent.putExtra("refreshService","dongtai");
                                    intent.setAction("refresh2");
                                    sendBroadcast(intent);


                                    //本地存储账号用户指纹登录时显示账号信息
                                    StringBuffer stringBuffer = new StringBuffer();
                                    SPUtil.getInstance().putString(Constants.SP_ACCOUNT, AesEncryptUtile.decrypt(username,key));
                                    stringBuffer.append(AesEncryptUtile.decrypt(username,key));
                                    stringBuffer.append(AesEncryptUtile.decrypt(password,key));
                                    SPUtil.getInstance().putString(Constants.SP_A_P, MD5Util.md5Password(stringBuffer.toString()));
                                    Log.e("login","tgt = "+ tgt + "  ,userName  = " + userName);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                T.showShort(MyApp.getInstance(),loginBean.getMsg());
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("userAgent4",  view.getSettings().getUserAgentString());

        }

        //        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }


            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://kys.zzuli.edu.cn","CASTGC="+tgt,getApplication());

        }

    };
}
