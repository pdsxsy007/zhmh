package io.cordova.zhqy.utils;


import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.cordova.zhqy.activity.LoginActivity;


/**
 * Created by Administrator on 2018/11/17 0017.
 */

public class CookieUtils {

    private static WebView webView;

    public static void getCookie(LoginActivity activity, final String url,
                                 final OnCookieLoadedListener cookieLoadedListener) {
        webView = new WebView(activity);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (cookieLoadedListener != null) {
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookie = cookieManager.getCookie(url);
                    if (!TextUtils.isEmpty(cookie)) {
                        webView.setWebViewClient(null);
                        cookieLoadedListener.onCookieLoaded(cookie);
                    }
                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }



    public interface OnCookieLoadedListener {
        void onCookieLoaded(String cookie);
    }

    public static void webClose(){
        webView.removeAllViews();
        webView.destroy();
    }
}
