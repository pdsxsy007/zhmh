package io.cordova.zhmh.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;
import java.util.Calendar;

import butterknife.BindView;
import io.cordova.zhmh.MainActivity;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.LoginBean;
import io.cordova.zhmh.bean.WXBindBean;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.BaseActivity;
import io.cordova.zhmh.utils.CookieUtils;
import io.cordova.zhmh.utils.FinishActivity;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.T;
import io.cordova.zhmh.utils.ToastUtils;

import static io.cordova.zhmh.UrlRes.casAuthenticationWeChat2ControllerUrl;
import static io.cordova.zhmh.utils.AesEncryptUtile.key;


/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class BindThreeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_sex)
    TextView tv_sex;

    @BindView(R.id.et_account)
    EditText et_account;

    @BindView(R.id.et_pwd)
    EditText et_pwd;

    @BindView(R.id.rl_next)
    RelativeLayout rl_next;

    @BindView(R.id.webView)
    WebView webView;

    String name;
    String gender;
    String iconur;
    String password;
    String username;
    String weChatOpenid;//加密后的微信openid
    String openid;
    @Override
    protected int getResourceId() {
        return R.layout.activity_bind_three;
    }

    @Override
    protected void initView() {
        super.initView();


        name = getIntent().getStringExtra("name");

        gender = getIntent().getStringExtra("gender");

        iconur = getIntent().getStringExtra("iconur");

        weChatOpenid = getIntent().getStringExtra("weChatOpenid");
        password = getIntent().getStringExtra("password");
        username = getIntent().getStringExtra("username");
        openid = getIntent().getStringExtra("openid");

        Glide.with(this)
                .load(iconur)
                .asBitmap()
                .error(R.mipmap.tabbar_user_pre)
                .into(iv_user_head);
        tv_name.setText(name);
        tv_sex.setText(gender);
        rl_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_next:
                String trim = et_account.getText().toString().trim();
                String trim2 = et_pwd.getText().toString().trim();
                if(trim.equals("")){
                    ToastUtils.showToast(this,"请输入账号!");
                    return;
                }
                if(trim2.equals("")){
                    ToastUtils.showToast(this,"请输入密码!");
                    return;
                }

                checkAccount(trim,trim2);

                break;


        }
    }
    LoginBean loginBean;
    String tgt;
    private void checkAccount(String trim, String trim2) {
        try {
            final String s1 = URLEncoder.encode(AesEncryptUtile.encrypt(trim,key),"UTF-8");
            final String s2 =  URLEncoder.encode(AesEncryptUtile.encrypt(trim2,key),"UTF-8");
            SPUtils.put(MyApp.getInstance(),"phone",trim+"");
            SPUtils.put(MyApp.getInstance(),"pwd",trim2+"");

            String imei =  AesEncryptUtile.encrypt((String) SPUtils.get(this, "imei", ""), key);
            OkGo.<String>get(UrlRes.HOME2_URL +"/cas/casApiLoginController")
                    .tag(this)
                    .params("openid","123456")
                    .params("username",s1)
                    .params("password",s2)
                    .params("equipmentId",imei)
                    .params("type","1")
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            //Log.e("result1",response.body());

                            loginBean = JSON.parseObject(response.body(),LoginBean.class);
                            if (loginBean.isSuccess() ) {

                                try {
                                    CookieManager cookieManager =  CookieManager.getInstance();
                                    cookieManager.removeAllCookie();
                                    tgt = AesEncryptUtile.decrypt(loginBean.getAttributes().getTgt(),key);

                                    if(!tgt.equals("")){
                                        String username0 = loginBean.getAttributes().getUsername();
                                        bindWxInfo(s1,s2,username0,tgt);

                                    }
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

    private void bindWxInfo(final String s1, final String s2, final String username, final String tgt) {
        OkGo.<String>get(UrlRes.HOME2_URL +casAuthenticationWeChat2ControllerUrl)
                .tag(this)
                .params("openid","123456")
                .params("username",s1)
                .params("password",s2)
                .params("weChatOpenid",openid)
                .params("serviceid","wx781c42f3a5b36a7a")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //Log.e("result2",response.body());

                        WXBindBean wxBindBean = JSON.parseObject(response.body(),WXBindBean.class);
                        if (wxBindBean.isSuccess() ) {

                            try {
                                String userName = AesEncryptUtile.decrypt(username,key);
                                webView.setWebViewClient(mWebViewClient);
                                //webView.loadUrl("http://iapp.zzuli.edu.cn/portal/login/appLogin");
                                webView.loadUrl(UrlRes.HOME_URL+"/portal/login/appLogin");
                                String userId  = AesEncryptUtile.encrypt(userName+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
                                SPUtils.put(MyApp.getInstance(),"time",Calendar.getInstance().getTimeInMillis()+"");
                                SPUtils.put(MyApp.getInstance(),"userId",userId);
                                SPUtils.put(MyApp.getInstance(),"personName",userName);
                                SPUtils.put(getApplicationContext(),"TGC",tgt);
                                SPUtils.put(getApplicationContext(),"username",s1);
                                SPUtils.put(getApplicationContext(),"password",s2);


                                Intent intent0 = new Intent(BindThreeActivity.this,MainActivity.class);
                                startActivity(intent0);
                                FinishActivity.clearActivity();
                                finish();


                                Intent intent = new Intent();
                                intent.putExtra("refreshService","dongtai");
                                intent.setAction("refresh2");
                                sendBroadcast(intent);

                                Intent intent2 = new Intent();
                                intent2.setAction("refresh3");
                                sendBroadcast(intent2);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                        }else {
                            T.showShort(MyApp.getInstance(),wxBindBean.getMsg());
                        }
                    }
                });

    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            Log.i("userAgent4",  view.getSettings().getUserAgentString());

        }

        //        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie(UrlRes.HOME2_URL,"CASTGC="+tgt,getApplication());

        }

    };

}
