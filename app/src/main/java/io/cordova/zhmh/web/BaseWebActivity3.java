package io.cordova.zhmh.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.just.agentweb.download.DownloadingService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.activity.LoginActivity;
import io.cordova.zhmh.bean.BaseBean;
import io.cordova.zhmh.utils.CookieUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.NetStateUtils;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.SoundPoolUtils;
import io.cordova.zhmh.utils.StringUtils;
import io.cordova.zhmh.utils.ToastUtils;


/**
 * Created by cenxiaozhong on 2017/5/26.
 * <p>
 * source code  https://github.com/Justson/AgentWeb
 */

@SuppressLint("Registered")
public class BaseWebActivity3 extends AppCompatActivity {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.layout_close)
    RelativeLayout rvClose;
    @BindView(R.id.layout_back)
    RelativeLayout layout_back;

    @BindView(R.id.rb_sc)
    ImageView rbSc;
     @BindView(R.id.tv_title)
    TextView mTitleTextView;
    @BindView(R.id.rl_no)
    RelativeLayout rl_no;

    @BindView(R.id.rl_load)
    RelativeLayout rl_load;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private AlertDialog mAlertDialog;
    String appServiceUrl, tgc,appId,search,oaMsg;
    private String time;
    boolean isFirst = true;
    private int flag = 0;
    String cookie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        ButterKnife.bind(this);
        webView.setVisibility(View.VISIBLE);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        rvClose.setVisibility(View.GONE);

        tgc = (String) SPUtils.get(getApplicationContext(), "TGC", "");
        appServiceUrl = getIntent().getStringExtra("appUrl");
        appId = getIntent().getStringExtra("appId");
        search = getIntent().getStringExtra("search");//微应用地址'
        oaMsg = getIntent().getStringExtra("oaMsg");//oa 消息
        String appName = getIntent().getStringExtra("appName");

        mTitleTextView.setText(appName);

        if (!StringUtils.isEmpty(oaMsg)){
            if (!appServiceUrl.contains("fromnewcas=Y")){
                appServiceUrl = appServiceUrl + "&fromnewcas=Y";
            }
        }

        checkNetState();




        rl_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNetState();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(mWebViewClient2);
        webView.loadUrl(appServiceUrl);
        webView.addJavascriptInterface(new AndroidInterface(), "backToLastUrl");//AndroidtoJS类对象映射到js的test对象



        if (StringUtils.isEmpty(appId)){
            rbSc.setVisibility(View.GONE);
        }else {
            rbSc.setVisibility(View.VISIBLE);
            netWorkIsCollection();
            initListener();
        }






    }

    private void checkNetState() {
        if (!NetStateUtils.isConnect(this)) {
            ToastUtils.showToast(this,"网络异常!");
            rl_no.setVisibility(View.VISIBLE);
        }else {
            rl_no.setVisibility(View.GONE);
            webView.loadUrl(appServiceUrl);
            if (StringUtils.isEmpty(appId)){
                rbSc.setVisibility(View.GONE);
            }else {
                rbSc.setVisibility(View.VISIBLE);
                netWorkIsCollection();
                initListener();
            }
        }
    }


    BaseBean appTime;
    private void networkAppStatTime() {
//ToDO
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.APP_Time)
                .tag(this)
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
                .tag(this)
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

    private void initListener() {
        rbSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0){
                    //请求收藏
                    networkCollection();
                }else {
                    //取消收藏
                    cancelCollection();
                }
            }
        });

    }

    /**请求收藏*/
    private void networkCollection() {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Add_Collection)
                .tag(this)
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
                            toastShow(baseBean.getMsg());
                            flag = 1;
                            Intent intent = new Intent();
                            intent.putExtra("refreshService","dongtai");
                            intent.setAction("refresh2");
                            sendBroadcast(intent);
                        }else {
                            rbSc.setBackgroundResource(R.mipmap.sc_icon);
                            toastShow(baseBean.getMsg());
                            flag = 0;
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
                .tag(this)
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
                            toastShow(baseBean.getMsg());
                            flag = 0;
                            Intent intent = new Intent();
                            intent.putExtra("refreshService","dongtai");
                            intent.setAction("refresh2");
                            sendBroadcast(intent);
                        }else {
                            rbSc.setBackgroundResource(R.mipmap.sc_hover_icon);
                            toastShow(baseBean.getMsg());
                            flag = 1;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        toastStop();
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
                if (webView.canGoBack()) {
                    webView.goBack();

                }else {
                    finish();
                }
                break;
            case R.id.iv_close:
                BaseWebActivity3.this.finish();
                break;
        }
    }
    long start;
    long end;
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }


            if (url.contains("http://platform.gilight.cn/cas/login")) {
                if (StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }
            return false;
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.contains("http://platform.gilight.cn/cas/login")) {
                if (StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://platform.gilight.cn","CASTGC="+tgc,getApplication());
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
            if (!StringUtils.isEmpty(appId)){
                start =  Calendar.getInstance().getTimeInMillis() ;
                Log.i("Info", "start:  " + start );
            }

        }

    };

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
           /* if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }*/

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
                Log.e("下载失败", path);
                Log.e("下载失败", url);

                Uri shareFileUrl = FileUtil.getFileUri(getApplicationContext(), null, new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(BaseWebActivity3.this)
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
                new Share2.Builder(BaseWebActivity3.this)
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
            SoundPoolUtils.virateCancle(BaseWebActivity3.this);

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
                    /*if (!mAgentWeb.back()){
                        finish();
                        rvClose.setVisibility(View.GONE);
                    }else {
                        mAgentWeb.back();
                        rvClose.setVisibility(View.GONE);
                    }*/
                    finish();

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

    private void showDialog() {

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您确定要关闭该页面吗?")
                    .setNegativeButton("再逛逛", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            BaseWebActivity3.this.finish();
                        }
                    }).create();
        }
        mAlertDialog.show();

    }

    @Override
    protected void onPause() {

        toastStop();
        super.onPause();

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        toastStop();

    }
    /**
     * 点击2次退出
     */
    long waitTime = 2000;
    long touchTime = 0;
    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;

            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private Toast mToast;
    // 对toast进行一个简单的封装
    public void toastShow(CharSequence message) {
        if (null == mToast) {
            mToast = Toast.makeText(BaseWebActivity3.this, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(message);
        }

        mToast.show();

    }
    //取消Toast
    public void toastStop() {
        if (null != mToast) {
            mToast.cancel();

        }

    }


    private WebViewClient mWebViewClient2 = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("userAgent4",  view.getSettings().getUserAgentString());


        }

        //        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url =  null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }


            if (url.contains("http://platform.gilight.cn/cas/login")) {
                if (StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
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
            if (url.contains("http://platform.gilight.cn/cas/login")) {
                if (StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""))){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            CookieUtils.syncCookie("http://platform.gilight.cn","CASTGC="+tgc,getApplication());
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
            if (!StringUtils.isEmpty(appId)){
                start =  Calendar.getInstance().getTimeInMillis() ;
                Log.i("Info", "start:  " + start );
            }

        }

    };


}
