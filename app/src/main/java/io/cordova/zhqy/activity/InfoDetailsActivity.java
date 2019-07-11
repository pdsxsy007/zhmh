package io.cordova.zhqy.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.WebListenerManager;
import com.just.agentweb.download.AgentWebDownloader;
import com.just.agentweb.download.DefaultDownloadImpl;
import com.just.agentweb.download.DownloadListenerAdapter;
import com.just.agentweb.download.DownloadingService;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
import io.cordova.zhqy.R;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.CookieUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.web.BaseWebActivity;
import io.cordova.zhqy.web.FileUtil;
import io.cordova.zhqy.web.WebLayout;

/**
 * Created by Administrator on 2019/4/16 0016.
 */

public class InfoDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

//    @BindView(R.id.swipeLayout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
     @BindView(R.id.ll_web)
    LinearLayout linearLayout;
    protected AgentWeb mAgentWeb;


    @BindView(R.id.layout_back)
    RelativeLayout layout_back;
    String appUrl,title,tgc;
    String appUrl2;
    String time;
    String msgsender;

    @Override
    protected int getResourceId() {
        return R.layout.activity_info_details;
    }

    @Override
    protected void initView() {
        super.initView();

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

        Log.e("InfoDetailsActivity",appUrl +"  换行    \n"+appUrl2);
        //appUrl2 = "【轻院通知】您的成绩查询通知已发放，请使用“轻院服务门户”的用户名和密码点击进入<a href=\"http://xytz.zzuli.edu.cn:8080/result/inquiry\">点击查看</a>并在通知文末填写反馈。同时，请点击<a href=\"http://info.zzuli.edu.cn/_t598/2019/0124/c13520a193789/page.htm\">  此处</a>及时完成学生基本信息补录。（信息中心）";
       if(appUrl!= null){
           mAgentWeb = AgentWeb.with(this)//
                   .setAgentWebParent(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                   .useDefaultIndicator(getResources().getColor(R.color.colorPrimaryDark), 1)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
////                .setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
//                .useDefaultIndicator(getResources().getColor(R.color.title_bar_bg),3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                    .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                    .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                    .setWebChromeClient(mWebChromeClient) //WebChromeClient
                    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                   .setWebLayout(new WebLayout(this))
                   .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                   .createAgentWeb()//创建AgentWeb。
                   .ready()//设置 WebSettings。
                   .go(appUrl);
           mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
       }else {


           String standard = "\n" +
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
                   "window.onload = function(){\n" +
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
//                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
//                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
//                .setWebChromeClient(mWebChromeClient) //WebChromeClient
//                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                   .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                   .createAgentWeb()//创建AgentWeb。
                   .ready()//设置 WebSettings。
                   .go(standard);
           mAgentWeb.getUrlLoader().loadDataWithBaseURL(null,standard,"text/html","UTF-8",null);
           mAgentWeb.getWebCreator().getWebView().setOverScrollMode(WebView.OVER_SCROLL_NEVER);
       }

        /*if (!mAgentWeb.back()){
            finish();
        }*/
    }

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


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);

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
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
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
            if (url.contains("http://kys.zzuli.edu.cn/cas/login")) {
                if (StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
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
            public WebListenerManager setDownloader(WebView webView, android.webkit.DownloadListener downloadListener) {
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
}
