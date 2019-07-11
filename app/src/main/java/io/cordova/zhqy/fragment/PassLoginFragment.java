package io.cordova.zhqy.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.FindPwdActivity;
import io.cordova.zhqy.activity.LoginActivity2;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.GetUserIdBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.SystemBarTintUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.fingerUtil.MD5Util;

import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2019/5/22 0022.
 */

public class PassLoginFragment extends BaseFragment {

    EditText etPhoneNum;

    EditText etPassword;


    Button btnLogin;
    Button btnLogin3;
    TextView tv_find_pwd;

    WebView webView;
    private Object W;
    private String s1;
    private String s2;

    @Override
    protected int getLayoutResource() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView(View view) {
        SystemBarTintUtils.initSystemBarColor(getActivity(), R.color.colorPrimary);
        tv_find_pwd = view.findViewById(R.id.tv_find_pwd);
        webView = view.findViewById(R.id.webView);
        btnLogin3 = view.findViewById(R.id.btn_login_3);
        btnLogin = view.findViewById(R.id.btn_login);
        etPassword = view.findViewById(R.id.et_password);
        etPhoneNum = view.findViewById(R.id.et_phoneNum);

        tv_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FindPwdActivity.class);
                startActivity(intent);
                FinishActivity.addActivity(getActivity());
            }
        });
    }

    GetUserIdBean getUserIdBean;

    @OnClick({R.id.l_1, R.id.btn_login,R.id.btn_login_3,R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.l_1:
                break;
            case R.id.btn_login:

                if (StringUtils.getEditTextData(etPhoneNum).isEmpty() && StringUtils.getEditTextData(etPassword).isEmpty()){
                    T.showShort(MyApp.getInstance(),"请输入用户名或密码");
                    return;
                }
                if(StringUtils.getEditTextData(etPhoneNum).isEmpty()){
                    T.showShort(MyApp.getInstance(),"请输入用户名");
                    return;
                }

                if(StringUtils.getEditTextData(etPassword).isEmpty()){
                    T.showShort(MyApp.getInstance(),"请输入密码");
                    return;
                }

                netWorkLogin();

                break;
            case R.id.btn_login_3:
                //   netExit();
                break;
            case R.id.iv_close:
                getActivity().finish();
                break;
        }
    }
    LoginBean loginBean;
    String tgt;
    private void netWorkLogin() {
        try {
//            URLEncoder.encode( ,"UTF-8")
            s1 = URLEncoder.encode(AesEncryptUtile.encrypt(StringUtils.getEditTextData(etPhoneNum),key),"UTF-8");
            s2 =  URLEncoder.encode(AesEncryptUtile.encrypt(StringUtils.getEditTextData(etPassword),key),"UTF-8");
            SPUtils.put(MyApp.getInstance(),"phone",etPhoneNum.getText().toString().trim()+"");
            SPUtils.put(MyApp.getInstance(),"pwd",etPassword.getText().toString().trim()+"");
            Log.e("login","s1 = "+ s1 + "  ,s2  = " + s2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        OkGo.<String>get(UrlRes.HOME2_URL +"/cas/casApiLoginController")
                .params("openid","123456")
                .params("username",s1)
                .params("password",s2)
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
                                SPUtils.put(getActivity(),"TGC",tgt);
                                SPUtils.put(getActivity(),"username",s1);
                                SPUtils.put(getActivity(),"password",s2);
                                getActivity().finish();
                                Intent intent = new Intent();
                                intent.putExtra("refreshService","dongtai");
                                intent.setAction("refresh2");
                                getActivity().sendBroadcast(intent);


                                //本地存储账号用户指纹登录时显示账号信息
                                StringBuffer stringBuffer = new StringBuffer();
                                SPUtil.getInstance().putString(Constants.SP_ACCOUNT, etPhoneNum.getText().toString());
                                stringBuffer.append( etPhoneNum.getText().toString());
                                stringBuffer.append(etPassword.getText().toString());
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
                    Intent intent = new Intent(getActivity(),LoginActivity2.class);
                    startActivity(intent);
                    getActivity().finish();

                    return true;
                }
            }
//            else if (url.contains("myoa.zzuli.edu.cn")) {
//                if (!url.contains("fromnewcas=Y")){
//                    url = url + "&fromnewcas=Y";
//
//                    view.loadUrl(url);
//                    Log.i("url", "== " + url);
//                    return true;
//                }
//                return false;
//            }
//            Log.i("url2", "== " + url);
            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getActivity(),LoginActivity2.class);
                    startActivity(intent);
                    getActivity().finish();

                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://kys.zzuli.edu.cn","CASTGC="+tgt,getActivity());


        }

    };
}
