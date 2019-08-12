package io.cordova.zhqy.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import com.just.agentweb.download.DownloadingService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;

import butterknife.BindView;
import gdut.bsx.share2.Share2;
import gdut.bsx.share2.ShareContentType;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.activity.LoginActivity;
import io.cordova.zhqy.activity.LoginActivity2;
import io.cordova.zhqy.activity.MyToDoMsgActivity;
import io.cordova.zhqy.bean.CountBean;
import io.cordova.zhqy.utils.BadgeView;
import io.cordova.zhqy.utils.BaseFragment;
import io.cordova.zhqy.utils.LighterHelper;
import io.cordova.zhqy.utils.MobileInfoUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.netState;
import io.cordova.zhqy.web.BaseWebActivity;
import io.cordova.zhqy.web.BaseWebActivity4;
import io.cordova.zhqy.web.FileUtil;
import io.cordova.zhqy.web.WebLayout;
import me.samlss.lighter.Lighter;
import me.samlss.lighter.interfaces.OnLighterListener;
import me.samlss.lighter.parameter.Direction;
import me.samlss.lighter.parameter.LighterParameter;
import me.samlss.lighter.parameter.MarginOffset;
import me.samlss.lighter.shape.CircleShape;

import static io.cordova.zhqy.utils.MyApp.getInstance;

/**
 * Created by Administrator on 2018/11/19 0019.
 */

public class FindPreFragment extends BaseFragment {



    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.header)
    ClassicsHeader header;

    @BindView(R.id.rl_msg_app1)
    RelativeLayout rl_msg_app;
    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private DownloadingService mDownloadingService;
    private Gson mGson = new Gson();
    /**分享*/
    private static final int REQUEST_SHARE_FILE_CODE = 120;
    private BadgeView badge1;
    boolean isLogin = false;
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_second_pre;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));

        setWeb();
        rl_msg_app.setVisibility(View.VISIBLE);
        count = (String) SPUtils.get(getActivity(), "count", "");
        badge1 = new BadgeView(getActivity(), rl_msg_app);
        remind();
        if (!isLogin){
            badge1.hide();
        }else {
            if(count != null){
                if(count.equals("0")){

                    badge1.hide();
                }else {
                    badge1.show();
                }
            }else {
                badge1.hide();
            }



        }



        /*if (!StringUtils.isEmpty(getText1())){
            rlFindMsgApp.setVisibility(View.VISIBLE);
            remind(rlFindMsgApp);
        }else {
            rlFindMsgApp.setVisibility(View.GONE);
        }*/
        header.setEnableLastTime(false);
        rl_msg_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));
                if (isLogin){
                    Intent intent = new Intent(MyApp.getInstance(), MyToDoMsgActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MyApp.getInstance(), LoginActivity2.class);
                    startActivity(intent);
                }
            }
        });

        String home02 = (String) SPUtils.get(MyApp.getInstance(), "home02", "");
        if(home02.equals("")){
            setGuideView();
        }
    }
    private void setGuideView() {


        CircleShape circleShape = new CircleShape(10);
        circleShape.setPaint(LighterHelper.getDashPaint()); //set custom paint
        View tipView1 = getLayoutInflater().inflate(R.layout.fragment_home_gl, null);
        // 使用图片
        Lighter.with(getActivity()
        )
                .setBackgroundColor(0xB9000000)
                .setOnLighterListener(new OnLighterListener() {
                    @Override
                    public void onShow(int index) {


                    }

                    @Override
                    public void onDismiss() {
                        SPUtils.put(MyApp.getInstance(),"home02","1");
                    }
                })
                .addHighlight(new LighterParameter.Builder()
                        //.setHighlightedViewId(R.id.rl_msg_app)
                        .setHighlightedView(rl_msg_app)
                        .setTipLayoutId(R.layout.fragment_home_gl3)
                        //.setLighterShape(new RectShape(80, 80, 50))
                        //.setLighterShape(circleShape)
                        .setTipViewRelativeDirection(Direction.BOTTOM)
                        .setTipViewRelativeOffset(new MarginOffset(150, 0, 30, 0))
                        .build()).show();
    }



    private void remind() { //BadgeView的具体使用


//        badge1.hide();
        String text1 = getText1();
        //ToastUtils.showToast(getActivity(),text1+"onCreate");
        String count = (String) SPUtils.get(getActivity(), "count", "");
        if(count.equals("")){
            count = "0";
        }
        badge1.setText(count); // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(10); // 文本大小
        badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
//        badge1.setBadgeMargin(5); //各边间隔
        // badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示

        // badge1.hide();//影藏显示

        if(count.equals("0")){
            badge1.hide();
        }else {
            badge1.show();// 只有显示

        }

    }



    private void setWeb() {


        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mSwipeRefreshLayout, new SwipeRefreshLayout.LayoutParams(-1,-1))
//                .setAgentWebParent((LinearLayout) view, -1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//传入AgentWeb的父控件。
                .useDefaultIndicator(getResources().getColor(R.color.title_bar_bg),3)//设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .setAgentWebWebSettings(getSettings())//设置 IAgentWebSettings。
                .setWebViewClient(mWebViewClient)//WebViewClient ， 与 WebView 使用一致 ，但是请勿获取WebView调用setWebViewClient(xx)方法了,会覆盖AgentWeb DefaultWebClient,同时相应的中间件也会失效。
                .setWebChromeClient(mWebChromeClient) //WebChromeClient
                .setMainFrameErrorView(R.layout.layout_no_net, 0)
                .setWebLayout(new WebLayout(getActivity()))
                .setPermissionInterceptor(mPermissionInterceptor) //权限拦截 2.0.0 加入。
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)//打开其他页面时，弹窗质询用户前往其他应用 AgentWeb 3.0.0 加入。
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                .go("http://info.zzuli.edu.cn/_t598/main.htm");

    }

    @Override
    public void initListener() {
        super.initListener();
//        initPullRefresh();
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (!netState.isConnect(getActivity()) ){
                    ToastUtils.showToast(getActivity(),"网络连接异常!");
                    refreshlayout.finishRefresh();
                }else {
                    if (mAgentWeb.getWebCreator().getWebView().getScrollY() == 0){
                        refreshlayout.finishRefresh();
                    }else {
                        refreshlayout.finishRefresh();
                    }
                    setWeb();
                }

            }
        });
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageCommitVisible(WebView view, String url) {
//            if (imei.isEmpty()){
//                String userAgent = view.getSettings().getUserAgentString();
//                imei = MobileInfoUtils.getIMEI(getApplicationContext());
////        String IMSI = MobileInfoUtils.getIMSI(activity.getApplication());
//                Map<String,String> map=new HashMap<String,String>();
//                map.put("device",imei);
//                map.put("version",localVersion);
//                JSONObject json =new JSONObject(map);
//                Log.i("userAgent2", json.toString());
//                view.getSettings().setUserAgentString(userAgent  +json.toString());
//            }

            //use the data received here

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

            if (!url.equals("http://info.zzuli.edu.cn/_t598/main.htm")){
                Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                intent.putExtra("appUrl",url);
//                    intent.putExtra("appId",listBean.getAppId()+"");
                startActivity(intent);
                return true;
            }

            return super.shouldOverrideUrlLoading(view, request);
        }

        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (!url.equals("http://info.zzuli.edu.cn/_t598/main.htm")){
                Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                intent.putExtra("appUrl",url);
//                    intent.putExtra("appId",listBean.getAppId()+"");
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
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

                Uri shareFileUrl = FileUtil.getFileUri(getActivity(),null,new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(getActivity())
                        .setContentType(ShareContentType.FILE)
                        .setShareFileUri(shareFileUrl)
                        .setTitle("Share File")
                        .setOnActivityResult(REQUEST_SHARE_FILE_CODE)
                        .build()
                        .shareBySystem();

            } else {//下载成功
                Log.e("path",path);

                Uri shareFileUrl = FileUtil.getFileUri(getActivity(),null,new File(path));
                Log.e("path2", String.valueOf(shareFileUrl));
                new Share2.Builder(getActivity())
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


    @Override
    public void onResume() {
//        setWeb();
        mAgentWeb.getWebLifeCycle().onResume();//恢复
//        if (!StringUtils.isEmpty(getText1())){
//            rlMsgApp.setVisibility(View.VISIBLE);
//            remind(rlMsgApp);
//        }else {
//            rlMsgApp.setVisibility(View.GONE);
//        }
        super.onResume();
    }

    @Override
    public void onPause() {

        mAgentWeb.getWebLifeCycle().onPause(); //暂停应用内所有WebView ， 调用mWebView.resumeTimers();/mAgentWeb.getWebLifeCycle().onResume(); 恢复。
        super.onPause();
    }


    private String text1;
    public String getText1() {
        return text1;
    }
    public void setText1(String text1) {
        this.text1 = text1;
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();


        }else{  // 在最前端显示 相当于调用了onResume();
            isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(),"username",""));
            netInsertPortal("2");
            String count = (String) SPUtils.get(getActivity(),"count","");
            Log.e("count-------",count);
            //badge1.setText(count);

            if (!isLogin){
                badge1.hide();
            }else {
                netWorkSystemMsg();
                //remind(rl_msg_app);
            }


        }
    }
    CountBean countBean1;
    private void netWorkSystemMsg() {

        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_countUnreadMessagesForCurrentUser)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());

                        countBean1 = JSON.parseObject(response.body(), CountBean.class);
                        //yy_msg_num.setText(countBean.getCount()+"");
                        netWorkOAToDoMsg();//OA待办

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    CountBean countBean2;
    /**OA消息列表*/
    private void netWorkOAToDoMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "db")
                .params("workType", "workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());

                        countBean2 = JSON.parseObject(response.body(), CountBean.class);
                        netWorkDyMsg();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("sssssss",response.toString());
                    }
                });
    }

    CountBean countBean3;
    String count;
    private void netWorkDyMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "dy")
                .params("workType", "workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.toString());

                        countBean3 = JSON.parseObject(response.body(), CountBean.class);

                        String s = countBean2.getCount() + Integer.parseInt(countBean1.getObj()) + countBean3.getCount() + "";


                        if(null == s){
                            s = "0";
                        }
                        SPUtils.put(MyApp.getInstance(),"count",s+"");

                        count = (String) SPUtils.get(getActivity(), "count", "");
                        if(!count.equals("") && !"0".equals(count)){
                            remind();
                            SPUtils.get(getActivity(),"count","");
                        }else {
                            badge1.hide();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    private void netInsertPortal(final String insertPortalAccessLog) {
        String imei = MobileInfoUtils.getIMEI(getActivity());
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Four_Modules)
                .params("portalAccessLogMemberId",(String) SPUtils.get(getInstance(),"userId",""))
                .params("portalAccessLogEquipmentId",(String) SPUtils.get(getInstance(),"imei",""))//设备ID
                .params("portalAccessLogTarget", insertPortalAccessLog)//访问目标
                .params("portalAccessLogVersionNumber", (String) SPUtils.get(getActivity(),"versionName", ""))//版本号
                .params("portalAccessLogOperatingSystem", "ANDROID")//版本号
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("sdsaas",response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

}
