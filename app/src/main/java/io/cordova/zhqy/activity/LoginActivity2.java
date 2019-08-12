package io.cordova.zhqy.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.VerifyListener;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.Main3Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.FaceBean;
import io.cordova.zhqy.bean.FaceBean2;
import io.cordova.zhqy.bean.GetUserIdBean;
import io.cordova.zhqy.bean.LogInTypeBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.JsonUtil;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.utils.fingerUtil.MD5Util;
import io.cordova.zhqy.widget.InputMethodLayout;
import io.reactivex.functions.Consumer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static io.cordova.zhqy.UrlRes.HOME2_URL;
import static io.cordova.zhqy.UrlRes.HOME_URL;
import static io.cordova.zhqy.activity.FaceActivity.bitmapToBase64;
import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2018/11/15 0015.
 */

public class LoginActivity2 extends BaseActivity {
    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.et_password)
    EditText etPassword;
    String userName, passWord;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_login_3)
    Button btnLogin3;
    @BindView(R.id.tv_find_pwd)
    TextView tv_find_pwd;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.tv_body)
    TextView tv_body;

    @BindView(R.id.tv_other)
    TextView tv_other;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.root)
    ScrollView scrollView;

    @BindView(R.id.rl)
    InputMethodLayout rl;
     @BindView(R.id.gantan)
    ImageView  gantanImg;
    int scrollViewHeight;

    private LinearLayoutManager mLinearLayoutManager;

    private Object W;
    private String s1;
    private String s2;
    private android.support.v7.app.AlertDialog mAlertDialog;

    @Override
    protected int getResourceId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initSystemBar() {
        super.initSystemBar();


        tv_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity2.this,FindPwdActivity.class);
                startActivity(intent);
                FinishActivity.addActivity(LoginActivity2.this);
            }
        });

        tv_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity2.this,NewLoginActivity.class);
                startActivity(intent);
                FinishActivity.addActivity(LoginActivity2.this);
            }
        });


        rl.setOnkeyboarddStateListener(new InputMethodLayout.onKeyboardsChangeListener() {
            @Override
            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case InputMethodLayout.KEYBOARD_STATE_HIDE:

                        break;
                    case InputMethodLayout.KEYBOARD_STATE_SHOW:

                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollViewHeight = scrollView.getMeasuredWidth();

                                scrollView.scrollTo(0, 500);
                            }
                        });

                        break;

                    default:
                        break;
                }
            }
        });
    gantanImg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showDialogs();
        }
    });
    }
    String update;
    boolean allowedScan = false;
    @Override
    protected void initView() {
        super.initView();
        SPUtils.put(this,"bitmap","");
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false);
        recyclerview.setLayoutManager(mLinearLayoutManager);


        List<LogInTypeBean> list = new ArrayList<>();
        list.clear();
        list.add(new LogInTypeBean("刷脸登录",R.mipmap.rl));
        list.add(new LogInTypeBean("一键登录",R.mipmap.yj));
        list.add(new LogInTypeBean("微信登录",R.mipmap.wx));
        list.add(new LogInTypeBean("qq登录",R.mipmap.qq));
        list.add(new LogInTypeBean("微博登录",R.mipmap.xl));
        recyclerview.setAdapter(new LoginTypeAdapter(this,R.layout.list_item_logintype,list));
        update = getIntent().getStringExtra("update");
        registerBoradcastReceiver();
    }

    /**请求权限*/
    private void setPermission() {
        //同时请求多个权限
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.e("用户已经同意该权限", permission.name + " is granted.");
//                            Intent intent = new Intent(MyApp.getInstance(), QRScanActivity.class);
//                            startActivity(intent);
                            allowedScan = true;
                            //   Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Log.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");
                            allowedScan = false;
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   Log.d(TAG, permission.name + " is denied.");
                            Log.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
                            allowedScan = true;
                        }
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
                String uname = StringUtils.getEditTextData(etPhoneNum);
                String pwd = StringUtils.getEditTextData(etPassword);
                netWorkLogin(uname,pwd);

                break;
            case R.id.btn_login_3:
                //   netExit();
                break;
            case R.id.iv_close:
                if(update != null){
                    Intent intent = new Intent(LoginActivity2.this,Main2Activity.class);
                    startActivity(intent);
                    finish();
                }else {
                    this.finish();
                }

                break;
        }
    }
    LoginBean loginBean;
    String tgt;

    /**推送设置弹窗*/
    private void showDialogs() {
        if (mAlertDialog == null) {
            mAlertDialog = new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("新生帐号为考生号，密码为身份证12-17位（帐号详情请参考应用中心用户手册）\n" +
                            "在校生/教职工帐号为学号，默认密码为身份证12-17位")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            //NotificationsUtils.gotoSet(getApplicationContext());

                        }
                    }).create();
        }
        mAlertDialog.show();

    }
    private void netWorkLogin(String uname, String pwd) {
        try {
//            URLEncoder.encode( ,"UTF-8")
            s1 = URLEncoder.encode(AesEncryptUtile.encrypt(uname,key),"UTF-8");
            s2 =  URLEncoder.encode(AesEncryptUtile.encrypt(pwd,key),"UTF-8");
            SPUtils.put(MyApp.getInstance(),"phone",etPhoneNum.getText().toString().trim()+"");
            SPUtils.put(MyApp.getInstance(),"pwd",etPassword.getText().toString().trim()+"");
            Log.e("login","s1 = "+ s1 + "  ,s2  = " + s2);

        } catch (Exception e) {
            e.printStackTrace();
        }

       ;
        try {
            String imei =  AesEncryptUtile.encrypt((String) SPUtils.get(this, "imei", ""), key);
            OkGo.<String>get(HOME2_URL +"/cas/casApiLoginController")
                    .params("openid","123456")
                    .params("username",s1)
                    .params("password",s2)
                    .params("equipmentId",imei)
                    .params("type","1")
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
                                    //webView.loadUrl("http://iapp.zzuli.edu.cn/portal/login/appLogin");
                                    webView.loadUrl(HOME_URL+"/portal/login/appLogin");
                                    String userId  = AesEncryptUtile.encrypt(userName+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
                                    SPUtils.put(MyApp.getInstance(),"time",Calendar.getInstance().getTimeInMillis()+"");
                                    SPUtils.put(MyApp.getInstance(),"userId",userId);
                                    SPUtils.put(MyApp.getInstance(),"personName",userName);
                                    SPUtils.put(getApplicationContext(),"TGC",tgt);
                                    SPUtils.put(getApplicationContext(),"username",s1);
                                    SPUtils.put(getApplicationContext(),"password",s2);



                                    finish();
                                    Intent intent = new Intent();
                                    intent.putExtra("refreshService","dongtai");
                                    intent.setAction("refresh2");
                                    sendBroadcast(intent);

                                    Intent intent2 = new Intent();
                                    intent2.setAction("refresh3");
                                    sendBroadcast(intent2);

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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
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


           /* if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"userId",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }
*/
            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"userId",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }*/

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            //CookieUtils.syncCookie("http://kys.zzuli.edu.cn","CASTGC="+tgt,getApplication());
            CookieUtils.syncCookie(HOME2_URL,"CASTGC="+tgt,getApplication());

        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(update != null){
                Intent intent = new Intent(LoginActivity2.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }else {
                this.finish();
            }
        }

        return super.onKeyDown(keyCode, event);

    }

    /**
     * 登陆  获取授权
     *
     * @param zq
     */
    private void login(SHARE_MEDIA zq) {
        //UMShareAPI.get(LoginActivity2.this).getPlatformInfo(LoginActivity2.this, zq, authListener);
        //UMShareAPI.get(LoginActivity2.this).deleteOauth(LoginActivity2.this, zq, authListener);
        UMShareAPI.get(LoginActivity2.this).getPlatformInfo(LoginActivity2.this, zq, authListener);
    }

    //获取授权的回掉
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @SuppressLint("WrongConstant")
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {



            if (platform == SHARE_MEDIA.WEIXIN) {
                String name = data.get("screen_name");
                String profile_image_url = data.get("profile_image_url");
                String gender = data.get("gender");


            } else if (platform == SHARE_MEDIA.QQ) {
                String qqName = data.get("screen_name");
                String profile_image_url = data.get("profile_image_url");
                String gender = data.get("gender");
            }else if(platform == SHARE_MEDIA.SINA){
                Toast.makeText(LoginActivity2.this, "新浪授权成功：", Toast.LENGTH_LONG).show();
            }


        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @SuppressLint("WrongConstant")
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity2.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @SuppressLint("WrongConstant")
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity2.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    //集成qq 微博  需要重写这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if(data!= null){
            String content = data.getStringExtra("content");
            Log.e("content",content);
        }
    }



    private void netWorkLogin2(final String uname, final String pwd, String s) {
        try {
           /* s1 = URLEncoder.encode(uname,"UTF-8");
            s2 =  URLEncoder.encode(pwd,"UTF-8");*/
            SPUtils.put(MyApp.getInstance(),"phone",AesEncryptUtile.decrypt(uname)+"");
            SPUtils.put(MyApp.getInstance(),"pwd",AesEncryptUtile.decrypt(pwd)+"");
            String imei =  AesEncryptUtile.encrypt((String) SPUtils.get(this, "imei", ""), key);
            OkGo.<String>get(HOME2_URL +"/cas/casApiLoginController")
                    .params("openid","123456")
                    .params("username",uname)
                    .params("password",pwd)
                    .params("type",s)
                    .params("equipmentId",imei)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("result1",response.body());
                            ViewUtils.cancelLoadingDialog();
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
                                    SPUtils.put(MyApp.getInstance(),"TGC",tgt);
                                    SPUtils.put(MyApp.getInstance(),"username",uname+"");
                                    SPUtils.put(MyApp.getInstance(),"password",pwd+"");



                                    finish();
                                    Intent intent = new Intent();
                                    intent.putExtra("refreshService","dongtai");
                                    intent.setAction("refresh2");
                                    sendBroadcast(intent);


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

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            ViewUtils.cancelLoadingDialog();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            ViewUtils.cancelLoadingDialog();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    class LoginTypeAdapter extends CommonAdapter<LogInTypeBean> {


        public LoginTypeAdapter(Context context, int layoutId, List<LogInTypeBean> datas) {
            super(context, layoutId, datas);
            mContext = context;
        }

        @Override
        protected void convert(ViewHolder holder, final LogInTypeBean s, int position) {

            holder.setText(R.id.tv_name,s.getName());
            ImageView iv = holder.getConvertView().findViewById(R.id.iv_bg);
            LinearLayout ll_item = holder.getConvertView().findViewById(R.id.ll_go);
            TextView tv = holder.getConvertView().findViewById(R.id.tv_name);
            Glide.with(mContext).load(s.getImageId()).asBitmap().into(iv);
            switch (position){
                case 0:


                    ll_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            faceData();
                        }
                    });
                    break;
                case 1:
                     /* ll_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            yiJianData();
                        }
                    });*/
                    iv.setAlpha(0.5f);
                    tv.setAlpha(0.5f);
                    ll_item.setClickable(false);

                    break;
                case 2:
                    iv.setAlpha(0.5f);
                    tv.setAlpha(0.5f);
                    ll_item.setClickable(false);
                    //login(SHARE_MEDIA.WEIXIN);
                    break;
                case 3:
                    iv.setAlpha(0.5f);
                    tv.setAlpha(0.5f);
                    ll_item.setClickable(false);
                    //login(SHARE_MEDIA.QQ);
                    break;
                case 4:
                    iv.setAlpha(0.5f);
                    tv.setAlpha(0.5f);
                    ll_item.setClickable(false);
                    //login(SHARE_MEDIA.SINA);
                    break;
            }


        }



    }

    /**
     * 一键登录
     */
    private void yiJianData() {

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
        JVerificationInterface.loginAuth(this, true, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                Log.e("content",content);
                dismissLoadingDialog();
                if(code == 6000){
                    checkYiJianData(content);
                }else if(code == 6002){

                    ToastUtils.showToast(LoginActivity2.this,"用户取消了操作");
                }else {

                    ToastUtils.showToast(LoginActivity2.this,"请确认手机网络是否打开");
                }

            }
        });
    }

    private void checkYiJianData(String content) {
        OkGo.<String>post(HOME2_URL+ UrlRes.loginTokenVerifyUrl)
                .params( "openId","123456")
                .params( "loginToken",content)
                .execute(new StringCallback(){
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        ViewUtils.createLoadingDialog2(LoginActivity2.this,true,"一键登陆中");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e("一键登录",response.body());
                        FaceBean faceBean = JsonUtil.parseJson(response.body(),FaceBean.class);
                        if(faceBean != null){
                            try {

                                boolean success = faceBean.getSuccess();


                                if(success == true){
                                    netWorkLogin2(faceBean.getObj().getUserName(),faceBean.getObj().getPassWord(),"12");

                                }else {
                                    String msg = faceBean.getMsg();
                                    ToastUtils.showToast(LoginActivity2.this,msg );
                                    ViewUtils.cancelLoadingDialog();
                                }
                            }catch (Exception e){
                                ToastUtils.showToast(LoginActivity2.this,faceBean.getMsg() );
                                ViewUtils.cancelLoadingDialog();
                            }
                        }else {
                            FaceBean2 faceBean2 = JsonUtil.parseJson(response.body(),FaceBean2.class);
                            boolean success = faceBean2.getSuccess();
                            if(success == true){


                            }else {
                                String msg = faceBean2.getMsg();
                                ToastUtils.showToast(LoginActivity2.this,msg );
                                ViewUtils.cancelLoadingDialog();
                            }
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.showShort(getApplicationContext(),"找不到服务器了，请稍后再试");
                        ViewUtils.cancelLoadingDialog();
                    }
                });

    }


    private static final int RC_CAMERA_PERM = 123;

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!

            SPUtils.put(this,"bitmap","");
            Intent intent = new Intent(this,FaceActivity.class);
            startActivityForResult(intent,99);
            ;//调用相机照相
        } else {//没有相应权限，获取相机权限
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "获取照相机权限",
                    RC_CAMERA_PERM, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }



    /**
     * 刷脸登录
     */
    private void faceData() {

        cameraTask();
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


    @Override
    protected void onResume() {
        super.onResume();

        String isLoading2 = (String) SPUtils.get(LoginActivity2.this, "isloading2", "");
        if(!isLoading2 .equals("")){
            ViewUtils.createLoadingDialog2(LoginActivity2.this,true,"人脸识别中");
            SPUtils.put(getApplicationContext(),"isloading2","");
        }

    }





    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("facerefresh");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);



    }
    private int imageid = 0;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals("facerefresh")){
                String FaceActivity = intent.getStringExtra("FaceActivity");
                if(imageid == 0){
                    if(FaceActivity != null){
                        imageid = 1;
                        String s = (String)SPUtils.get(LoginActivity2.this, "bitmap", "");


                        String imei = (String) SPUtils.get(LoginActivity2.this, "imei", "");

//                }
                        try {
                            String secret  = AesEncryptUtile.encrypt(Calendar.getInstance().getTimeInMillis()+ "_"+"123456",key);
                            OkGo.<String>post(HOME2_URL+ UrlRes.getPassByFaceUrl)
                                    .params( "openId","123456")
                                    .params( "secret",secret)
                                    .params( "img",s )
                                    .params( "equipmentId",imei)
                                    .execute(new StringCallback(){

                                        @Override
                                        public void onStart(Request<String, ? extends Request> request) {
                                            super.onStart(request);
//                                    ViewUtils.createLoadingDialog2(LoginActivity2.this,true,"人脸识别中");
                                        }

                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            SPUtils.put(getApplicationContext(),"isloading2","");
                                            Log.e("tag",response.body());
                                            FaceBean faceBean = JsonUtil.parseJson(response.body(),FaceBean.class);
                                            try{
                                                boolean success = faceBean.getSuccess();
                                                String msg = faceBean.getMsg();
                                                if(success == true){
                                                    Log.e("调试1",response.body());
                                                    Boolean verification = faceBean.getObj().getVerification();
                                                    if(verification == false){
                                                        netWorkLogin2(faceBean.getObj().getUserName(),faceBean.getObj().getPassWord(), "9");
                                                    }else {
                                                        ViewUtils.cancelLoadingDialog();
                                                        Intent intent = new Intent(LoginActivity2.this,CodeBindActivity.class);
                                                        intent.putExtra("phone",faceBean.getObj().getPhone());
                                                        intent.putExtra("username",faceBean.getObj().getUserName());
                                                        intent.putExtra("password",faceBean.getObj().getPassWord());
                                                        startActivity(intent);
                                                        FinishActivity.addActivity(LoginActivity2.this);
                                                    }

                                                }else {
                                                    Log.e("调试2",response.body());
                                                    ToastUtils.showToast(LoginActivity2.this,msg);
                                                    ViewUtils.cancelLoadingDialog();
                                                    imageid = 0;
                                                }
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                ViewUtils.cancelLoadingDialog();
                                            }

                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);
                                            SPUtils.put(getApplicationContext(),"isloading2","");
                                            T.showShort(getApplicationContext(),"找不到服务器了，请稍后再试");
                                            ViewUtils.cancelLoadingDialog();
                                            imageid = 0;
                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        imageid = 0;
                    }
                }

            }
        }
    };

    /**
     *
     * @param requestCode
     * @param permissions 请求的权限
     * @param grantResults 请求权限返回的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            // camear 权限回调

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                // 表示用户授权
                Toast.makeText(LoginActivity2.this, " user Permission" , Toast.LENGTH_SHORT).show();

                SPUtils.put(this,"bitmap","");
                Intent intent = new Intent(this,FaceActivity.class);
                startActivityForResult(intent,99);


            } else {

                //用户拒绝权限
                Toast.makeText(LoginActivity2.this, " no Permission" , Toast.LENGTH_SHORT).show();

            }



        }

    }


}
