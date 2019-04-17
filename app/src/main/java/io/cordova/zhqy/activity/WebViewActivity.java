//package menhu.jh.myapplication.activity;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.KeyEvent;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.lzy.okgo.OkGo;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import menhu.jh.myapplication.R;
//import menhu.jh.myapplication.utils.ActivityUtils;
//import menhu.jh.myapplication.utils.SystemBarTintUtils;
//import menhu.jh.myapplication.utils.WebUtil.IEventHandler;
//
//
///**
// * Created by Administrator on 2018/11/17 0017.
// */
//
//public class WebViewActivity extends AppCompatActivity {
//    @BindView(R.id.web)
//    WebView web;
//    String url, tgc;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.tv_sure)
//    TextView tvSure;
//    @BindView(R.id.layout_close)
//    RelativeLayout rvClose;
//
//    boolean isCollection = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        SystemBarTintUtils.initSystemBarColor(this, R.color.title_bar_bg);
//        setContentView(R.layout.webview_activity);
//        ButterKnife.bind(this);
//        ActivityUtils.getActivityManager().addActivity(this);
//        rvClose.setVisibility(View.VISIBLE);
//        initView();
//    }
//
//    private void initView() {
//        tgc = getIntent().getStringExtra("tgc");
//        url = getIntent().getStringExtra("url");
//        iniweb();
//        initIsCollectin();
//    }
//
//    /*检测是否收藏过*/
//    private void initIsCollectin() {
//
//
//
//    }
//    /**
//     * IEventHandler 处理WebView相关返回事件
//     */
//    @OnClick({R.id.iv_back, R.id.tv_sure,R.id.iv_close})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.iv_back:
//                if (!web.canGoBack()) {
//                   WebViewActivity.this.finish();
//                }else {
//                    web.goBack();
//                }
//                break;
//            case R.id.tv_sure:
//                goCollection();
//                cancelCollection();
//                break;
//            case R.id.iv_close:
//                finish();
//                break;
//        }
//    }
//
//    /*取消收藏*/
//    private void cancelCollection() {
//
//
//    }
//
//    /*去收藏*/
//    private void goCollection() {
//
//
//    }
//
//
//    private void iniweb() {
////        WebSettings webSettings = web.getSettings();
////        webSettings.setAppCacheEnabled(true);
////        webSettings.setDomStorageEnabled(true);
////        webSettings.supportMultipleWindows();
////        webSettings.setAllowContentAccess(true);
////        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
////        webSettings.setUseWideViewPort(true);
////        webSettings.setLoadWithOverviewMode(true);
////        webSettings.setSavePassword(true);
////        webSettings.setSaveFormData(true);
////        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
////        webSettings.setLoadsImagesAutomatically(true);
////
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            web.getSettings().setMixedContentMode(
////                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
////        }
////        web.setWebViewClient(new WebViewClient());
////        web.setWebViewClient(new WebViewClient() {
////            @Override
////            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
////                // super.onReceivedSslError(view, handler, error);
////                // 接受所有网站的证书，忽略SSL错误，执行访问网页
////                handler.proceed();
////                //默认的处理方式，WebView变成空白页
////                //handler.cancel();
////                // 其他处理
////                //handleMessage(Message msg);
////            }
////        });
//        web.loadUrl(url);
//        web.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//        WebSettings settings = web.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//    }
//
//    /* 改写物理按键返回的逻辑 */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
//            // 返回上一页面
//            web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            web.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        ActivityUtils.getActivityManager().finishActivity(this);
//        OkGo.getInstance().cancelTag(this);
//        web.removeAllViews();
//        web.destroy();
//    }
//
//
//}
