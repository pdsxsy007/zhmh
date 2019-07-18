package io.cordova.zhqy.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.Calendar;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.fingerUtil.MD5Util;

import static io.cordova.zhqy.utils.AesEncryptUtile.key;



/**
 * Created by Administrator on 2018/12/11 0011.
 */

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    private String s1;
    private String s2;
    private Handler handler = new MyHandler(this);
    private static String localVersionName;
    @BindView(R.id.webView)
    WebView webView;
    public static final int PASSWORD_LOGIN_FLAG = 0x0004;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        imageView =  (ImageView) findViewById(R.id.iv_bg);

            getLocalVersionName(getApplicationContext());
            //getMsg();
        String infoType = (String) SPUtils.get(MyApp.getInstance(), "InfoType", "");
        //ToastUtils.showToast(this,infoType+"infoType");
        if(infoType.equals("1")){
            getMsg();
            SPUtils.put(MyApp.getInstance(),"InfoType","");
        }else {
            handleOpenClick();
        }

            initView();


    }

    protected void initView() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }
    /**
     * 暂时闪屏界面不需要执行什么操作，所以先发个2秒的延时空消息,其实可以把软件所需的申请权限放和检查版本更新放这
     *
     */
    private class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                finish();
            }else {

                if (SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN)) {
                   Intent intent = new Intent(SplashActivity.this,LoginActivity3.class);
                   startActivity(intent);
                   finish();
                }else {
                    netWorkLogin();
                }

            }

          /*  Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            startActivity(intent);
            finish();*/
        }
    }

    LoginBean loginBean;
    String tgt;
    private void netWorkLogin() {
        s1 = (String) SPUtils.get(MyApp.getInstance(),"username","");
        s2 =  (String) SPUtils.get(MyApp.getInstance(),"password","");
        Log.e("login","s1 = "+ s1 + "  ,s2  = " + s2);
        OkGo.<String>get(UrlRes.HOME2_URL +"/cas/casApiLoginController")
//        OkGo.<String>get("http://192.168.30.29:8080" +"/cas/casApiLoginController")
                .params("openid","123456")
                .params("username",s1)
                .params("password",s2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        loginBean = JSON.parseObject(response.body(),LoginBean.class);
                        if (loginBean.isSuccess() ) {
                            try {

                                tgt = AesEncryptUtile.decrypt(loginBean.getAttributes().getTgt(),key);
                                String userName = AesEncryptUtile.decrypt(loginBean.getAttributes().getUsername(),key) ;
                                String userId  = AesEncryptUtile.encrypt(userName+ "_"+ Calendar.getInstance().getTimeInMillis(),key);

                                SPUtils.put(MyApp.getInstance(),"userId",userId);
//                                SPUtils.put(MyApp.getInstance(),"tgt",tgt);
                                SPUtils.put(getApplicationContext(),"TGC",tgt);
                                SPUtils.put(getApplicationContext(),"username",s1);
                                SPUtils.put(getApplicationContext(),"password",s2);
                               /* webView.setWebViewClient(mWebViewClient);
                                webView.loadUrl("http://iapp.zzuli.edu.cn/portal/login/appLogin");*/
                                Intent intent = new Intent(MyApp.getInstance(),Main2Activity.class);
                                intent.putExtra("userId",userName);
                                startActivity(intent);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            T.showShort(MyApp.getInstance(),loginBean.getMsg());

                            SPUtils.put(MyApp.getInstance(),"userId","");
//                                SPUtils.put(MyApp.getInstance(),"tgt",tgt);
                            SPUtils.put(getApplicationContext(),"TGC","");
                            SPUtils.put(getApplicationContext(),"username","");
                            SPUtils.put(getApplicationContext(),"password","");
                            Intent intent = new Intent(MyApp.getInstance(),Main2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        finish();
                        Intent intent = new Intent(MyApp.getInstance(),Main2Activity.class);
                        startActivity(intent);
                        finish();

                    }
                });

    }

    /**华为 OPPO  Fac*/
    private static final String TAG = "HuaWei";
    /**消息Id**/
    private static final String KEY_MSGID = "msg_id";
    private static final String KEY_TYPE = "msg_type";
    /**该通知的下发通道**/
    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
    /**通知标题**/
    private static final String KEY_TITLE = "n_title";
    /**通知内容**/
    private static final String KEY_CONTENT = "n_content";
    /**通知附加字段**/
    private static final String KEY_EXTRAS = "n_extras";

    /**
     * 处理点击事件，当前启动配置的Activity都是使用
     * Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
     * 方式启动，只需要在onCreat中调用此方法进行处理
     */
    private void handleOpenClick() {
        Log.d(TAG, "用户点击打开了通知");
        String data = null;
        //获取华为平台附带的jpush信息
        if (getIntent().getData() != null) {
            data = getIntent().getData().toString();
        }

        //获取fcm或oppo平台附带的jpush信息
        if (TextUtils.isEmpty(data) && getIntent().getExtras() != null) {
            data = getIntent().getExtras().getString("JMessageExtra");
        }

        Log.w(TAG, " -消息体- " + String.valueOf(data));
        if (TextUtils.isEmpty(data)) return;
        try {
            JSONObject jsonObject = new JSONObject(data);
            String msgId = jsonObject.optString(KEY_MSGID);
            String msgType = jsonObject.optString(KEY_TYPE);
            byte whichPushSDK = (byte) jsonObject.optInt(KEY_WHICH_PUSH_SDK);
            String title = jsonObject.optString(KEY_TITLE);
            String content = jsonObject.optString(KEY_CONTENT);
            String extras = jsonObject.optString(KEY_EXTRAS);

            try {
                JSONObject extraJson = new JSONObject(extras);
                if (extraJson.length() > 0) {
                    Log.e("extras", extras);
                    Log.e("extraJson", extraJson.getString("messageId"));
                    msgId =  extraJson.getString("messageId");
                    msgType =  extraJson.getString("messageType");
                    Log.e("msgId", msgId);
                    SPUtils.put(MyApp.getInstance(),"msgId",msgId);
                    SPUtils.put(MyApp.getInstance(),"msgType",msgType);
                }
            } catch (JSONException ignored) {
                Log.e("JPush",ignored.getMessage());
            }

//            StringBuilder sb = new StringBuilder();
//            sb.append("msgId:");
//            sb.append(String.valueOf(msgId));
//            sb.append("\n");
//            sb.append("title:");
//            sb.append(String.valueOf(title));
//            sb.append("\n");
//            sb.append("content:");
//            sb.append(String.valueOf(content));
//            sb.append("\n");
//            sb.append("extras:");
//            sb.append(String.valueOf(extras));
//            sb.append("\n");
//            sb.append("platform:");
//            sb.append(getPushSDKName(whichPushSDK));
////            mTextView.setText(sb.toString());
//            Log.w(TAG, sb.toString()+"  - 华为推送");
            //上报点击事件
            JPushInterface.reportNotificationOpened(this, msgId, whichPushSDK);
        } catch (JSONException e) {
            Log.w(TAG, "parse notification error");
        }


    }

    /**
     * 推送消息获取
     * msgType 消息类型(-1:删除，0:系统消息,1:待办,2:待阅,3:已办,4:已阅,5:我的申请)
     *
     * */
    private void getMsg() {
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            String extrasBean = null;
            String extrasBean2 = null;
            String msgId = null;
            String msgType = null;
            if(bundle!=null){

                Log.e("bundle",bundle+"");
               // ToastUtils.showToast(SplashActivity.this,bundle+"");
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                extrasBean = bundle.getString(JPushInterface.EXTRA_EXTRA);
                extrasBean2 = bundle.getString("JMessageExtra");
                if(!StringUtils.isEmpty(extrasBean)){
                    try {
                        JSONObject extraJson = new JSONObject(extrasBean);
                        if (extraJson.length() > 0) {
                            Log.e("extras", extrasBean);
                            Log.e("extraJson", extraJson.getString("messageId"));
                            msgId =  extraJson.getString("messageId");
                            msgType =  extraJson.getString("messageType");
                            Log.e("msgId", msgId);
                            SPUtils.put(MyApp.getInstance(),"msgId",msgId);
                            SPUtils.put(MyApp.getInstance(),"msgType",msgType);

                        }
                    } catch (JSONException ignored) {
                        Log.e("JPush",ignored.getMessage());
                    }
                }
                /*else if(!StringUtils.isEmpty(extrasBean2)){

                    try {

                        JSONObject extraJson2 = null;
                        extraJson2 = new JSONObject(extrasBean2);
                       *//* Log.e("extras", extrasBean);
                        Log.e("extraJson", extraJson.getString("messageId"));*//*
                        String n_extras = extraJson2.getString("n_extras");
                        JSONObject n_extras2 = new JSONObject(n_extras);
                        msgId =  n_extras2.getString("messageId");
                        msgType =  n_extras2.getString("messageType");
                        Log.e("msgId", msgId);
                        SPUtils.put(MyApp.getInstance(),"msgId",msgId);
                        SPUtils.put(MyApp.getInstance(),"msgType",msgType);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {

                }*/
            }
            Log.e("JPush","Title : " + title + "  " + "Content : " + content + "   msgId  : " + msgId + "   msgType  : " + msgType);


        }
    }

    /**
     * 获取本地软件版本号
     */
//    public static float getLocalVersion(Context ctx) {
//
//        try {
//            PackageInfo packageInfo = ctx.getApplicationContext()
//                    .getPackageManager()
//                    .getPackageInfo(ctx.getPackageName(), 0);
//            float localVersion = packageInfo.versionCode;
//            Log.d("TAG", "本软件的版本号 = " + localVersion);
//
////            SPUtils.put(ctx,"localVersion",localVersion);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return localVersion;
//    }

    /**
     * 获取本地软件版本号名称
     */

    public static String getLocalVersionName(Context ctx) {
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersionName = packageInfo.versionName;
            Log.d("TAG", "本软件的版本号 = " + localVersionName);
              SPUtils.put(ctx.getApplicationContext(),"VersionName", localVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersionName;
    }


    private void setPermission() {
        //同时请求多个权限
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(new io.reactivex.functions.Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.e("用户已经同意该权限", permission.name + " is granted.");
                            //   Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Log.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   Log.d(TAG, permission.name + " is denied.");
                            Log.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
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
