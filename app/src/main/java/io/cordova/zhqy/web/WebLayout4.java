package io.cordova.zhqy.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.just.agentweb.IWebLayout;
import com.jwsd.libzxing.decode.DecodeBitmap;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import io.cordova.zhqy.R;
import io.cordova.zhqy.utils.DownPicUtil;
import io.cordova.zhqy.utils.MobileInfoUtils;

import static io.cordova.zhqy.activity.SplashActivity.getLocalVersionName;


/**
 * Created by cenxiaozhong on 2017/7/1.
 * source code  https://github.com/Justson/AgentWeb
 */

public class WebLayout4 implements IWebLayout {

    private Activity mActivity;
    private final RelativeLayout mTwinklingRefreshLayout;
    private WebView mWebView = null;
    String url;

    @SuppressLint("HandlerLeak")
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == 99){
                boolean isQR = (boolean) msg.obj;
                if(isQR == false){
                    rl_code.setVisibility(View.GONE);
                }else {
                    rl_code.setVisibility(View.VISIBLE);
                }
                popupWindow.showAtLocation(mWebView, Gravity.BOTTOM, 0, 0);
            }else {
                String picFile = (String) msg.obj;
                String[] split = picFile.split("/");
                String fileName = split[split.length-1];
                try {
                    MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), picFile, fileName, null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 最后通知图库更新
                mActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile)));
                Toast.makeText(mActivity,"图片保存图库成功",Toast.LENGTH_LONG).show();
            }

        }
    };

    String imei = "";

    public WebLayout4(final Activity activity) {
        this.mActivity = activity;
        mTwinklingRefreshLayout = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.fragment_twk_web, null);
      //  mTwinklingRefreshLayout.setPureScrollModeOn();
        mWebView = (WebView) mTwinklingRefreshLayout.findViewById(R.id.webView);
        CookieManager cookieManager = CookieManager.getInstance();
        if(Build.VERSION.SDK_INT>=21){
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            cookieManager.flush();
        }
        WebSettings setting = mWebView.getSettings();
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setDomStorageEnabled(true);

        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024*1024*8);
        String appCachePath = activity.getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);

        if (imei.isEmpty()){
            String userAgent = setting.getUserAgentString();
            imei = MobileInfoUtils.getIMEI(activity);
//        String IMSI = MobileInfoUtils.getIMSI(activity.getApplication());
            Map<String,String> map=new HashMap<String,String>();
            map.put("version",getLocalVersionName(activity));
            map.put("device",imei);
            JSONObject json =new JSONObject(map);
            Log.e("userAgent", json.toString());
            setting.setUserAgentString(userAgent  +json.toString());
            String s = userAgent + json.toString();
            Log.e("userAgent", s);
        }

       /* initPop();
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final WebView.HitTestResult hitTestResult = mWebView.getHitTestResult();
                if(hitTestResult.getType()== WebView.HitTestResult.IMAGE_TYPE||
                        hitTestResult.getType()== WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    url = hitTestResult.getExtra();
                    isQR = false;
                    DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener(){

                        @Override
                        public void getDownPath(String s) {
                            isQR = decodeImage(s);
                            Message message = new Message();
                            message.what = 99;
                            message.obj = isQR;
                            handler.sendMessage(message);
                        }
                    });

                }
                return true;
            }
        });*/



////        cookieManager.removeAllCookie();//移除
//        cookieManager.removeSessionCookie();//移除
////        cookieManager.removeAllCookie();  +  ;Domain=http://kys.zzuli.edu.cn
//        cookieManager.setAcceptCookie(true);
//        String stgc = "CASTGC="+tgc;
//        Log.i("stgc",stgc);
//        cookieManager.setCookie("http://kys.zzuli.edu.cn",stgc);

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(MyApp.getInstance());
//        }
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);// 允许接受 Cookie
//        cookieManager.setCookie("http://kys.zzuli.edu.cn","CASTGC=TGT-675-ZVcXoadTb0xdfC4BCEFhzk3eDGADOMzzY7y6WjzKiqrev5kvRC-jinghua;Path=/cas/;Domain=kys.zzuli.edu.cn");
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


    PopupWindow popupWindow;
    RelativeLayout rl_code;
    private void initPop() {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.dialog_bottom, null, false);
        //实例化PopupWindow并设置宽高
        popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失，这里因为PopupWindow填充了整个窗口，所以这句代码就没用了
        popupWindow.setOutsideTouchable(true); //设置可以点击
        popupWindow.setTouchable(true); //进入退出的动画

        RelativeLayout rl_cancel = inflate.findViewById(R.id.rl_cancel);
        RelativeLayout rl_save = inflate.findViewById(R.id.rl_save);

        rl_code = inflate.findViewById(R.id.rl_code);
        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownPicUtil.downPic(url, new DownPicUtil.DownFinishListener(){

                    @Override
                    public void getDownPath(String s) {
                        Toast.makeText(mActivity,"下载完成",Toast.LENGTH_LONG).show();
                        Message msg = Message.obtain();
                        msg.obj=s;
                        handler.sendMessage(msg);
                    }
                });
                popupWindow.dismiss();
            }
        });

        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
            }
        });

        rl_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mActivity,CodeWebActivity.class);
                intent.putExtra("appUrl",text);
                mActivity.startActivity(intent);
                popupWindow.dismiss();
            }
        });



    }

    boolean isQR = false;
    String text;
    /**
     * 判断是否为二维码
     * @param sUrl 图片地址
     * @return
     */
    private boolean decodeImage(String sUrl){
        Bitmap bitmap= BitmapFactory.decodeFile(sUrl); ;
        QRCodeReader reader = new QRCodeReader();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        Result decode = null;
        try {
            decode = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        //result = DecodeImage.handleQRCodeFormBitmap(getBitmap(sUrl));
        if(decode == null){
            isQR = false;
        }else {
            isQR = true;

            text = decode.getText();
        }
        return isQR;
    }


}
