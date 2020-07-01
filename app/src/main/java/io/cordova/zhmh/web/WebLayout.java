package io.cordova.zhmh.web;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.just.agentweb.IWebLayout;

import io.cordova.zhmh.R;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.SPUtils;

/**
 * Created by cenxiaozhong on 2017/7/1.
 * source code  https://github.com/Justson/AgentWeb
 */

public class WebLayout implements IWebLayout {

    private Activity mActivity;
    private final RelativeLayout mTwinklingRefreshLayout;
    private WebView mWebView = null;

    public WebLayout(final Activity activity) {
        this.mActivity = activity;
        mTwinklingRefreshLayout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.fragment_twk_web, null);
      //  mTwinklingRefreshLayout.setPureScrollModeOn();
        mWebView = (WebView) mTwinklingRefreshLayout.findViewById(R.id.webView);
        CookieManager cookieManager = CookieManager.getInstance();
        if(Build.VERSION.SDK_INT>=21){
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }

        String tgc = (String) SPUtils.get(MyApp.getInstance(),"TGC","");
                cookieManager.removeSessionCookie();//移除
//        cookieManager.removeAllCookie();  +  ;Domain=http://platform.gilight.cn
        cookieManager.setAcceptCookie(true);
        String stgc = "CASTGC="+tgc;
        Log.i("stgc",stgc);
        cookieManager.setCookie("http://platform.gilight.cn",stgc);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            cookieManager.flush();
        }
//        WebSettings setting = mWebView.getSettings();
//        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        setting.setUseWideViewPort(true);
//        setting.setLoadWithOverviewMode(true);
//        setting.setDomStorageEnabled(true);
////        cookieManager.removeAllCookie();//移除
//        cookieManager.removeSessionCookie();//移除
////        cookieManager.removeAllCookie();  +  ;Domain=http://platform.gilight.cn
//        cookieManager.setAcceptCookie(true);
//        String stgc = "CASTGC="+tgc;
//        Log.i("stgc",stgc);
//        cookieManager.setCookie("http://platform.gilight.cn",stgc);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(MyApp.getInstance());
//        }
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);// 允许接受 Cookie
//        cookieManager.setCookie("http://platform.gilight.cn","CASTGC=TGT-675-ZVcXoadTb0xdfC4BCEFhzk3eDGADOMzzY7y6WjzKiqrev5kvRC-jinghua;Path=/cas/;Domain=platform.gilight.cn");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.setAcceptThirdPartyCookies(mWebView, true);        //跨域cookie读取
//        }
//


    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mTwinklingRefreshLayout;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }



}
