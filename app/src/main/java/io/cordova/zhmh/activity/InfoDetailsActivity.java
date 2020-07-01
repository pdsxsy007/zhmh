package io.cordova.zhmh.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.just.agentweb.download.DownloadingService;
import com.jwsd.libzxing.OnQRCodeListener;
import com.jwsd.libzxing.QRCodeManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.AppOrthBean;
import io.cordova.zhmh.bean.BaseBean;
import io.cordova.zhmh.bean.DownLoadBean;
import io.cordova.zhmh.bean.ImeiBean;
import io.cordova.zhmh.bean.LocalFaceBean;
import io.cordova.zhmh.bean.LocalVersonBean;
import io.cordova.zhmh.bean.LocationBean;
import io.cordova.zhmh.bean.LocationBean2;
import io.cordova.zhmh.bean.NaturePicBean;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.BaseActivity;
import io.cordova.zhmh.utils.BitmapUtils;
import io.cordova.zhmh.utils.CookieUtils;
import io.cordova.zhmh.utils.DensityUtil;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.MobileInfoUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.PermissionsUtil;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.ScreenSizeUtils;
import io.cordova.zhmh.utils.SoundPoolUtils;
import io.cordova.zhmh.utils.StringUtils;
import io.cordova.zhmh.utils.ToastUtils;
import io.cordova.zhmh.utils.ViewUtils;
import io.cordova.zhmh.web.FileUtil;
import io.cordova.zhmh.web.WebLayout2;
import io.cordova.zhmh.web.WebLayout4;
import io.cordova.zhmh.widget.MyDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static io.cordova.zhmh.UrlRes.HOME_URL;
import static io.cordova.zhmh.UrlRes.functionInvocationLogUrl;
import static io.cordova.zhmh.activity.SplashActivity.getLocalVersionName;
import static io.cordova.zhmh.utils.AesEncryptUtile.key;
import static io.cordova.zhmh.utils.MobileInfoUtils.getIMEI;
import static io.cordova.zhmh.utils.PermissionsUtil.SETTINGS_REQ_CODE;

/**
 * Created by Administrator on 2019/4/16 0016.
 */

public class InfoDetailsActivity extends BaseActivity implements PermissionsUtil.IPermissionsCallback{

    @BindView(R.id.tv_title)
    TextView tv_title;


     @BindView(R.id.ll_web)
    LinearLayout linearLayout;
    protected AgentWeb mAgentWeb;


    @BindView(R.id.layout_back)
    RelativeLayout layout_back;
    String appUrl,title,tgc;
    String appUrl2;
    String time;
    String msgsender;
    public LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private PermissionsUtil permissionsUtil;

    @Override
    protected int getResourceId() {
        return R.layout.activity_info_details;
    }

    @Override
    protected void initView() {
        super.initView();

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.ll_web);
        title = getIntent().getStringExtra("title2");
        appUrl = getIntent().getStringExtra("appUrl");
        appUrl2 = getIntent().getStringExtra("appUrl2");
        msgsender = getIntent().getStringExtra("msgsender");
        time = getIntent().getStringExtra("time");

        tgc = (String) SPUtils.get(getApplicationContext(), "TGC", "");
        if (StringUtils.isEmpty(title)){
            tv_title.setText("系统消息");
        }else {
            tv_title.setText(title);
        }

        String latitude = (String) SPUtils.get(MyApp.getInstance(), "latitude", "");
        String longitude = (String) SPUtils.get(MyApp.getInstance(), "longitude", "");
        if(latitude.equals("") || longitude.equals("")){
            permissionsUtil = PermissionsUtil
                    .with(this)
                    .requestCode(0)
                    .isDebug(true)
                    .permissions(PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION)
                    .request();

        }


        //appUrl2 = "【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）";
       if(appUrl!= null){

           if(appUrl.contains("gilight://")){

               String endUrl = appUrl.substring(10,appUrl.length());
               String appServiceUrl2 = UrlRes.huanxingUrl+"?"+endUrl;
               Uri uri = Uri.parse(appServiceUrl2);
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               if (intent.resolveActivity(getPackageManager()) != null) {
                   // 网址正确 跳转成功
                   startActivity(intent);
                   finish();
               } else {
                   //网址不正确 跳转失败 提示错误
                   //Toast.makeText(MainActivity.this, "网址输入错误，请重新输入！", Toast.LENGTH_SHORT).show();

               }


           }




           mAgentWeb = AgentWeb.with(this)//
                   .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                   .useDefaultIndicator(getResources().getColor(R.color.colorPrimaryDark), 1)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
////                .setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
//                .useDefaultIndicator(getResources().getColor(R.color.title_bar_bg),3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                    .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                    .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                    .setWebChromeClient(mWebChromeClient) //WebChromeClient
                    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                   .setWebLayout(new WebLayout4(this))
                   .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                   .createAgentWeb()//创建AgentWeb。
                   .ready()//设置 WebSettings。
                   .go(appUrl);
           mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
           mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface());
       }else {



           appUrl = "\n" +
                   "<html>\n" +
                   "\n" +
                   "<head>\n" +
                   "    <meta charset=\"utf-8\">\n" + "<title>"+title+"</title>\n" +
                   "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no\">\n" +
                   "    <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                   "    <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                   "    <style type=\"text/css\">\n" +
                   "        *{\n" +
                   "            margin: 0;\n" +
                   "            padding: 0;\n" +
                   "         }\n" +
                   "         html,body{\n" +
                   "            width: 100%;\n" +
                   "            height: 100%;\n" +
                   "            font-size: 12px;\n" +
                   "         }\n" +
                   "        h2{\n" +
                   "            text-align: center;\n" +
                   "            margin-bottom: 1rem;\n" +
                   "            font-size: 2rem;\n" +
                   "        }\n" +
                   "        h5{\n" +
                   "           text-align: center;\n" +
                   "            margin-bottom: 1rem; \n" +
                   "        }\n" +
                   "         h5 span{\n" +
                   "            display: inline-block;\n" +
                   "            color: #666;\n" +
                   "            font-weight: normal;\n" +
                   "            margin: 0 1rem;\n" +
                   "         }\n" +
                   "        .content{\n" +
                   "            padding: 1rem;\n" +
                   "        }\n" +
                   "        .message_detail_area{\n" +
                   "            border-top: 1px dotted #ccc;\n" +
                   "            font-size: 1.2rem;\n" +
                   "            line-height: 1.6rem;\n" +
                   "            padding: 1rem 0;\n" +
                   "        }\n" +
                   "    </style>\n" +
                   "<body class=\"combg\">\n" +
                   "<div class=\"content\">\n" +
                   "        <div class=\"mui-content-padded\">\n" +
                   "            <div class=\"message_detail_title\">\n" + "<h2>"+title+"</h2>\n" +
                   "                <h5><span>发布人："+msgsender+"</span><span>发布时间："+stampToDate(time)+"</span></h5>\n" +
                   "            </div>\n" +
                   "            <article class=\"message_detail_area\">"+appUrl2+"</article>\n" +
                   "        </div>\n" +
                   "</div>\n" +
                   "</body>\n" +
                   "</html>";

           /*String standard = "<html> \n" +
                   "<head> \n" +
                   "<title>"+title+"</title> \n"+
                   "<style type=\"text/css\"> \n" +
                   "body {font-size:13px;}\n" +
                   "</style> \n" +
                   "</head> \n" +
                   "<body>" +
                   "<script type='text/javascript'>" +
                   "w  indow.onload = function(){\n" +
                   "var $img = document.getElementsByTagName('img');\n" +
                   "for(var p in  $img){\n" +
                   " $img[p].style.width = '100%%';\n" +
                   "$img[p].style.height ='auto'\n" +
                   "}\n" +
                   "}" +
                   "</script>" +
                   appUrl2
                   + "</body>" +
                   "</html>";*/
           mAgentWeb = AgentWeb.with(this)//
                   .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                   .useDefaultIndicator(getResources().getColor(R.color.colorPrimaryDark), 1)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
////                .setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
//                .useDefaultIndicator(getResources().getColor(R.color.title_bar_bg),3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                   .setWebLayout(new WebLayout2(this))
//                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                   .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                   .createAgentWeb()//创建AgentWeb。
                   .ready()//设置 WebSettings。
                   .go(appUrl);
           mAgentWeb.getUrlLoader().loadDataWithBaseURL(null,appUrl,"text/html","UTF-8",null);
           mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
           mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface());
       }

        registerBoradcastReceiver();
        registerBoradcastReceiver2();
    }

    private void registerBoradcastReceiver2() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("facereYiQing2");
        //注册广播
        registerReceiver(broadcastReceiver2, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals("facereYiQing2")){
                LocalFaceBean localVersonBean = new LocalFaceBean();
                localVersonBean.setSuccess(false);
                localVersonBean.setMessage("用户取消了人脸识别");
                Gson gson = new Gson();
                String s = gson.toJson(localVersonBean);
                String jsonParams = s;
                String url2 = "javascript:getFaceAndroidParams('"+jsonParams+"')";
                mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        //此处为 js 返回的结果
                        Log.e("value",value);
                    }
                });



            }
        }
    };



    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("facereYiQing");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);

    }


    private static final int RC_CAMERA_PERM2 = 124;


    @Override
    protected void initData() {
        super.initData();
        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mAgentWeb.back()){
                    InfoDetailsActivity.this.finish();
                }
            }
        });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            double altitude = location.getAltitude();
            float speed = location.getSpeed();
            float direction = location.getDirection();
            int locationWhere = location.getLocationWhere();

            Log.e("errorCode",errorCode+"");
            Log.e("latitude",latitude+"");
            Log.e("longitude",longitude+"");
            com.baidu.location.Address address = location.getAddress();
            String addressLine = address.address;
            String country = address.country;
            String countryCode = address.countryCode;
            String province = address.province;
            String city = address.city;
            String cityCode = address.cityCode;
            String district = address.district;
            String town = address.town;
            String adcode = address.adcode;
            String street = address.street;
            String streetNumber = address.streetNumber;
            if(addressLine != null){
                SPUtils.put(InfoDetailsActivity.this,"addressLine",addressLine);
            }
            if(country != null){
                SPUtils.put(InfoDetailsActivity.this,"country",country);
            }

            if(countryCode != null){
                SPUtils.put(InfoDetailsActivity.this,"countryCode",countryCode);
            }

            if(province != null){
                SPUtils.put(InfoDetailsActivity.this,"province",province);
            }

            if(city != null){
                SPUtils.put(InfoDetailsActivity.this,"city",city);
            }

            if(cityCode != null){
                SPUtils.put(InfoDetailsActivity.this,"cityCode",cityCode);
            }

            if(district != null){
                SPUtils.put(InfoDetailsActivity.this,"district",district);
            }

            if(adcode != null){
                SPUtils.put(InfoDetailsActivity.this,"adcode",adcode);
            }

            if(street != null){
                SPUtils.put(InfoDetailsActivity.this,"street",street);
            }

            if(streetNumber != null){
                SPUtils.put(InfoDetailsActivity.this,"streetNumber",streetNumber);
            }
            if(town != null){
                SPUtils.put(InfoDetailsActivity.this,"town",town);
            }
            SPUtils.put(InfoDetailsActivity.this,"latitude",latitude+"");
            SPUtils.put(InfoDetailsActivity.this,"longitude",longitude+"");
            SPUtils.put(InfoDetailsActivity.this,"altitude",altitude+"");
            SPUtils.put(InfoDetailsActivity.this,"speed",speed+"");
            SPUtils.put(InfoDetailsActivity.this,"direction",direction+"");
            SPUtils.put(InfoDetailsActivity.this,"locationWhere",locationWhere+"");
        }
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // super.onReceivedSslError(view, handler, error);
            /**
             *  Webview在安卓5.0之前默认允许其加载混合网络协议内容
             *  在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                handler.proceed(); // 接受所有网站的证书
            }


        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            CookieManager cookieManager = CookieManager.getInstance();
            if(Build.VERSION.SDK_INT>=21){
                cookieManager.setAcceptThirdPartyCookies(view, true);
            }



        }
        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }
            Log.e("url",url);
            //String isapp = url.substring(url.indexOf("/") + 2, 11);

           /* if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }

             String intercept = url.substring(0,url.indexOf(":")+3);
            if (hasApplication(intercept)){
                try {
                    //直接根据Scheme打开软件  拼接参数
                    if (url.contains("{memberid}")){
                        String s1=  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"personName",""), "UTF-8");
                        url =  url.replace("{memberid}", s1);
                    }
                    if (url.contains("{memberAesEncrypt}")){
                        String memberAesEncrypt = AesEncryptUtile.encrypt((String) SPUtils.get(MyApp.getInstance(),"personName",""), String.valueOf(appsBean.getAppSecret()));
                        String s2=  URLEncoder.encode(memberAesEncrypt, "UTF-8");
                        url =  url.replace("{memberAesEncrypt}", s2);
//                                                 appUrl.substring(0,appUrl.indexOf("\"{memberAesEncrypt}\""));
                    }
                    if (url.contains("{quicklyTicket}")){
                        String s3 =  URLEncoder.encode((String) SPUtils.get(MyApp.getInstance(),"TGC",""), "UTF-8");
                        url = url.replace("{quicklyTicket}",s3);
                    }
                    Log.e("TAG", appUrl+"");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
            }*/
           if(url.contains("chaoxingshareback")){
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
               startActivity(intent);
           }


            return super.shouldOverrideUrlLoading(view, request);
        }


        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String downLoadType = (String) SPUtils.get(InfoDetailsActivity.this, "downLoadType", "");
            DownLoadBean downLoadBean = JsonUtil.parseJson(downLoadType,DownLoadBean.class);
            List<String> downLoadTypeList = downLoadBean.getString();
            for (int i = 0; i < downLoadTypeList.size(); i++) {
                if(url.contains(downLoadTypeList.get(i))){
                    Log.e("tag",downLoadTypeList.get(i));
                    logOut(url);
                    //getSettings();
                    return true;
                }else {

                }
            }
            if(url.contains("gilight://")){

                String endUrl = url.substring(10,url.length());
                String fullUrl = UrlRes.huanxingUrl+"?"+endUrl;

                //view.loadUrl(endUrl);
                Uri uri = Uri.parse(fullUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // 网址正确 跳转成功
                    startActivity(intent);
                    //finish();
                } else {
                    //网址不正确 跳转失败 提示错误
                    //Toast.makeText(MainActivity.this, "网址输入错误，请重新输入！", Toast.LENGTH_SHORT).show();

                }

                return true;

            }else {
                if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                    if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username","")) || tgc.equals("")){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                        startActivity(intent);
                        finish();

                        return true;
                    }
                }


            }



            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            CookieUtils.syncCookie("http://kys.zzuli.edu.cn","CASTGC="+tgc,getApplication());
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //   do you work
            Log.i("Info", "onProgress:" + newProgress);
        }

        /*Title*/
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            if (mTitleTextView != null) {
//                mTitleTextView.setText(title);
//            }
        }

    };
    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings()  {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;
            private WebSettings mWebSettings;
            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            @Override
            public IAgentWebSettings toSetting(WebView webView) {

                return super.toSetting(webView);
            }

            /**
             * AgentWeb 4.0.0 内部删除了 DownloadListener 监听 ，以及相关API ，将 Download 部分完全抽离出来独立一个库，
             * 如果你需要使用 AgentWeb Download 部分 ， 请依赖上 compile 'com.just.agentweb:download:4.0.0 ，
             * 如果你需要监听下载结果，请自定义 AgentWebSetting ， New 出 DefaultDownloadImpl，传入DownloadListenerAdapter
             * 实现进度或者结果监听，例如下面这个例子，如果你不需要监听进度，或者下载结果，下面 setDownloader 的例子可以忽略。
             * @param webView
             * @param downloadListener
             * @return WebListenerManager
             */
            @Override
            public WebListenerManager setDownloader(WebView webView, DownloadListener downloadListener) {
                return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create((Activity) webView.getContext(),
                                        webView,
                                        mDownloadListenerAdapter,
                                        mDownloadListenerAdapter,
                                        this.mAgentWeb.getPermissionInterceptor()));
            }
        };
    }
    private static final int REQUEST_SHARE_FILE_CODE = 120;
    private DownloadingService mDownloadingService;
    /**
     * 更新于 AgentWeb  4.0.0
     */
    protected DownloadListenerAdapter mDownloadListenerAdapter = new DownloadListenerAdapter() {
        /**
         *
         * @param url                下载链接
         * @param userAgent          UserAgent
         * @param contentDisposition ContentDisposition
         * @param mimetype           资源的媒体类型
         * @param contentLength      文件长度
         * @param extra              下载配置 ， 用户可以通过 Extra 修改下载icon ， 关闭进度条 ， 是否强制下载。
         * @return true 表示用户处理了该下载事件 ， false 交给 AgentWeb 下载
         */
        @Override
        public boolean onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, AgentWebDownloader.Extra extra) {
            Log.i("下载链接", "onStart:" + url);
            extra.setOpenBreakPointDownload(true) // 是否开启断点续传
                    .setIcon(R.drawable.ic_file_download_black_24dp) //下载通知的icon
                    .setConnectTimeOut(6000) // 连接最大时长
                    .setBlockMaxTime(10 * 60 * 1000)  // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                    .setDownloadTimeOut(Long.MAX_VALUE) // 下载最大时长
                    .setParallelDownload(false)  // 串行下载更节省资源哦
                    .setEnableIndicator(true)  // false 关闭进度通知
                    //.addHeader("Cookie", "xx") // 自定义请求头
                    //  .setAutoOpen(false) // 下载完成自动打开
                    .setForceDownload(true); // 强制下载，不管网络网络类型
            return false;
        }

        /**
         *
         * 不需要暂停或者停止下载该方法可以不必实现
         * @param url
         * @param downloadingService  用户可以通过 DownloadingService#shutdownNow 终止下载
         */
        @Override
        public void onBindService(String url, DownloadingService downloadingService) {
            super.onBindService(url, downloadingService);
            mDownloadingService = downloadingService;
            Log.i("停止下载", "onBindService:" + url + "  DownloadingService:" + downloadingService);
        }

        /**
         * 回调onUnbindService方法，让用户释放掉 DownloadingService。
         * @param url
         * @param downloadingService
         */
        @Override
        public void onUnbindService(String url, DownloadingService downloadingService) {
            super.onUnbindService(url, downloadingService);
            mDownloadingService = null;
            Log.i("回调onUnbindService方法", "onUnbindService:" + url);
        }

        /**
         *
         * @param url  下载链接
         * @param loaded  已经下载的长度
         * @param length    文件的总大小
         * @param usedTime   耗时 ，单位ms
         * 注意该方法回调在子线程 ，线程名 AsyncTask #XX 或者 AgentWeb # XX
         */
        @Override
        public void onProgress(String url, long loaded, long length, long usedTime) {
            int mProgress = (int) ((loaded) / Float.valueOf(length) * 100);
            Log.i("下载进度", "onProgress:" + mProgress);
            super.onProgress(url, loaded, length, usedTime);
        }

        /**
         *
         * @param path 文件的绝对路径
         * @param url  下载地址
         * @param throwable    如果异常，返回给用户异常
         * @return true 表示用户处理了下载完成后续的事件 ，false 默认交给AgentWeb 处理
         */
        @Override
        public boolean onResult(String path, String url, Throwable throwable) {
            if (null == throwable) { //下载成功
                //do you work
                Log.e("下载失败",path);
                Log.e("下载失败",url);

                Uri shareFileUrl = FileUtil.getFileUri(InfoDetailsActivity.this,null,new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(InfoDetailsActivity.this)
                        .setContentType(ShareContentType.FILE)
                        .setShareFileUri(shareFileUrl)
                        .setTitle("Share File")
                        .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
                        .build()
                        .shareBySystem();

            } else {//下载成功
                Log.e("path",path);

                Uri shareFileUrl = FileUtil.getFileUri(InfoDetailsActivity.this,null,new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(InfoDetailsActivity.this)
                        .setContentType(ShareContentType.FILE)
                        .setShareFileUri(shareFileUrl)
                        .setTitle("Share File")
                        .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
                        .build()
                        .shareBySystem();

            }
            return false; // true  不会发出下载完成的通知 , 或者打开文件
        }
    };

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }
    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        mLocationClient.stop();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiver2);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            if (!mAgentWeb.back()){
                InfoDetailsActivity.this.finish();
                return true;

            }


        }
        return super.onKeyDown(keyCode, event);
    }

    /*
 * 将时间戳转换为时间
 */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 判断是否安装了应用
     * @return true 为已经安装
     */
    private boolean hasApplication(String scheme) {
        PackageManager manager = getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(scheme));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    /**扫码*/
    private static final int QR_CODE = 55846;
    private Handler deliver = new Handler(Looper.getMainLooper());

    /**震动响铃*/
    private Handler handler = new MyHandler(this);




    @SuppressLint("HandlerLeak")
    public class MyHandler extends Handler {
        private WeakReference<Context> reference;
        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            //关闭铃声
            SoundPoolUtils.stopRing();
            //关闭震动
            SoundPoolUtils.virateCancle(InfoDetailsActivity.this);

        }
    }
    /**Js调用内部类*/
    public class AndroidInterface {


        /**震动响铃*/
        @JavascriptInterface
        public void playSoundAndVibration() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    setAlarmParams();
                    handler.sendEmptyMessageDelayed(0, 1100);
                }
            });

        }

        /**清理缓存*/
        @JavascriptInterface
        public void cleanUpAppCache() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    toCleanWebCache();
                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }
        /**返回上一页*/
        @JavascriptInterface
        public void backToLastUrl() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    if (!mAgentWeb.back()){
                        finish();
                    }else {
                        mAgentWeb.back();
                    }

                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }


        /**进入扫描二维码页面*/
        @JavascriptInterface
        public void nativeScanQRCode(final String invocationLogAppId,final String invocationLogFunction) {
            ceshiData(invocationLogAppId,invocationLogFunction,"nativeScanQRCode");

        }
        @JavascriptInterface
        public void nativeOpenSystemSetting(final String invocationLogAppId,final String invocationLogFunction,final String domainName) {
            ceshiData(invocationLogAppId,invocationLogFunction,"nativeOpenSystemSetting");

        }

        @JavascriptInterface
        public void nativeGetPicture(final String ratio,final String invocationLogAppId,final String invocationLogFunction) {
            if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //ceshiFaceData(invocationLogAppId,invocationLogFunction,"nativeVerifyUserAndFace");
                ceshiDataNativeGetPicture(invocationLogAppId,invocationLogFunction, "nativeGetPicture",ratio);
            } else {
                permissionsUtil = PermissionsUtil
                        .with(InfoDetailsActivity.this)
                        .requestCode(0)
                        .isDebug(true)
                        .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                        .request();
            }


        }

        @JavascriptInterface
        public void nativeGetPicture(final String invocationLogAppId,final String invocationLogFunction) {
            if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ceshiDataNativeGetPicture(invocationLogAppId,invocationLogFunction, "nativeGetPicture",0.8+"");
            } else {
                permissionsUtil = PermissionsUtil
                        .with(InfoDetailsActivity.this)
                        .requestCode(0)
                        .isDebug(true)
                        .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                        .request();
            }

        }


        /**手机定位坐标*/
        @JavascriptInterface
        public void nativeGetLocation(final String invocationLogAppId,final String invocationLogFunction) {
            Log.e("nativeGetLocation","执行了");
            if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ceshiData(invocationLogAppId,invocationLogFunction, "nativeGetLocation");

            } else {//没有相应权限，获取相机权限

                //TestShowDig.AskForPermission(BaseWebActivity4.this,"定位");
                permissionsUtil = PermissionsUtil
                        .with(InfoDetailsActivity.this)
                        .requestCode(0)
                        .isDebug(true)
                        .permissions(PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION)
                        .request();
            }
        }

        @JavascriptInterface
        public void nativeVerifyUserAndFace(final String invocationLogAppId,final String invocationLogFunction) {
            if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ceshiFaceData(invocationLogAppId,invocationLogFunction,"nativeVerifyUserAndFace");
            } else {
                permissionsUtil = PermissionsUtil
                        .with(InfoDetailsActivity.this)
                        .requestCode(0)
                        .isDebug(true)
                        .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                        .request();
            }

        }


        @JavascriptInterface
        public void nativeGetLocation(final String invocationLogAppId,final String invocationLogFunction,final String needExtraInfo) {
            if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ceshiData2(invocationLogAppId,invocationLogFunction, "nativeGetLocation",needExtraInfo);

            } else {//没有相应权限，获取相机权限

                //TestShowDig.AskForPermission(BaseWebActivity4.this,"定位");
                permissionsUtil = PermissionsUtil
                        .with(InfoDetailsActivity.this)
                        .requestCode(0)
                        .isDebug(true)
                        .permissions(PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION)
                        .request();
            }




        }

        /**关闭当前页面*/
        @JavascriptInterface
        public void nativeCloseCurrentPage(final String invocationLogAppId,final String invocationLogFunction) {
            ceshiData(invocationLogAppId,invocationLogFunction, "nativeCloseCurrentPage");

            Log.i("Info", "Thread:" + Thread.currentThread());
        }

        /**获取设备imei*/
        @JavascriptInterface
        public void nativeGetEquipmentId(final String invocationLogAppId,final String invocationLogFunction) {
            if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                imeiTask(invocationLogAppId,invocationLogFunction);

            } else {//没有相应权限，获取相机权限

                //TestShowDig.AskForPermission(BaseWebActivity4.this,"定位");
                permissionsUtil = PermissionsUtil
                        .with(InfoDetailsActivity.this)
                        .requestCode(2)
                        .isDebug(true)
                        .permissions(PermissionsUtil.Permission.Phone.READ_PHONE_STATE)
                        .request();
            }
        }

        /**获取设备imei*/
        @JavascriptInterface
        public void nativeGetLocalVersion(final String invocationLogAppId,final String invocationLogFunction) {
            ceshiData(invocationLogAppId,invocationLogFunction, "nativeGetLocalVersion");
        }


        /**关闭当前页面*/
        @JavascriptInterface
        public void closeCurrentPage() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }


        /**手机定位坐标*/
        @JavascriptInterface
        public void getLocation() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
                    onLoctionCoordinate();
                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }



        /**进入扫描二维码页面*/
        @JavascriptInterface
        public void ScanQRCode() {


            deliver.post(new Runnable() {
                @Override
                public void run() {
                    qrPermission();
                    if (allowedScan){
                        onScanQR();
                    }else {
                        Toast.makeText(getApplicationContext(),"请允许权限后尝试",Toast.LENGTH_SHORT).show();
                        qrPermission();
                    }


                }
            });

        }
    }
    String ratios = null;
    private void ceshiDataNativeGetPicture(String appid, String function, String nativeGetPicture, final String ratio) {
        ratios = ratio;
        String username = (String) SPUtils.get(MyApp.getInstance(), "personName", "");
        String userId  = null;
        try {
            userId = AesEncryptUtile.encrypt(username+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
            OkGo.<String>post(HOME_URL+functionInvocationLogUrl)
                    .params("invocationLogAppId",appid)
                    .params("invocationLogMember",userId)
                    .params("invocationLogFunction",function)
                    .params("domainName",appUrl)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            Log.e("js调用扫码",response.body());

                            AppOrthBean appOrthBean = JsonUtil.parseJson(response.body(),AppOrthBean.class);
                            boolean success = appOrthBean.getSuccess();
                            if(success == true){
                                deliver.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialogss(ratio);
                                    }
                                });
                            }else {
                                ToastUtils.showToast(InfoDetailsActivity.this,"没有使用该功能的权限!");
                                /*deliver.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onLoctionCoordinate();
                                    }
                                });*/
                            }
                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("s",response.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AlertDialog.Builder builder;
    private void dialogss(final String ratio) {



        final String items[] = {"打开相机", "打开相册"};
        builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可

        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which].equals("打开相机")) {

                    photoTask();

                } else if (items[which].equals("打开相册")) {
                    getAlbum(Float.parseFloat(ratio));
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    int REQUEST_CODE_CHOOSE = 2;
    //打开相册
    private void getAlbum(float ratio) {
        Matisse
                .from(this)

                //选择图片
                .choose(MimeType.ofImage())
                //是否只显示选择的类型的缩略图，就不会把所有图片视频都放在一起，而是需要什么展示什么
                .showSingleMediaType(true)
                //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                .capture(true)
                .captureStrategy(new CaptureStrategy(true,"PhotoPicker"))
                //有序选择图片 123456...
                .countable(true)
                //最大选择数量为9
                .maxSelectable(1)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(ratio)
                //蓝色主题
                .theme(R.style.Matisse_Zhihu)
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                //Glide加载方式
                .imageEngine(new GlideEngine())
                //请求码
                .forResult(REQUEST_CODE_CHOOSE);
    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void photoTask() {
        if (EasyPermissions.hasPermissions(InfoDetailsActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing

            testTakePhoto();
            ;//调用相机照相
        } else {//没有相应权限，获取相机权限
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "获取照相机权限",
                    RC_CAMERA_PERM, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private Uri fileUri1;
    File mPhotoFile;
    //打开系统相机
    private void testTakePhoto() {





        //指定相机拍照的存储路径
        mPhotoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        try {
            //判断文件是否存在，存在删除，不存在创建
            if (mPhotoFile.exists()){
                mPhotoFile.delete();
            }
            mPhotoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //指定intent跳转到系统相机
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>=24){
            fileUri1= FileProvider.getUriForFile(InfoDetailsActivity.this,"io.cordova.zhmh.provider",mPhotoFile);
            Log.e("获取到的url为",fileUri1.toString());
        }else {
            fileUri1 = Uri.fromFile(mPhotoFile);
        }


        //设置存储路径
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri1);
        try {
            //开启相机
            startActivityForResult(captureIntent, 5);
        } catch (Exception e) {
            Log.e("相机异常",e.getMessage());
            Toast.makeText(this, "跳转系统相机异常", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 清除 WebView 缓存
     */
    @SuppressLint("WrongConstant")
    private void toCleanWebCache() {

        if (this.mAgentWeb != null) {
            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb.clearWebCache();
            Toast.makeText(getApplicationContext(), "已清理缓存", Toast.LENGTH_SHORT).show();
            //清空所有 AgentWeb 硬盘缓存，包括 WebView 的缓存 , AgentWeb 下载的图片 ，视频 ，apk 等文件。
//            AgentWebConfig.clearDiskCache(this.getContext());
        }

    }




    private static final int Get_Imei = 100;

    @AfterPermissionGranted(Get_Imei)
    public void imeiTask(String invocationLogAppId, String invocationLogFunction) {
        ceshiData(invocationLogAppId,invocationLogFunction, "nativeGetEquipmentId");
    }


    /**
     * 二维码扫描权限申请
     * 应用内获取通知消息*/
    boolean allowedScan = false;
    private void qrPermission() {
        //同时请求多个权限
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(
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
                            allowedScan =true;

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            allowedScan =false;
                            Log.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");

                        } else {
                            allowedScan =false;
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   Log.d(TAG, permission.name + " is denied.");
                            Log.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
//                            BaseWebActivity.this.finish();
                        }
                    }
                });

    }

    /** 响铃震动提示*/
    @SuppressLint("WrongConstant")
    private void setAlarmParams() {
        //AudioManager provides access to volume and ringer mode control.
        AudioManager volMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        switch (volMgr.getRingerMode()) {//获取系统设置的铃声模式
            case AudioManager.RINGER_MODE_SILENT://静音模式，值为0，这时候不震动，不响铃
                break;
            case AudioManager.RINGER_MODE_VIBRATE://震动模式，值为1，这时候震动，不响铃
                SoundPoolUtils.vibrate(this, new long[]{1000, 500, 1000, 500}, 0);
                break;
            case AudioManager.RINGER_MODE_NORMAL://常规模式，值为2，分两种情况：1_响铃但不震动，2_响铃+震动
                SoundPoolUtils.playRing(this);
                SoundPoolUtils.vibrate(this, new long[]{500, 1000, 500, 1000}, 0);
                break;
            default:
                break;
        }
    }


    private void ceshiData(String appid, String function, final String typeName) {
        Log.e("function",function);
        String username = (String) SPUtils.get(MyApp.getInstance(), "personName", "");
        String userId  = null;
        try {
            userId = AesEncryptUtile.encrypt(username+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
            OkGo.<String>post(HOME_URL+functionInvocationLogUrl)
                    .params("invocationLogAppId",appid)
                    .params("invocationLogMember",userId)
                    .params("invocationLogFunction",function)
                    .params("domainName",appUrl)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            Log.e("js调用扫码",response.body());

                            AppOrthBean appOrthBean = JsonUtil.parseJson(response.body(),AppOrthBean.class);
                            boolean success = appOrthBean.getSuccess();
                            if(success == true){
                                String invocationLogFunction = appOrthBean.getObj().getInvocationLogFunction();
                                if(typeName.equals(invocationLogFunction)){
                                    if(invocationLogFunction.equals("nativeScanQRCode")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                cameraTask();



                                            }
                                        });
                                    }  else if(invocationLogFunction.equals("nativeGetLocation")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onLoctionCoordinate();
                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeCloseCurrentPage")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeGetEquipmentId")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onNativeGetImei();
                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeGetLocalVersion")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onNativeGetLocalVersion();
                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeOpenSystemSetting")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                intent.setData(uri);
                                                startActivityForResult(intent, SETTINGS_REQ_CODE);
                                            }
                                        });
                                    }

                                }
                            }else {
                                ToastUtils.showToast(InfoDetailsActivity.this,"没有使用该功能的权限!");
                                /*deliver.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onLoctionCoordinate();
                                    }
                                });*/
                            }
                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ceshiData2(String appid, String function, final String typeName, final String needExtraInfo) {
        Log.e("function",function);
        String username = (String) SPUtils.get(MyApp.getInstance(), "personName", "");
        String userId  = null;
        try {
            userId = AesEncryptUtile.encrypt(username+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
            OkGo.<String>post(HOME_URL+functionInvocationLogUrl)
                    .params("invocationLogAppId",appid)
                    .params("invocationLogMember",userId)
                    .params("invocationLogFunction",function)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            Log.e("js调用扫码",response.body());

                            AppOrthBean appOrthBean = JsonUtil.parseJson(response.body(),AppOrthBean.class);
                            boolean success = appOrthBean.getSuccess();
                            if(success == true){
                                String invocationLogFunction = appOrthBean.getObj().getInvocationLogFunction();
                                if(typeName.equals(invocationLogFunction)){
                                    if(invocationLogFunction.equals("nativeScanQRCode")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                cameraTask();



                                            }
                                        });
                                    }  else if(invocationLogFunction.equals("nativeGetLocation")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(needExtraInfo!= null){
                                                    if(needExtraInfo.equals("0")){
                                                        onLoctionCoordinate();
                                                    }else {
                                                        onLoctionCoordinate2();
                                                    }
                                                }else {
                                                    onLoctionCoordinate();
                                                }


                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeCloseCurrentPage")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeGetEquipmentId")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onNativeGetImei();
                                            }
                                        });
                                    }else if(invocationLogFunction.equals("nativeGetLocalVersion")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onNativeGetLocalVersion();
                                            }
                                        });
                                    }

                                }
                            }else {
                                ToastUtils.showToast(InfoDetailsActivity.this,"没有使用该功能的权限!");
                                /*deliver.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onLoctionCoordinate();
                                    }
                                });*/
                            }
                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("s",response.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void onNativeGetLocalVersion() {
        String localVersionName = getLocalVersionName(this);
        LocalVersonBean localVersonBean = new LocalVersonBean();
        localVersonBean.setSuccess(1);
        localVersonBean.setMessage("成功");
        localVersonBean.setLocalVersionName(localVersionName);
        Gson gson = new Gson();
        String s = gson.toJson(localVersonBean);
        String jsonParams = s;
        String url2 = "javascript:getAndroidVersionParams('"+jsonParams+"')";
        Log.e("url",url2);
        mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Log.e("value",value);
            }
        });
    }

    private void onNativeGetImei() {

        String imei = getIMEI(this);
        ImeiBean imeiBean = new ImeiBean();
        imeiBean.setSuccess(1);
        imeiBean.setMessage("成功");
        imeiBean.setEquipmentId(imei);
        Gson gson = new Gson();
        String s = gson.toJson(imeiBean);
        String jsonParams = s;
        String url2 = "javascript:getAndroidImeiParams('"+jsonParams+"')";
        Log.e("url",url2);
        mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Log.e("value",value);
            }
        });
    }


    private static final int RC_CAMERA_PERM = 123;

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!

            onScanQR();
            ;//调用相机照相
        } else {//没有相应权限，获取相机权限
            permissionsUtil=  PermissionsUtil
                    .with(this)
                    .requestCode(1)
                    .isDebug(true)//开启log
                    .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                    .request();
        }
    }



    /**
     * 进入扫描二维码页面
     * @param
     */
    String loginQrUrl;
    public void onScanQR() {

        QRCodeManager.getInstance()
                .with(this)
                .setReqeustType(0)
                .setRequestCode(QR_CODE)
                .scanningQRCode(new OnQRCodeListener() {
                    @Override
                    public void onCompleted(String result) {
                        //controlLog.append("\n\n(结果)" + result);
                        Log.e("QRCodeManager = ",result);
                        loginQrUrl = result;
                        mAgentWeb.getWebCreator().getWebView().loadUrl(result);

                    }

                    @Override
                    public void onError(Throwable errorMsg) {
                        //   controlLog.append("\n\n(错误)" + errorMsg.toString());
                        Log.e("QRCodeManager = = ",errorMsg.toString());
                    }

                    @Override
                    public void onCancel() {
                        //controlLog.append("\n\n(取消)扫描任务取消了");
                        Log.e("QRCodeManager = = = ","扫描任务取消了");
                    }

                    @Override
                    public void onManual(int requestCode, int resultCode, Intent data) {
                        Log.e("QRCodeManager","点击了手动添加了");
                    }
                });

    }
    private Location location;
    private LocationManager locationManager;
    private String locationProvider;       //位置提供器

    /**手机定位坐标*/
    @SuppressLint("WrongConstant")
    public void onLoctionCoordinate(){
        String latitude =(String) SPUtils.get(MyApp.getInstance(), "latitude", "");
        String longitude =(String) SPUtils.get(MyApp.getInstance(), "longitude", "");
        String direction =(String) SPUtils.get(MyApp.getInstance(), "direction", "");
        String altitude =(String) SPUtils.get(MyApp.getInstance(), "altitude", "");
        String speed =(String) SPUtils.get(MyApp.getInstance(), "speed", "");
        String locationWhere =(String) SPUtils.get(MyApp.getInstance(), "locationWhere", "");


        String countryName =(String) SPUtils.get(MyApp.getInstance(), "country", "");
        String countryCode =(String) SPUtils.get(MyApp.getInstance(), "countryCode", "");
        String province =(String) SPUtils.get(MyApp.getInstance(), "province", "");
        String city =(String) SPUtils.get(MyApp.getInstance(), "city", "");
        String cityCode =(String) SPUtils.get(MyApp.getInstance(), "cityCode", "");
        String district =(String) SPUtils.get(MyApp.getInstance(), "district", "");
        String town =(String) SPUtils.get(MyApp.getInstance(), "town", "");
        String adcode =(String) SPUtils.get(MyApp.getInstance(), "adcode", "");
        String street =(String) SPUtils.get(MyApp.getInstance(), "street", "");
        String streetNumber =(String) SPUtils.get(MyApp.getInstance(), "streetNumber", "");

        Log.e("获取坐标2",longitude);
        LocationBean locationBean = new LocationBean();
        locationBean.setSuccess(true);
        locationBean.setMessage("成功");
        locationBean.setIsBaidu("1");
        locationBean.setSignRecordEquipmentId(MobileInfoUtils.getIMEI(InfoDetailsActivity.this));
        locationBean.setLatitude(latitude+"");
        locationBean.setLongitude(longitude+"");
        locationBean.setAddress((String) SPUtils.get(MyApp.getInstance(), "addressLine", ""));
        locationBean.setAltitude(altitude);

        locationBean.setCountryName(countryName);
        locationBean.setCountryCode(countryCode);
        locationBean.setProvince(province);
        locationBean.setCity(city);
        locationBean.setCityCode(cityCode);
        locationBean.setDistrict(district);
        locationBean.setTown(town);
        locationBean.setAdcode(adcode);
        locationBean.setStreet(street);
        locationBean.setStreetNumber(streetNumber);
        Gson gson = new Gson();
        String s = gson.toJson(locationBean);
        String jsonParams = s;
        String url2 = "javascript:getAndroidParams('"+jsonParams+"')";
        Log.e("url",url2);
        mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Log.e("value",value);
            }
        });


    }


    @SuppressLint("WrongConstant")
    public void onLoctionCoordinate2(){
        String latitude =(String) SPUtils.get(MyApp.getInstance(), "latitude", "");
        Log.e("获取坐标",latitude);

        String longitude =(String) SPUtils.get(MyApp.getInstance(), "longitude", "");
        String direction =(String) SPUtils.get(MyApp.getInstance(), "direction", "");
        String altitude =(String) SPUtils.get(MyApp.getInstance(), "altitude", "");
        String speed =(String) SPUtils.get(MyApp.getInstance(), "speed", "");
        String locationWhere =(String) SPUtils.get(MyApp.getInstance(), "locationWhere", "");
        Log.e("获取坐标2",longitude);

        String countryName =(String) SPUtils.get(MyApp.getInstance(), "country", "");
        String countryCode =(String) SPUtils.get(MyApp.getInstance(), "countryCode", "");
        String province =(String) SPUtils.get(MyApp.getInstance(), "province", "");
        String city =(String) SPUtils.get(MyApp.getInstance(), "city", "");
        String cityCode =(String) SPUtils.get(MyApp.getInstance(), "cityCode", "");
        String district =(String) SPUtils.get(MyApp.getInstance(), "district", "");
        String town =(String) SPUtils.get(MyApp.getInstance(), "town", "");
        String adcode =(String) SPUtils.get(MyApp.getInstance(), "adcode", "");
        String street =(String) SPUtils.get(MyApp.getInstance(), "street", "");
        String streetNumber =(String) SPUtils.get(MyApp.getInstance(), "streetNumber", "");


        LocationBean2 locationBean = new LocationBean2();
        locationBean.setSuccess(true);
        locationBean.setMessage("成功");
        locationBean.setIsBaidu("1");
        locationBean.setAltitude(altitude);
        locationBean.setCourse(direction);
        if(locationWhere.equals("1")){
            locationBean.setChina(true);
        }else {
            locationBean.setChina(false);
        }
        locationBean.setSpeed(speed);
        locationBean.setSignRecordEquipmentId(MobileInfoUtils.getIMEI(InfoDetailsActivity.this));
        //locationBean.setLatitude(location.getLatitude()+"");
        locationBean.setLatitude(latitude+"");
        //locationBean.setLongitude(location.getLongitude()+"");
        locationBean.setLongitude(longitude+"");
        locationBean.setAddress((String) SPUtils.get(MyApp.getInstance(), "addressLine", ""));

        locationBean.setCountryName(countryName);
        locationBean.setCityCode(countryCode);
        locationBean.setProvince(province);
        locationBean.setCity(city);
        locationBean.setCityCode(cityCode);
        locationBean.setDistrict(district);
        locationBean.setTown(town);
        locationBean.setAdcode(adcode);
        locationBean.setStreet(street);
        locationBean.setStreetNumber(streetNumber);

        Gson gson = new Gson();
        String s = gson.toJson(locationBean);
        String jsonParams = s;
        String url2 = "javascript:getAndroidParams('"+jsonParams+"')";
        Log.e("url",url2);
        mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Log.e("value",value);
            }
        });


    }
    private void getMyLocation() {

        LocationClientOption option = new LocationClientOption();

        //设置坐标类型
        option.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        //设置是否打开gps进行定位
        option.setOpenGps(true);
        //设置扫描间隔为1秒
        option.setScanSpan(300000);
        //传入设置好的信息
        mLocationClient.setLocOption(option);

        mLocationClient.start();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //需要调用onRequestPermissionsResult
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        permissionsUtil.onActivityResult(requestCode, resultCode, intent);
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE_CHOOSE) {
            if (resultCode == RESULT_OK) {//打开相册返回
                //图片路径 同样视频地址也是这个 根据requestCode
                List<String> pathList = Matisse.obtainPathResult(intent);

                for (int i = 0; i < pathList.size(); i++) {
                    String albumUri = pathList.get(i);

                    File file = new File(albumUri);

                    //String image = getFileBase64(file);
                    Bitmap bitmap = BitmapFactory.decodeFile(albumUri);
                    double v = 500 * Double.parseDouble(ratios);

                    Bitmap scaledBitmap = BitmapUtils.compressByWidth(bitmap, (int)v);
                    String image = BitmapUtils.bitmapToBase64(scaledBitmap);

                    NaturePicBean naturePicBean = new NaturePicBean();
                    naturePicBean.setSuccess(true);
                    naturePicBean.setMessage("成功");

                    naturePicBean.setImage(URLEncoder.encode( "data:image/" + getFileExtension(file) + ";base64," +image));


                    Gson gson = new Gson();
                    String s = gson.toJson(naturePicBean);
                    String jsonParams = s;

                    String url2 = "javascript:getNativeGetPicture('"+jsonParams+"')";
                    Log.e("url",url2);
                    mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //此处为 js 返回的结果
                            Log.e("value",value);
                        }
                    });
                }
            }
        }



        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {//拍照返回

//                Bitmap bitmap=BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
////                    Bitmap bitmap =   BitmapFactory.decodeStream(this.getContentResolver().openInputStream(fileUri1));
//                File file = saveBitmapFile2(bitmap);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String extension = mPhotoFile.getAbsolutePath();
                        extension = extension.substring(extension.lastIndexOf("."));
                        extension = extension.replace(".","");
                        Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());

                        double v = 500 * Double.parseDouble(ratios);

                        Bitmap scaledBitmap = BitmapUtils.compressByWidth(bitmap, (int)v);

                        String image = BitmapUtils.bitmapToBase64(scaledBitmap);
                        NaturePicBean naturePicBean = new NaturePicBean();
                        naturePicBean.setSuccess(true);
                        naturePicBean.setMessage("成功");
                        naturePicBean.setImage( URLEncoder.encode( "data:image/" + extension + ";base64," +image));
                        Gson gson = new Gson();
                        String s = gson.toJson(naturePicBean);
                        String jsonParams = s;

                        String url2 = "javascript:getNativeGetPicture('"+jsonParams+"')";
                        Log.e("url",url2);
                        mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                                Log.e("value",value);
                            }
                        });
                    }
                }).run();

                //                cutImage(fileUri1);
            }
        }
    }


    public String getFileExtension(File file) {
        String extension = "";
        try {
            if (file != null && file.exists()) {
                String name = file.getName();//a.jpg
                extension = name.substring(name.lastIndexOf("."));
                extension = extension.replace(".","");
            }
        } catch (Exception e) {
            extension = "";
        }
        return extension;
    }

    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        Log.e("权限获取回调","onPermissionsGranted");
        mLocationClient.stop();

        if(requestCode == 0){
            getMyLocation();
        }else if(requestCode == 1){
            onScanQR();
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {
        Log.e("权限被拒绝回调","onPermissionsDenied");
        mLocationClient.stop();
        if(requestCode == 0){
            getMyLocation();
        }
    }


    private int imageid = 0;
    /**
     * 验证人脸
     * @param
     * @param invocationLogAppId
     * @param invocationLogFunction
     * @param
     */
    private void ceshiFaceData(String invocationLogAppId, String invocationLogFunction, final String typeName) {

        String username = (String) SPUtils.get(MyApp.getInstance(), "personName", "");
        String userId  = null;
        try {
            userId = AesEncryptUtile.encrypt(username+ "_"+ Calendar.getInstance().getTimeInMillis(),key);
            OkGo.<String>post(HOME_URL+functionInvocationLogUrl)
                    .params("invocationLogAppId",invocationLogAppId)
                    .params("invocationLogMember",userId)
                    .params("invocationLogFunction",invocationLogFunction)
                    .params("domainName",appUrl)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            Log.e("js调用扫码",response.body());

                            AppOrthBean appOrthBean = JsonUtil.parseJson(response.body(),AppOrthBean.class);
                            boolean success = appOrthBean.getSuccess();
                            if(success == true){
                                String invocationLogFunction = appOrthBean.getObj().getInvocationLogFunction();
                                if(typeName.equals(invocationLogFunction)){
                                    if(invocationLogFunction.equals("nativeVerifyUserAndFace")){
                                        deliver.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                SPUtils.put(InfoDetailsActivity.this,"bitmap","");
                                                Intent intent = new Intent(InfoDetailsActivity.this,FaceYiQingActivity.class);
                                                startActivityForResult(intent,99);
                                                imageid = 0;
                                            }
                                        });
                                    }

                                }
                            }else {
                                ToastUtils.showToast(InfoDetailsActivity.this,"没有使用该功能的权限!");
                                /*deliver.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        onLoctionCoordinate();
                                    }
                                });*/
                            }
                        }
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("s",response.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals("facereYiQing")){
                Log.e("imageid",imageid+"");
                String FaceActivity = intent.getStringExtra("FaceActivity");
                if(imageid == 0){
                    if(FaceActivity != null){
                        imageid = 1;

                        String s = (String)SPUtils.get(InfoDetailsActivity.this, "bitmap", "");
                        String imei = (String) SPUtils.get(InfoDetailsActivity.this, "imei", "");
                        String username = (String) SPUtils.get(InfoDetailsActivity.this, "phone", "");

                        try {
                            //String secret  = AesEncryptUtile.encrypt(Calendar.getInstance().getTimeInMillis()+ "_"+"123456",key);
                            String secret = AesEncryptUtile.encrypt(username,key);
                            OkGo.<String>post(UrlRes.HOME2_URL+"/authentication/api/face/distinguishFace")
                                    .params( "openId",AesEncryptUtile.openid)
                                    .params( "memberId",secret)
                                    .params( "img",s )
                                    .params( "equipmentId",imei)
                                    .execute(new StringCallback(){

                                        @Override
                                        public void onStart(Request<String, ? extends Request> request) {
                                            super.onStart(request);
                                            //ViewUtils.createLoadingDialog2(LoginActivity2.this,true,"人脸识别中");

                                        }

                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            SPUtils.put(getApplicationContext(),"isloading2","");
                                            ViewUtils.cancelLoadingDialog();
                                            Log.e("tag",response.body());
                                            BaseBean baseBean = JsonUtil.parseJson(response.body(),BaseBean.class);
                                            boolean success = baseBean.isSuccess();

                                            LocalFaceBean localVersonBean = new LocalFaceBean();
                                            localVersonBean.setSuccess(success);
                                            localVersonBean.setMessage(baseBean.getMsg());
                                            Gson gson = new Gson();
                                            String s = gson.toJson(localVersonBean);
                                            String jsonParams = s;
                                            String url2 = "javascript:getFaceAndroidParams('"+jsonParams+"')";
                                            Log.e("url",url2);
                                            mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
                                                @Override
                                                public void onReceiveValue(String value) {
                                                    //此处为 js 返回的结果
                                                    Log.e("value",value);
                                                }
                                            });



                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);
                                            SPUtils.put(getApplicationContext(),"isloading2","");
                                            LocalFaceBean localVersonBean = new LocalFaceBean();
                                            localVersonBean.setSuccess(false);
                                            localVersonBean.setMessage("当前网络异常，识别失败");
                                            Gson gson = new Gson();
                                            String s = gson.toJson(localVersonBean);
                                            String jsonParams = s;
                                            String url2 = "javascript:getFaceAndroidParams('"+jsonParams+"')";
                                            mAgentWeb.getWebCreator().getWebView().evaluateJavascript(url2, new ValueCallback<String>() {
                                                @Override
                                                public void onReceiveValue(String value) {
                                                    //此处为 js 返回的结果
                                                    Log.e("value",value);
                                                }
                                            });
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

    private LinearLayout mLinearLayout;
    private MyDialog m_Dialog;
    private int tag = 0;
    private void logOut(final String urldown) {
        m_Dialog = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_down, null);
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

                tag = 1;
                mAgentWeb = AgentWeb.with(InfoDetailsActivity.this)
                        .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                        .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                        .setWebChromeClient(mWebChromeClient)
                        .setWebViewClient(mWebViewClient2)
                        .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                        .setWebLayout(new WebLayout4(InfoDetailsActivity.this))
                        .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                        .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                        .setAgentWebWebSettings( getSettings2())//设置 IAgentWebSettings。
                        .createAgentWeb()
                        .ready()
                        .go(urldown);


            }
        });
    }

    @SuppressLint("WrongConstant")
    private WebViewClient mWebViewClient2 = new WebViewClient() {
        @Override
        public void onPageCommitVisible(WebView view, String url) {

            super.onPageCommitVisible(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                handler.proceed(); // 接受所有网站的证书
            }


        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            boolean b = mAgentWeb.getWebCreator().getWebView().canGoBack();



            WebSettings webSettings = view.getSettings();
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
            webSettings.setJavaScriptEnabled(true);//支持js
            webSettings.setBuiltInZoomControls(true); // 显示放大缩小
            webSettings.setSupportZoom(true); // 可以缩放
            webSettings.setUseWideViewPort(true);  //为图片添加放大缩小功能


        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }



            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username","")) || tgc.equals("")){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }




            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {



            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://kys.zzuli.edu.cn","CASTGC="+tgc,getApplication());


        }


    };

    public IAgentWebSettings getSettings2() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;


            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }

            @Override
            public WebListenerManager setDownloader(WebView webView, DownloadListener downloadListener) {

                return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create((Activity) webView.getContext(),
                                        webView,
                                        mDownloadListenerAdapter,
                                        mDownloadListenerAdapter,
                                        this.mAgentWeb.getPermissionInterceptor()));
            }


        };
    }

}
