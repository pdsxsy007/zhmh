package io.cordova.zhqy.utils;

import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * ==============================================
 * ==============================================
 * ==================《程序员》==================
 * =======十年生死两茫茫，写程序，到天亮。=======
 * ==============千行代码，Bug何处藏。===========
 * =======纵使上线又怎样，朝令改，夕断肠。=======
 * ----------------------------------------------
 * ======领导每天新想法，天天改，日日忙。========
 * =============相顾无言，惟有泪千行。===========
 * ======每晚灯火阑珊处，夜难寐，加班狂。========
 * ==============================================
 */
public class MyIntentService extends IntentService {
    private static final String ACTION_INIT_WHEN_APP_CREATE
            = "com.li.service.action.INIT";
//    public MyIntentService(String name) {
//        super(name);
//    }

    public MyIntentService() {
        super("niujunfeng");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        L.isDebug = true;//打包以前必须修改为flase*************************************
        initOkGo();
        //置入一个不设防的VmPolicy：解决7.0+的fileUri异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    /**
     * 初始化ok
     */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("feng");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        builder.readTimeout(30000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(30000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(3000, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));

//
//        try {
//            HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("a.cer"));
//            builder.sslSocketFactory(sslParams3.sSLSocketFactory, sslParams3.trustManager);
//            builder.hostnameVerifier(new SafeHostnameVerifier());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        OkGo.getInstance().init((Application) MyApp.getInstance())
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .setRetryCount(3);

    }

    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //这个以后要和服务器讨论，因为https现在现在什么效验都没做
            if (hostname.equals("www.baidu.cn") || hostname.equals("baidu.cn")) {
                return true;
            }
            return false;
        }
    }
}
