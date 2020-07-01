package io.cordova.zhmh.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cxz.swipelibrary.SwipeBackActivity;
import com.google.gson.Gson;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.activity.LoginActivity;
import io.cordova.zhmh.bean.BaseBean;
import io.cordova.zhmh.bean.DownLoadBean;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.CookieUtils;
import io.cordova.zhmh.utils.DensityUtil;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.ScreenSizeUtils;
import io.cordova.zhmh.utils.SoundPoolUtils;
import io.cordova.zhmh.utils.StringUtils;
import io.cordova.zhmh.utils.T;
import io.cordova.zhmh.utils.ViewUtils;
import io.cordova.zhmh.widget.CLWebView;
import io.cordova.zhmh.widget.MyDialog;
import io.cordova.zhmh.widget.TestWebView;

import static io.cordova.zhmh.utils.AesEncryptUtile.key;


/**
 * Created by cenxiaozhong on 2017/5/26.
 * <p>
 * source code  https://github.com/Justson/AgentWeb
 */

@SuppressLint("Registered")
public class BaseWebActivity extends SwipeBackActivity {
    protected AgentWeb mAgentWeb;
    @BindView(R.id.webView)
    TestWebView webView;

    @BindView(R.id.layout_close)
    RelativeLayout rvClose;

     @BindView(R.id.tv_title)
    TextView mTitleTextView;
    @BindView(R.id.rl_no)
    RelativeLayout rl_no;

    @BindView(R.id.rb_sc)
    ImageView rbSc;

    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private AlertDialog mAlertDialog;
    String appServiceUrl, tgc,appId,search,oaMsg;
    private String time;
    boolean isFirst = true;
    String appName;
    private int flag = 0;
    private int tag = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        rvClose.setVisibility(View.VISIBLE);

        tgc = (String) SPUtils.get(getApplicationContext(), "TGC", "");
        appServiceUrl = getIntent().getStringExtra("appUrl");
        appId = getIntent().getStringExtra("appId");
        search = getIntent().getStringExtra("search");//微应用地址'
        oaMsg = getIntent().getStringExtra("oaMsg");//oa 消息

        appName = getIntent().getStringExtra("appName");

        mTitleTextView.setText(appName);
        if (!StringUtils.isEmpty(oaMsg)){
            if (!appServiceUrl.contains("fromnewcas=Y")){
                appServiceUrl = appServiceUrl + "&fromnewcas=Y";
            }
        }
        urldown = appServiceUrl;
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .createAgentWeb()
                .ready()
                .go(appServiceUrl);
        Log.e("appServiceUrl",appServiceUrl);
        mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        mAgentWeb.getJsInterfaceHolder().addJavaObject("android",new AndroidInterface());

        boolean b = mAgentWeb.getWebCreator().getWebView().canGoBack();
        if(b){
            rvClose.setVisibility(View.VISIBLE);
        }else {
            rvClose.setVisibility(View.GONE);
        }

    }



    BaseBean appTime;
    private void networkAppStatTime() {
//ToDO
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.APP_Time)
                .params( "responseTime",time )
                .params( "responseAppId",appId)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                            Log.e("Tag",response.body());
                            appTime = JSON.parseObject(response.body(),BaseBean.class);
                            if (appTime.isSuccess()) {
                                isFirst = false;
                            }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        isFirst = true;
                    }
                });


    }

    BaseBean baseBean;
    /**
     * 请求检测是否收藏页面
     * */
    private void netWorkIsCollection() {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Query_IsCollection)
                .params( "version","1.0" )
                .params( "collectionAppId",appId )
                .params( "userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                        Log.e("tag",response.body());
                        baseBean = JSON.parseObject(response.body(), BaseBean.class);
                        if (baseBean.isSuccess()){
                            rbSc.setBackgroundResource(R.mipmap.sc_hover_icon);
                            flag = 1;
                        }else {
                            rbSc.setBackgroundResource(R.mipmap.sc_icon);
                            flag = 0;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });

    }


    /**请求收藏*/
    private void networkCollection() {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Add_Collection)
                .params( "version","1.0" )
                .params( "collectionAppId",appId )
                .params( "userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                        Log.e("tag",response.body());
                        baseBean = JSON.parseObject(response.body(), BaseBean.class);
                        if (baseBean.isSuccess()){
                            rbSc.setBackgroundResource(R.mipmap.sc_hover_icon);
                            flag = 1;
                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                            Intent intent = new Intent();
                            intent.putExtra("refreshService","dongtai");
                            intent.setAction("refresh2");
                            sendBroadcast(intent);
                        }else {
                            rbSc.setBackgroundResource(R.mipmap.sc_icon);
                            flag = 0;
                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        rbSc.setBackgroundResource(R.mipmap.sc_icon);
                    }
                });
    }

    /**取消收藏*/
    private void cancelCollection() {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Cancel_Collection)
                .params( "version","1.0" )
                .params( "collectionAppId",appId )
                .params( "userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                        Log.e("tag",response.body());
                        baseBean = JSON.parseObject(response.body(), BaseBean.class);
                        if (baseBean.isSuccess()){
                            rbSc.setBackgroundResource(R.mipmap.sc_icon);
                            flag = 0;
                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                            Intent intent = new Intent();
                            intent.putExtra("refreshService","dongtai");
                            intent.setAction("refresh2");
                            sendBroadcast(intent);
                        }else {
                            rbSc.setBackgroundResource(R.mipmap.sc_hover_icon);
                            flag = 1;
                            T.showShort(MyApp.getInstance(),baseBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        rbSc.setBackgroundResource(R.mipmap.sc_hover_icon);
                    }
                });

    }
    /**
     * IEventHandler 处理WebView相关返回事件
     */
    @OnClick({R.id.iv_back, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            if (!mAgentWeb.back()){
                BaseWebActivity.this.finish();
            }
                break;
            case R.id.iv_close:
                BaseWebActivity.this.finish();
                break;
        }
    }
    long start;
    long end;
    String urldown = "";
    String sBegin;//开始时间戳
    String s1;//开始时间
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            boolean b = mAgentWeb.getWebCreator().getWebView().canGoBack();
            if(b){
                rvClose.setVisibility(View.VISIBLE);
            }else {
                rvClose.setVisibility(View.GONE);
            }
            Log.i("userAgent4",  view.getSettings().getUserAgentString());
            WebSettings webSettings = view.getSettings();
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
            webSettings.setJavaScriptEnabled(true);//支持js
            webSettings.setBuiltInZoomControls(true); // 显示放大缩小
            webSettings.setSupportZoom(true); // 可以缩放
            webSettings.setUseWideViewPort(true);  //为图片添加放大缩小功能
            Log.e("myurl--Base","结束了");

            sBegin = Calendar.getInstance().getTimeInMillis() + "";

            s1 = stringToDate(sBegin);
        }

        //        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }


            if (url.contains("http://platform.gilight.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }
            urldown = "";
            urldown =url;
            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://platform.gilight.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }
            urldown = "";
            urldown =url;
            Log.e("urldown",urldown);
            webView.loadUrl(urldown);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    Log.e("myurl",url);
                    return true;
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    Log.e("myurl",request.getUrl().toString());
                    return super.shouldOverrideUrlLoading(view, request);

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    Log.e("myurl","结束了");
                    Log.e("myurl",url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Log.e("myurl","开始了");
                    Log.e("myurl",url);
                }
            });

            webView.setLoadFinishListener(new CLWebView.LoadFinishListener() {
                @Override
                public void onLoadFinish(WebView webView) {
                    Log.e("urldown",urldown);


                    upLoadWebInfo();
                    String url1 = webView.getUrl();
                    urldown = url1;
                    sBegin = Calendar.getInstance().getTimeInMillis() + "";

                    s1 = stringToDate(sBegin);
                    Log.e("urldown",urldown);
                    Log.e("myurl","加载结束了");
                    Log.e("myurl",url1);
                    String downLoadType = (String) SPUtils.get(BaseWebActivity.this, "downLoadType", "");
                    DownLoadBean downLoadBean = JsonUtil.parseJson(downLoadType,DownLoadBean.class);
                    List<String> downLoadTypeList = downLoadBean.getString();
                    for (int i = 0; i < downLoadTypeList.size(); i++) {
                        if(urldown.contains(downLoadTypeList.get(i))){
                            Log.e("tag",downLoadTypeList.get(i));
                            logOut(urldown);
                            break;
                        }else {

                        }
                    }
                }
            });


            Log.e("url",url);



            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://platform.gilight.cn","CASTGC="+tgc,getApplication());
            if (!StringUtils.isEmpty(appId)){
                start =  Calendar.getInstance().getTimeInMillis() ;
                Log.i("Info", "start:  " + start );
            }
            //imgReset(view);
            Log.e("myurl--Base","开始了");




        }

    };




    /**
     * @Description: String类型毫秒数转换成日期
     * [@param](http://my.oschina.net/param) lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String stringToDate(String lo){
        long time = Long.parseLong(lo);
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }



    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //   do you work

            if (newProgress == 100 && start > 0){
                 end =   Calendar.getInstance().getTimeInMillis();
                time =(end - start) +"";
            }

            if (!StringUtils.isEmpty(time) && !StringUtils.isEmpty(appId) && isFirst){
                networkAppStatTime();
            }
            Log.i("Info", "onProgress:" + newProgress);
            Log.i("Info", "onProgress:" +  time);

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }
            if(appName != null){
                mTitleTextView.setText(appName);
            }
        }


    };
    protected PermissionInterceptor mPermissionInterceptor = new PermissionInterceptor() {

        /**
         * PermissionInterceptor 能达到 url1 允许授权， url2 拒绝授权的效果。
         * @param url
         * @param permissions
         * @param action
         * @return true 该Url对应页面请求权限进行拦截 ，false 表示不拦截。
         */
        @Override
        public boolean intercept(String url, String[] permissions, String action) {
            Log.i("请求权限进行拦截 ", "mUrl:" + url + "  permission:" + mGson.toJson(permissions) + " action:" + action);
            return false;
        }
    };

    public String getUrl() {
        return "http://iapp.zzuli.edu.cn/portal/portal-app/app-5/index.html?isAPP=true";
    }

    private Gson mGson = new Gson();



    protected DownloadListenerAdapter mDownloadListenerAdapter = new DownloadListenerAdapter() {

        @Override
        public boolean onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, AgentWebDownloader.Extra extra) {
            Log.i("下载链接", "onStart:" + url);
            ViewUtils.createLoadingDialog(BaseWebActivity.this);
            extra.setOpenBreakPointDownload(true) // 是否开启断点续传
                    .setIcon(R.drawable.ic_file_download_black_24dp) //下载通知的icon
                    .setConnectTimeOut(6000) // 连接最大时长
                    .setBlockMaxTime(10 * 60 * 1000)  // 以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常
                    .setDownloadTimeOut(Long.MAX_VALUE) // 下载最大时长
                    .setParallelDownload(false)  // 串行下载更节省资源哦
                    .setEnableIndicator(false)  // false 关闭进度通知
                    //.addHeader("Cookie", "xx") // 自定义请求头
                    .setAutoOpen(true) // 下载完成自动打开
                    .setForceDownload(true); // 强制下载，不管网络网络类型

            return false;
        }

        /*@Override
        public void onProgress(String url, long loaded, long length, long usedTime) {
            int mProgress = (int) ((loaded) / Float.valueOf(length) * 100);
            Log.i("下载进度", "onProgress:" + mProgress);
            super.onProgress(url, loaded, length, usedTime);
        }*/

        @Override
        public boolean onResult(String path, String url, Throwable throwable) {
            ViewUtils.cancelLoadingDialog();
            if (null == throwable) { //下载成功
                //do you work
                Log.e("下载失败", path);
                Log.e("下载失败", url);

                Uri shareFileUrl = FileUtil.getFileUri(getApplicationContext(), null, new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(BaseWebActivity.this)
                        .setContentType(ShareContentType.FILE)
                        .setShareFileUri(shareFileUrl)
                        .setTitle("Share File")
                        .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
                        .build()
                        .shareBySystem();

            } else {//下载成功
                Log.e("path", path);

                Uri shareFileUrl = FileUtil.getFileUri(getApplicationContext(), null, new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(BaseWebActivity.this)
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

    /**
     * @return IAgentWebSettings
     */
    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;


            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
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

              /*  return super.setDownloader(webView,
                        DefaultDownloadImpl
                                .create((Activity) webView.getContext(),
                                        webView,
                                        mDownloadListenerAdapter,
                                        mDownloadListenerAdapter,
                                        this.mAgentWeb.getPermissionInterceptor()));*/


              if(tag == 0){
                  Log.e("tag---------","111111");
                  return super.setDownloader(webView,downloadListener);

              }else {
                  tag = 0;
                  Log.e("tag---------","222222");
                  return super.setDownloader(webView,
                          DefaultDownloadImpl
                                  .create((Activity) webView.getContext(),
                                          webView,
                                          mDownloadListenerAdapter,
                                          null,
                                          this.mAgentWeb.getPermissionInterceptor()));
              }

                //return super.setDownloader(webView,downloadListener);
            }


        };
    }

    private static final int REQUEST_SHARE_FILE_CODE = 120;

    private MyDialog m_Dialog;
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

                mAgentWeb = AgentWeb.with(BaseWebActivity.this)
                        .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                        .useDefaultIndicator(-1, 3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                        .setWebChromeClient(mWebChromeClient)
                        .setWebViewClient(mWebViewClient)
                        .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                        .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                        .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                        .setWebLayout(new WebLayout(BaseWebActivity.this))
                        .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                        .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                        .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                        .createAgentWeb()
                        .ready()
                        .go(urldown);





            }
        });
    }

    /**
     * 清除 WebView 缓存
     */
    private void toCleanWebCache() {

        if (this.mAgentWeb != null) {
            //清理所有跟WebView相关的缓存 ，数据库， 历史记录 等。
            this.mAgentWeb.clearWebCache();
            Toast.makeText(getApplicationContext(), "已清理缓存", Toast.LENGTH_SHORT).show();

        }

    }

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
            SoundPoolUtils.virateCancle(BaseWebActivity.this);

        }
    }
    /**Js调用内部类*/
    public class AndroidInterface {
        private Handler deliver = new Handler(Looper.getMainLooper());

        /**震动响铃*/
        @JavascriptInterface
        public void playSoundAndVibration() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
//                    Log.i("Info", "main Thread:" + Thread.currentThread());
//                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
//                    handler.sendEmptyMessageDelayed(0, 5000);
                    setAlarmParams();
                    handler.sendEmptyMessageDelayed(0, 1100);
                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }

        /**清理缓存*/
        @JavascriptInterface
        public void cleanUpAppCache() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
//                    Log.i("Info", "main Thread:" + Thread.currentThread());
//                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
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
//                    Log.i("Info", "main Thread:" + Thread.currentThread());
//                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
                    if (!mAgentWeb.back()){
                        finish();
                    }else {
                        mAgentWeb.back();
                    }

                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }
        /**关闭当前页面*/
        @JavascriptInterface
        public void closeCurrentPage() {
            deliver.post(new Runnable() {
                @Override
                public void run() {
//                    Log.i("Info", "main Thread:" + Thread.currentThread());
//                    Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
                   finish();
                }
            });
            Log.i("Info", "Thread:" + Thread.currentThread());
        }

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


    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();


        upLoadWebInfo();


    }

    /**
     * 结束时间
     */
    private void upLoadWebInfo() {
        String s = Calendar.getInstance().getTimeInMillis() + "";
        long sCha = Long.parseLong(s) - Long.parseLong(sBegin);
        String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
        try {
            String decrypt = AesEncryptUtile.decrypt(userId, key);
            Map<String,String> map=new HashMap<String,String>();
            map.put("portalReadingAccessEquipmentId",(String) SPUtils.get(MyApp.getInstance(),"imei",""));
            map.put("portalReadingAccessMemberId",decrypt);
            map.put("portalReadingAccessLongitude",(String) SPUtils.get(MyApp.getInstance(),"longitude",""));
            map.put("portalReadingAccessLatitude",(String) SPUtils.get(MyApp.getInstance(),"latitude",""));
            map.put("portalReadingAccessAddress",(String) SPUtils.get(MyApp.getInstance(),"addressLine",""));
            map.put("portalReadingAccessTime",s1);
            map.put("portalReadingAccessUrl",urldown);
            map.put("portalReadingAccessReadTime",sCha/1000+"");
            JSONObject json =new JSONObject(map);
            String s3 = json.toString();
            Log.e("content",json.toString());
            String content = AesEncryptUtile.encrypt(s3, key);

            OkGo.<String>post("http://192.168.30.30:8081/portal/mobile/portalReadingAccess/addPortalReadingAccess")
                    .params("json", content)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("s",response.toString());
                           /* FaceBean2 faceBean2 = JsonUtil.parseJson(response.toString(),FaceBean2.class);

                            boolean success = faceBean2.getSuccess();
                            if(success == true){

                            }*/
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
    }




    private class DownloadTask extends AsyncTask<String, Void, Void> {
        // 传递两个参数：URL 和 目标路径
        private String url;
        private String destPath;

        @Override
        protected void onPreExecute() {
            ViewUtils.createLoadingDialog(BaseWebActivity.this);
        }

        @Override
        protected Void doInBackground(String... params) {

            url = params[0];
            destPath = params[1];
            OutputStream out = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                InputStream in = urlConnection.getInputStream();
                out = new FileOutputStream(params[1]);
                byte[] buffer = new byte[10 * 1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                in.close();
            } catch (IOException e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {

                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ViewUtils.cancelLoadingDialog();
            Intent handlerIntent = new Intent(Intent.ACTION_VIEW);
            String mimeType = getMIMEType(url);
            Uri uri = Uri.fromFile(new File(destPath));
            //log.debug("mimiType:{}, uri:{}", mimeType, uri);
            handlerIntent.setDataAndType(uri, mimeType);
            startActivity(handlerIntent);
            new Share2.Builder(BaseWebActivity.this)
                    .setContentType(ShareContentType.FILE)
                    .setShareFileUri(uri)
                    .setTitle("Share File")
                    .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
                    .build()
                    .shareBySystem();
        }
    }

    private String getMIMEType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        //log.debug("extension:{}", extension);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }



}
