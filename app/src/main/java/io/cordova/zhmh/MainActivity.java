package io.cordova.zhmh;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jwsd.libzxing.OnQRCodeListener;
import com.jwsd.libzxing.QRCodeManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.cordova.zhmh.activity.FaceNewActivity;
import io.cordova.zhmh.activity.InfoDetailsActivity2;
import io.cordova.zhmh.activity.LoginActivity2;
import io.cordova.zhmh.bean.AddFaceBean;
import io.cordova.zhmh.bean.BaseBean;
import io.cordova.zhmh.bean.CountBean;
import io.cordova.zhmh.bean.CurrencyBean;
import io.cordova.zhmh.bean.LoginBean;
import io.cordova.zhmh.bean.NewStudentBean;
import io.cordova.zhmh.bean.UpdateBean;
import io.cordova.zhmh.bean.UserMsgBean;
import io.cordova.zhmh.fragment.MessageFragment;
import io.cordova.zhmh.fragment.SecondFragment;
import io.cordova.zhmh.fragment.home.Home1Fragment;
import io.cordova.zhmh.fragment.home.MyPre2Fragment;
import io.cordova.zhmh.fragment.home.ServicePreFragment;
import io.cordova.zhmh.jpushutil.NotificationsUtils;
import io.cordova.zhmh.utils.ActivityUtils;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.BadgeView;
import io.cordova.zhmh.utils.BaseActivity3;
import io.cordova.zhmh.utils.CookieUtils;
import io.cordova.zhmh.utils.DensityUtil;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.MobileInfoUtils;
import io.cordova.zhmh.utils.MyApp;
import io.cordova.zhmh.utils.PermissionsUtil;
import io.cordova.zhmh.utils.SPUtils;
import io.cordova.zhmh.utils.ScreenSizeUtils;
import io.cordova.zhmh.utils.StringUtils;
import io.cordova.zhmh.utils.SystemInfoUtils;
import io.cordova.zhmh.utils.T;
import io.cordova.zhmh.utils.ToastUtils;
import io.cordova.zhmh.utils.ViewUtils;
import io.cordova.zhmh.utils.NetStateUtils;
import io.cordova.zhmh.web.BaseWebActivity4;
import io.cordova.zhmh.web.ChangeUpdatePwdWebActivity;
import io.cordova.zhmh.widget.MyDialog;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static io.cordova.zhmh.UrlRes.HOME_URL;
import static io.cordova.zhmh.UrlRes.insertportalPositionUrl;
import static io.cordova.zhmh.activity.SplashActivity.getLocalVersionName;
import static io.cordova.zhmh.utils.AesEncryptUtile.key;
import static io.cordova.zhmh.utils.MyApp.getInstance;
import static io.cordova.zhmh.utils.MyApp.isrRunIng;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class MainActivity extends BaseActivity3 implements PermissionsUtil.IPermissionsCallback{

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_my)
    RadioButton rbMy;

    @BindView(R.id.main_radioGroup)
    RadioGroup mainRadioGroup;

    String count;

    boolean isFirst = true, isHome = true, isFind = true, isService = true, isMy = true, isFive = true, isLogin = false;
    String insertportalAccessLog;
    Home1Fragment homePreFragment;


    SecondFragment findPreFragment;
    MessageFragment messageFragment;
    ServicePreFragment servicePreFragment;

    MyPre2Fragment myPre2Fragment;
    private String registrationId;
    CurrencyBean currencyBean;

    private Button button4;
    private BadgeView badge1;
    int perTag;
    private int flag = 0;
    public static boolean isForeground = false;
    String time;
    @BindView(R.id.webView)
    WebView webView;

    private int quanxian = 0;

    String userId;

    public LocationClient mLocationClient;
    private MyLocationListener myListener = new MyLocationListener();
    private PermissionsUtil permissionsUtil;
    @BindView(R.id.rb_home_page)
    RadioButton rb_home_page;
    @BindView(R.id.rb_recommend)
    RadioButton rb_recommend;
    @BindView(R.id.rb_message)
    RadioButton rb_message;
    @BindView(R.id.rb_shopping)
    RadioButton rb_shopping;


    @Override
    protected int getResourceId() {
        return R.layout.activity_main2;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        super.initView();
        //测试修改提交git
        //
        Drawable drawableFirst = getResources().getDrawable(R.drawable.tab_home_page);
        drawableFirst.setBounds(0, 0, 69, 69);
        rb_home_page.setCompoundDrawables(null, drawableFirst, null, null);

        Drawable drawableFirst2 = getResources().getDrawable(R.drawable.tab_find);
        drawableFirst2.setBounds(0, 0, 69, 69);
        rb_recommend.setCompoundDrawables(null, drawableFirst2, null, null);

        Drawable drawableFirst3 = getResources().getDrawable(R.drawable.tab_msg);
        drawableFirst3.setBounds(0, 0, 69, 69);
        rb_message.setCompoundDrawables(null, drawableFirst3, null, null);

        Drawable drawableFirst4 = getResources().getDrawable(R.drawable.tab_service_selector);
        drawableFirst4.setBounds(0, 0, 69, 69);
        rb_shopping.setCompoundDrawables(null, drawableFirst4, null, null);

        Drawable drawableFirst5 = getResources().getDrawable(R.drawable.tab_my);
        drawableFirst5.setBounds(0, 0, 69, 69);
        rbMy.setCompoundDrawables(null, drawableFirst5, null, null);


        isrRunIng = "1";

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        showState();

        button4 = (Button) findViewById(R.id.btn_my);
//        提示消息
        badge1 = new BadgeView(this, button4);
        badge1.hide();
        Log.d("TAG", " registrationId : " + MyApp.registrationId);

        mainRadioGroup.check(R.id.rb_home_page);
        showFragment(flag);
        insertportalAccessLog = "1";
        if (isHome) {
            netInsertportal(insertportalAccessLog);
        }


        isOpen();//判断悬浮窗权限

//        getUpdateInfo();
        registerBoradcastReceiver();

        getDownLoadType();
        /*permissionsUtil=  PermissionsUtil
                .with(this)
                .requestCode(0)
                .isDebug(true)//开启log
                .permissions(PermissionsUtil.Permission.Phone.READ_PHONE_STATE,
                        PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION)
                .request();


        String  st = (String)SPUtils.get(this,"showys","");
        if(st.equals("") || st == null){
            showDialogs2();
        }

        String splash = getIntent().getStringExtra("splash");

        if(splash == null){
            permissionsUtil=  PermissionsUtil
                    .with(this)
                    .requestCode(1)
                    .isDebug(true)//开启log
                    .permissions(PermissionsUtil.Permission.Camera.CAMERA,PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                    .request();
        }*/

        String splash =getIntent().getStringExtra("splash");
        String  st = (String)SPUtils.get(this,"showys","");
        if(st.equals("") || st == null){
            showDialogs2();
        }else {
            if(splash == null){
                permissionsUtil=  PermissionsUtil
                        .with(MainActivity.this)
                        .requestCode(1)
                        .isDebug(true)//开启log
                        .permissions(PermissionsUtil.Permission.Camera.CAMERA,
                                PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,
                                PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                        .request();
            }

            String showQuanxian = (String) SPUtils.get(this, "showQuanxian", "");
            if(!showQuanxian.equals("1")){
                permissionsUtil=  PermissionsUtil
                        .with(this)
                        .requestCode(0)
                        .isDebug(true)//开启log
                        .permissions(PermissionsUtil.Permission.Phone.READ_PHONE_STATE,
                                PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION
                               )
                        .request();
            }
        }

        String openlogin = getIntent().getStringExtra("openlogin");
        if(openlogin != null){
            Log.e("跳转","跳转登录");
            Intent intent = new Intent(MainActivity.this,LoginActivity2.class);
            startActivity(intent);
        }


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
            boolean b = location.hasAltitude();
            double altitude = location.getAltitude();
            float speed = location.getSpeed();
            float direction = location.getDirection();
            int locationWhere = location.getLocationWhere();

            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            Log.e("errorCode",errorCode+"");
            Log.e("latitude",latitude+"");
            Log.e("longitude",longitude+"");
            Log.e("altitude主页",altitude+"");
            Log.e("speed",speed+"");
            Log.e("direction",direction+"");
            Log.e("locationWhere",locationWhere+"");

            com.baidu.location.Address address = location.getAddress();
            String addressLine = address.address;
            if(addressLine != null){
                SPUtils.put(MainActivity.this,"addressLine",addressLine);
            }

            SPUtils.put(MainActivity.this,"latitude",latitude+"");
            SPUtils.put(MainActivity.this,"longitude",longitude+"");
            SPUtils.put(MainActivity.this,"altitude",altitude+"");
            SPUtils.put(MainActivity.this,"speed",speed+"");
            SPUtils.put(MainActivity.this,"direction",direction+"");
            SPUtils.put(MainActivity.this,"locationWhere",locationWhere+"");
            OkGo.<String>post(UrlRes.HOME_URL+insertportalPositionUrl)
                    .tag(this)
                    .params("memberId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                    .params("positionLongitude", longitude)
                    .params("positionLatitude", latitude)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("五分钟阅读",response.body());

                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Log.e("s",response.toString());
                        }
                    });
        }
    }




    private MyDialog m_Dialog3;

    private void showDialogs2() {

        m_Dialog3 = new MyDialog(this, R.style.dialogdialog);

        View view = View.inflate(this, R.layout.alert_ys, null);

        int width = ScreenSizeUtils.getWidth(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        m_Dialog3.setContentView(view, layoutParams);
        final TextView agreetv = view.findViewById(R.id.agree_tv);
        CheckBox argeeCheck = view.findViewById(R.id.agree_check);
        TextView gongneng = view.findViewById(R.id.gongneng);


        argeeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    agreetv.setTextColor(Color.parseColor("#42adbb"));
                    agreetv.setClickable(true);
                    agreetv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            m_Dialog3.dismiss();
                            SPUtils.put(MainActivity.this,"showys","1");

                            String splash =getIntent().getStringExtra("splash");
                            if(splash == null){
                                permissionsUtil=  PermissionsUtil
                                        .with(MainActivity.this)
                                        .requestCode(1)
                                        .isDebug(true)//开启log
                                        .permissions(PermissionsUtil.Permission.Camera.CAMERA,
                                                PermissionsUtil.Permission.Storage.READ_EXTERNAL_STORAGE,
                                                PermissionsUtil.Permission.Storage.WRITE_EXTERNAL_STORAGE)
                                        .request();
                            }

                            String showQuanxian = (String) SPUtils.get(MainActivity.this, "showQuanxian", "");
                            if(!showQuanxian.equals("1")){
                                permissionsUtil=  PermissionsUtil
                                        .with(MainActivity.this)
                                        .requestCode(0)
                                        .isDebug(true)//开启log
                                        .permissions(PermissionsUtil.Permission.Phone.READ_PHONE_STATE,
                                                PermissionsUtil.Permission.Location.ACCESS_FINE_LOCATION
                                                )
                                        .request();
                            }

                        }
                    });


                }else{
                    agreetv.setTextColor(Color.parseColor("#A0A0A0"));
                    agreetv.setClickable(false);
                }
            }
        });
        gongneng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BaseWebActivity4.class);
                intent.putExtra("appUrl","http://platform.gilight.cn/authentication/authentication/views/appNative/privacyProtocol.html" );
                startActivity(intent);
            }
        });




        m_Dialog3.setCanceledOnTouchOutside(false);
        m_Dialog3.show();

        m_Dialog3.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });


    }



    private void showNewSuDialog() {
        userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
        if (!userId.equals("")) {
            //是否弹窗人脸识别
            checkIsNewStudent(userId);

            //是否强制修改密码
            checkIsUpdatepwd(userId);


        }
    }
    /**个人信息*/
    UserMsgBean userMsgBean;
    private void netWorkUserMsg() {
        try {
            OkGo.<String>post(UrlRes.HOME_URL + UrlRes.User_Msg)
                    .tag(this)
                    .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.e("获取个人信息",response.body());
                            userMsgBean = JSON.parseObject(response.body(), UserMsgBean.class);
                            if (userMsgBean.isSuccess()) {
                                if(null != userMsgBean.getObj()) {
                                    String memberSign = userMsgBean.getObj().getModules().getMemberSign();
                                    if(memberSign == null){
                                        memberSign = "";
                                    }
                                    if(!memberSign.equals("1")){
                                        Intent intent = new Intent(MainActivity.this, ChangeUpdatePwdWebActivity.class);
                                        intent.putExtra("appUrl", UrlRes.HOME2_URL+ UrlRes.changePwdUrl2);
                                        //intent.putExtra("appUrl", "http://192.168.30.68:8084/authentication/authentication/views/appNative/changePwd_kfdx.html");
                                        startActivity(intent);
                                    }
                                    StringBuilder sb = new StringBuilder();
                                    if(userMsgBean.getObj().getModules().getRolecodes()!= null){

                                        if (userMsgBean.getObj().getModules().getRolecodes().size() > 0){
                                            for (int i = 0; i < userMsgBean.getObj().getModules().getRolecodes().size(); i++) {
                                                sb.append(userMsgBean.getObj().getModules().getRolecodes().get(i).getRoleCode()).append(",");
                                            }
                                            String ss = sb.substring(0, sb.lastIndexOf(","));
                                            Log.e("TAG",ss);
                                            SPUtils.put(MyApp.getInstance(),"rolecodes",ss);
                                            SPUtils.put(MyApp.getInstance(),"usernameeidt",userMsgBean.getObj().getModules().getMemberUsername());

                                            Intent intent2 = new Intent();
                                            intent2.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                                           /* Bundle bundle = new Bundle();
                                            bundle.putString("datas", AppWidgetManager.EXTRA_APPWIDGET_IDS);
                                            intent2.putExtras(bundle);*/
                                            sendBroadcast(intent2);

                                        }

                                    }

                                }else {

                                }



                            }
                        }
                    });
        }catch (Exception e){

        }


    }


    private void checkIsUpdatepwd(String userId) {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.newStudentUpdatePwdStateUrl)
                .tag(this)
                .params("userName", userId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("checkIsUpdatepwd", response.body());

                        NewStudentBean newStudentBean = JsonUtil.parseJson(response.body(), NewStudentBean.class);
                        if (newStudentBean != null) {
                            boolean success = newStudentBean.getSuccess();
                            if (success) {
                                NewStudentBean.Obj obj = newStudentBean.getObj();
                                if (obj != null) {
                                    String type = obj.getType();
                                    if (type.equals("0")) {//弹出修改密码
                                        Intent intent = new Intent(MainActivity.this, ChangeUpdatePwdWebActivity.class);
                                        intent.putExtra("appUrl", "http://platform.gilight.cn/authentication/authentication/views/appNative/changePwd_jhdx.html");
                                        startActivity(intent);
                                    }
                                }
                            }
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("onError", response.body());
                    }
                });
    }

    private void checkIsNewStudent(String userId) {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.jugdeFaceUrl)
                .tag(this)
                .params("userName", userId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("checkIsNewStudent", response.body());

                        NewStudentBean newStudentBean = JsonUtil.parseJson(response.body(), NewStudentBean.class);
                        if (newStudentBean != null) {
                            boolean success = newStudentBean.getSuccess();
                            if (success) {
                                NewStudentBean.Obj obj = newStudentBean.getObj();
                                if (obj != null) {
                                    String type = obj.getType();
                                    Log.e("获取到的type", type);
                                    if (type.equals("0")) {//弹出人脸识别框
                                        cameraTask();

                                    }
                                }
                            }
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("onError", response.body());
                    }
                });
    }

    private static final int RC_CAMERA_PERM = 123;

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            logOut();
        } else {//没有相应权限，获取相机权限
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "获取照相机权限",
                    RC_CAMERA_PERM, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void getDownLoadType() {
        OkGo.<String>get(UrlRes.HOME_URL + UrlRes.getDownLoadTypeUrl)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("下载类型", response.body());
                        SPUtils.put(MainActivity.this, "downLoadType", response.body());
                       /* DownLoadBean downLoadBean = JsonUtil.parseJson(response.body(),DownLoadBean.class);
                        List<String> string = downLoadBean.getString();*/


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    private void addMobieInfo() {

        //获取运行内存的信息
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        long totalMem = info.totalMem;

        final StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long totalCounts = statFs.getBlockCountLong();//总共的block数
        long size = statFs.getBlockSizeLong(); //每格所占的大小，一般是4KB==
        long totalROMSize = totalCounts * size; //内部存储总大小


        String imei = MobileInfoUtils.getIMEI(this);
        String deviceModel = SystemInfoUtils.getDeviceModel();
        String displayVersion = SystemInfoUtils.getDISPLAYVersion();
        String deviceAndroidVersion = SystemInfoUtils.getDeviceAndroidVersion();
        String deviceCpu = SystemInfoUtils.getDeviceCpu();
        String s = totalMem / 1024 / 1024 / 1024 + "GB";
        String s3 = totalROMSize / 1024 / 1024 / 1024 + "GB";
        String s4 = SystemInfoUtils.getWindowWidth(this) + "X" + SystemInfoUtils.getWindowHeigh(this);

        Log.e("s", s);
        Log.e("s3", s3);
        Log.e("s4", s4);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.addMobileInfoUrl)
                .tag(this)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .params("mobilePhoneModel", SystemInfoUtils.getDeviceModel())
                .params("mobileVersion", SystemInfoUtils.getDISPLAYVersion())
                .params("mobileSystemVersion", SystemInfoUtils.getDeviceAndroidVersion())
                .params("mobileCpu", SystemInfoUtils.getDeviceCpu())
                .params("mobileMemory", s)
                .params("mobileStorageSpace", s3)
                .params("mobileScreen", SystemInfoUtils.getWindowWidth(this) + "X" + SystemInfoUtils.getWindowHeigh(this))
                .params("mobileEquipmentId", imei)
                .params("mobileSystemCategory", 1)
                .params("mobileAppVersion", getLocalVersionName(this))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("SysMsg", response.body());


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    private void showState() {
        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(), "username", ""));
        if (isLogin) {

            if (StringUtils.isEmpty(MyApp.registrationId)) {
                MyApp.registrationId = JPushInterface.getRegistrationID(this);
            }

            SPUtils.put(this, "registrationId", MyApp.registrationId);
            bindJpush();

            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            boolean isOpened = manager.areNotificationsEnabled();
            //ToastUtils.showToast(this,isOpened+"");
            if(!isOpened){
                String noticeTime = (String) SPUtils.get(MainActivity.this,"noticeTime","");
                if(noticeTime.equals("")){
                    showDialogs();
                }else {
                    long lastNoticeTime = Long.parseLong(noticeTime);
                    long time = new Date().getTime();
                    Long s = (time - lastNoticeTime) / (1000 * 60*60*24);
                    if(s >= 7){
                        showDialogs();
                    }
                }



            }
        }
    }


    private void remind() { //BadgeView的具体使用
        String count = (String) SPUtils.get(this, "count", "");
        // 创建一个BadgeView对象，view为你需要显示提醒的控件
        //badge1.setText(countBean2.getCount()+Integer.parseInt(countBean1.getObj())+countBean3.getCount()+""); // 需要显示的提醒类容

        //int i = countBean2.getCount() + countBean1.getCount() + countBean3.getCount();
        int i1 = Integer.parseInt(count);
        if(i1>99){
            badge1.setText("99+"); // 需要显示的提醒类容
        }else {
            badge1.setText(count); // 需要显示的提醒类容
        }

        if (Integer.parseInt(count) > 9) {
            // 需要显示的提醒类容
            badge1.setBadgePosition(BadgeView.POSITION_TOP_LEFT1);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        } else {
            badge1.setBadgePosition(BadgeView.POSITION_TOP_LEFT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        }


        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(10); // 文本大小
        badge1.show();// 只有显示

    }


    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refresh2");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("refresh2")) {
                String faceNewActivity = intent.getStringExtra("FaceNewActivity");
                if (faceNewActivity != null) {
                    String bitmap = (String) SPUtils.get(MainActivity.this, "bitmapnewsd", "");

                    upToServer(bitmap);
                } else {
                    showState();
                }

            }
        }
    };

    public void upToServer(String sresult) {

        ViewUtils.createLoadingDialog(this);
        OkGo.<String>post(UrlRes.HOME2_URL + UrlRes.addFaceUrl)
                .tag(this)
                .params("openId", "123456")
                .params("memberId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .params("img", sresult)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e("上传图片", response.body());
                        AddFaceBean faceBean = JsonUtil.parseJson(response.body(), AddFaceBean.class);
                        boolean success = faceBean.getSuccess();
                        String msg = faceBean.getMsg();
                        ViewUtils.cancelLoadingDialog();
                        if (success == true) {
                            Intent intent = new Intent(MainActivity.this, BaseWebActivity4.class);
                            intent.putExtra("appUrl", "http://microapp.zzuli.edu.cn/microapplication/db_qy/app/newStudentDb.html");
                            startActivity(intent);

                            m_Dialog2.dismiss();
                        } else {
                            ToastUtils.showToast(MainActivity.this, msg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(getApplicationContext(), "找不到服务器了，请稍后再试");
                    }
                });
    }

    @Override
    protected void initListener() {
        super.initListener();
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_page:
                        flag = 0;
                        showFragment(0);

                        insertportalAccessLog = "5";
                        if (isFive) {
                            netInsertportal(insertportalAccessLog);
                        }
                        break;
                    case R.id.rb_recommend:
                        flag = 1;
                        showFragment(1);
                        insertportalAccessLog = "2";
                        if (isFind) {
                            netInsertportal(insertportalAccessLog);
                        }
                        break;
                    case R.id.rb_message:
                        if (isLogin) {
                            showFragment(2);
                            flag = 2;
                            insertportalAccessLog = "3";
                            netInsertportal(insertportalAccessLog);
                        } else {

                            switch (flag) {
                                case 0:
                                    mainRadioGroup.check(R.id.rb_home_page);
                                    break;
                                case 1:
                                    mainRadioGroup.check(R.id.rb_recommend);
                                    break;
                                case 2:
                                    mainRadioGroup.check(R.id.rb_message);
                                    break;
                                case 3:
                                    mainRadioGroup.check(R.id.rb_shopping);
                                    break;
                                case 4:
                                    mainRadioGroup.check(R.id.rb_my);
                                    break;
                            }
                            showFragment(flag);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.rb_shopping:
                        flag = 3;
                        showFragment(3);
                        insertportalAccessLog = "4";
                        if (isService) {
                            netInsertportal(insertportalAccessLog);
                        }
                        break;
                    case R.id.rb_my:
                        //if (isLogin && NetStateUtils.isConnect(getApplicationContext())){
                        if (isLogin) {
                            showFragment(4);
                            flag = 4;
                            insertportalAccessLog = "5";
                            if (isMy) {
                                netInsertportal(insertportalAccessLog);
                            }
                        } else {
                            //mainRadioGroup.check(R.id.rb_home_page);
                            switch (flag) {
                                case 0:
                                    mainRadioGroup.check(R.id.rb_home_page);
                                    break;
                                case 1:
                                    mainRadioGroup.check(R.id.rb_recommend);
                                    break;
                                case 2:
                                    mainRadioGroup.check(R.id.rb_message);
                                    break;
                                case 3:
                                    mainRadioGroup.check(R.id.rb_shopping);
                                    break;
                                case 4:
                                    mainRadioGroup.check(R.id.rb_my);
                                    break;
                            }
                            showFragment(flag);
                            Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                            startActivity(intent);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onAttachFragment(Fragment fragment) {


        if (homePreFragment == null && fragment instanceof Home1Fragment)
            homePreFragment = (Home1Fragment) fragment;
        if (findPreFragment == null && fragment instanceof SecondFragment)
            findPreFragment = (SecondFragment) fragment;
        if (messageFragment == null && fragment instanceof MessageFragment)
            messageFragment = (MessageFragment) fragment;
        if (servicePreFragment == null && fragment instanceof ServicePreFragment)
            servicePreFragment = (ServicePreFragment) fragment;
        if (myPre2Fragment == null && fragment instanceof MyPre2Fragment)
            myPre2Fragment = (MyPre2Fragment) fragment;
    }


    public void showFragment(int i) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragments(ft);
        Bundle mBundle = null;
        switch (i) {
            case 0:
                if (homePreFragment != null)
                    ft.show(homePreFragment);
                else {
                    homePreFragment = new Home1Fragment();
                    ft.add(R.id.frameLayout, homePreFragment);
                }
                break;
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (findPreFragment != null)
                    ft.show(findPreFragment);
                else {
                    findPreFragment = new SecondFragment();
                    ft.add(R.id.frameLayout, findPreFragment);
                }


                break;
            case 2:
                // 如果fragment1已经存在则将其显示出来
                if (messageFragment != null)
                    ft.show(messageFragment);
                else {
                    messageFragment = new MessageFragment();
                    ft.add(R.id.frameLayout, messageFragment);
                }


                break;
            case 3:
                // 如果fragment1已经存在则将其显示出来
                if (servicePreFragment != null)
                    ft.show(servicePreFragment);
                    // 否则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    servicePreFragment = new ServicePreFragment();
                    ft.add(R.id.frameLayout, servicePreFragment);
                }

                break;
            case 4:

                if (!NetStateUtils.isConnect(MainActivity.this)) {
                    if (myPre2Fragment != null) {
                        ft.show(myPre2Fragment);
                    } else {
                        myPre2Fragment = new MyPre2Fragment();
                        ft.add(R.id.frameLayout, myPre2Fragment);
                    }
                    ToastUtils.showToast(MainActivity.this, "网络连接异常!");
                } else {
                    if (myPre2Fragment != null) {
                        ft.show(myPre2Fragment);
                    } else {
                        myPre2Fragment = new MyPre2Fragment();
                        ft.add(R.id.frameLayout, myPre2Fragment);
                    }


                }

                break;
        }
        ft.commit();
    }

    private void hideFragments(FragmentTransaction ft2) {
        if (homePreFragment != null)
            ft2.hide(homePreFragment);
        if (servicePreFragment != null)
            ft2.hide(servicePreFragment);
        if (messageFragment != null)
            ft2.hide(messageFragment);
        if (findPreFragment != null)
            ft2.hide(findPreFragment);
        if (myPre2Fragment != null)
            ft2.hide(myPre2Fragment);
    }

    /**
     * 第一次进入统计四大模块是否点击
     *
     * @param insertportalAccessLog
     */
    BaseBean baseBean;

    private void netInsertportal(final String insertportalAccessLog) {
        String imei = MobileInfoUtils.getIMEI(this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Four_Modules)
                .params("portalAccessLogMemberId", (String) SPUtils.get(getInstance(), "userId", ""))
                .params("portalAccessLogEquipmentId", imei)//设备ID
                .params("portalAccessLogTarget", insertportalAccessLog)//访问目标
                .params("portalAccessLogVersionNumber", (String) SPUtils.get(getApplicationContext(), "versionName", ""))//版本号
                .params("portalAccessLogOperatingSystem", "ANDROID")//版本号
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("sdsaas", response.body());
                        if (null != response.body()) {
                            baseBean = JSON.parseObject(response.body(), BaseBean.class);
                            if (baseBean.isSuccess()) {
                                if (insertportalAccessLog.equals("1")) {
                                    isHome = false;
                                } else if (insertportalAccessLog.equals("2")) {
                                    isFind = false;
                                } else if (insertportalAccessLog.equals("3")) {
                                    isService = false;
                                } else if (insertportalAccessLog.equals("4")) {
                                    isMy = false;
                                } else if (insertportalAccessLog.equals("5")) {
                                    isFive = false;
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }


    /**
     * 根据UserId 绑定Jpush
     */
    private void bindJpush() {
        OkGo.<String>get(UrlRes.HOME4_URL + UrlRes.Registration_Id)
                .tag("Jpush")
                .params("equipType", "android")
                .params("userId", (String) SPUtils.get(getInstance(), "userId", ""))
                .params("portalEquipmentMemberEquipmentId", (String) SPUtils.get(MainActivity.this, "registrationId", ""))
                .params("portalEquipmentMemberGroup", "")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("JPush", response.body());
                        currencyBean = JSON.parseObject(response.body(), CurrencyBean.class);
                        if (currencyBean.isSuccess()) {
                            //绑定成功
                            isFirst = false;
                            Log.e("JPush", "绑定成功");
                        } else {
                            //绑定失败
                            isFirst = true;
                            Log.e("JPush", "绑定失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //绑定失败
                        isFirst = true;
                        Log.e("JPush", "绑定失败");
                    }
                });
    }


    /**
     * 开启通知权限
     */
    private AlertDialog mAlertDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setPer() {
        if (!NotificationsUtils.isNotificationEnabled(getApplicationContext())) {
            showDialogs();
        }
    }

    private AlertDialog mAlertDialogNotice;
    private Window mDialogWindow;
    private void showDialogs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog);
        mAlertDialogNotice = builder.create();
        mAlertDialogNotice.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mAlertDialogNotice.dismiss();
            }
        });
        mAlertDialogNotice.show();
        mAlertDialogNotice.setCanceledOnTouchOutside(false);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_notice, null);
        RelativeLayout rl_close = dialogView.findViewById(R.id.rl_close);
        Button btn_open = dialogView.findViewById(R.id.btn_open);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialogNotice.dismiss();
                long time = new Date().getTime();
                SPUtils.put(MainActivity.this,"noticeTime",time+"");
            }
        });
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAlertDialogNotice.dismiss();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        mAlertDialogNotice.setContentView(dialogView);
        mDialogWindow = mAlertDialogNotice.getWindow();
        //计算屏幕宽高
        Integer[] widthAndHeight =getWidthAndHeight(this.getWindow());
        if (mDialogWindow != null) {
            mDialogWindow.setGravity(Gravity.CENTER);
            mDialogWindow.setWindowAnimations(R.style.PopupWindow);
            mDialogWindow.setLayout((int)(widthAndHeight[0] / 1.3), (int)(widthAndHeight[1] / 1.5));
            mDialogWindow.setBackgroundDrawableResource(R.drawable.shape_button_touming);
        }

    }

    public static Integer[] getWidthAndHeight(Window window) {
        if (window == null) {
            return null;
        }
        Integer[] integer = new Integer[2];
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        } else {
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        integer[0] = dm.widthPixels;
        integer[1] = dm.heightPixels;
        return integer;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    /**
     * 点击2次退出
     */
    long waitTime = 2000;
    long touchTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {

            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                T.showShort(MainActivity.this, "再按一次退出");
                touchTime = currentTime;
            } else {
                ActivityUtils.getActivityManager().finishAllActivity();
            }

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private void getUpdateInfo() {
        OkGo.<String>get(UrlRes.HOME_URL + UrlRes.getNewVersionInfo)
                .tag(this)
                .params("system", "android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("ss", response.body());

                        UpdateBean updateBean = JSON.parseObject(response.body(), UpdateBean.class);
                        String portalVersionNumber = updateBean.getObj().getportalVersionNumber();
                        int portalVersionUpdate = updateBean.getObj().getportalVersionUpdate();
                        String portalVersionDownloadAdress = updateBean.getObj().getportalVersionDownloadAdress();
                        logShow(portalVersionUpdate, portalVersionDownloadAdress, portalVersionNumber);

                    }
                });

    }

    String localVersionName;
    private MyDialog m_Dialog;
    private int isUpdate = 0;

    private void logShow(int portalVersionUpdate, final String portalVersionDownloadAdress, final String portalVersionNumber) {
        localVersionName = getLocalVersionName(this);
        m_Dialog = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_update, null);
        RelativeLayout rl_down = view.findViewById(R.id.rl_down);
        RelativeLayout rl_down2 = view.findViewById(R.id.rl_down2);
        TextView tv_version = view.findViewById(R.id.tv_version);
        RelativeLayout rl_cancel = view.findViewById(R.id.rl_cancel);

        int width = ScreenSizeUtils.getWidth(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(this, 24),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        m_Dialog.setContentView(view, layoutParams);
        tv_version.setText(portalVersionNumber);
        rl_down2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(MyApp.getInstance(), "update", portalVersionNumber);
                m_Dialog.dismiss();
                showNewSuDialog();

            }
        });
        String update = (String) SPUtils.get(MyApp.getInstance(), "update", "");
        Log.e("update", update);

        if (localVersionName.equals(portalVersionNumber)) {//最新版本
            if (m_Dialog2 != null) {
                if (m_Dialog2.isShowing()) {
                    m_Dialog2.dismiss();
                }
            }
            showNewSuDialog();


        } else {
            if (update.equals(portalVersionNumber)) {

                showNewSuDialog();
            } else {

                dealData(portalVersionNumber,rl_down,rl_down2,rl_cancel,portalVersionUpdate);

            }

        }

        rl_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, InfoDetailsActivity2.class);
                intent.putExtra("appUrl", portalVersionDownloadAdress);
                intent.putExtra("title2", "下载地址");
                startActivity(intent);
                if (isUpdate == 1) {
                    finish();
                }

            }
        });
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
                showNewSuDialog();
            }
        });


    }

    private void dealData(String portalVersionNumber, RelativeLayout rl_down, RelativeLayout rl_down2, RelativeLayout rl_cancel, int portalVersionUpdate) {
        String[] split = portalVersionNumber.split("\\.");
        String[] splitLocal = localVersionName.split("\\.");
        if(split.length == 3 && splitLocal.length == 3) {
            int sp0 = Integer.parseInt(split[0]);//服务器版本号
            int sp1 = Integer.parseInt(split[1]);
            int sp2 = Integer.parseInt(split[2]);
            int sl0 = Integer.parseInt(splitLocal[0]);//本地版本号
            int sl1 = Integer.parseInt(splitLocal[1]);
            int sl2 = Integer.parseInt(splitLocal[2]);

            if (sp0 == sl0) {//主版本号相等
                //判断第二位
                if (sp1 == sl1) {//服务器第二位版本号等于于本地第二位版本号 不执行更新操作

                    if (sp2 <= sl2) {//服务器第三位版本号等于于本地第三位版本号 不执行更新操作

                    } else {
                        m_Dialog.show();
                        if (portalVersionUpdate == 1) {//1代表强制更新

                            isUpdate = 1;
                            m_Dialog.setCanceledOnTouchOutside(false);
                            m_Dialog.setCancelable(false);
                            rl_cancel.setVisibility(View.GONE);
                            rl_down2.setVisibility(View.GONE);
                        } else {//普通更新
                            m_Dialog.setCanceledOnTouchOutside(false);
                            rl_cancel.setClickable(false);
                            isUpdate = 0;

                        }
                    }


                } else if (sp1 < sl1) {//服务器第二位版本号小于本地第二位版本号 不执行更新操作

                } else {//服务器第二位版本号大于本地第二位版本号 不执行更新操作
                    m_Dialog.show();
                    if (portalVersionUpdate == 1) {//1代表强制更新

                        isUpdate = 1;
                        m_Dialog.setCanceledOnTouchOutside(false);
                        m_Dialog.setCancelable(false);
                        rl_cancel.setVisibility(View.GONE);
                        rl_down2.setVisibility(View.GONE);
                    } else {//普通更新
                        m_Dialog.setCanceledOnTouchOutside(false);
                        rl_cancel.setClickable(false);
                        isUpdate = 0;

                    }
                }

            } else if (sp0 < sl0) {//服务器主版本号小于本地主版本号 不执行更新操作

            } else {//服务器主版本号大于本地版本号 执行更新操作
                m_Dialog.show();
                if (portalVersionUpdate == 1) {//1代表强制更新

                    isUpdate = 1;
                    m_Dialog.setCanceledOnTouchOutside(false);
                    m_Dialog.setCancelable(false);
                    rl_cancel.setVisibility(View.GONE);
                    rl_down2.setVisibility(View.GONE);
                } else {//普通更新
                    m_Dialog.setCanceledOnTouchOutside(false);
                    rl_cancel.setClickable(false);
                    isUpdate = 0;

                }
            }
        }
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onResume() {

        super.onResume();


        getUpdateInfo();
        String count = (String) SPUtils.get(this, "count", "");
        if(isLogin){
            netWorkUserMsg();
        }
        isForeground = true;
        //showFragment(flag);
        isLogin = !StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(), "username", ""));
        Log.e("onResume", "onResume");

        if (isLogin) {

            time = (String) SPUtils.get(MyApp.getInstance(), "time", "");
            if (time.equals("")) {
                time = Calendar.getInstance().getTimeInMillis() + "";
            }
            long nowTime = Calendar.getInstance().getTimeInMillis();
            long l = nowTime - Long.parseLong(time);
            long l1 = l / 1000 / 60 / 60;
            if (l1 >= 1) {
                netWorkLogin();
                   /*Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                   startActivity(intent);*/
            } else {

                webView.setWebViewClient(mWebViewClient);

                webView.loadUrl(HOME_URL+"/portal/login/appLogin");//http://platform.gilight.cn/cas/login

                if (count.equals("")) {
                    netWorkSystemMsg();
                } else {

                    netWorkSystemMsg();
                }



            }


        } else {
            badge1.hide();

        }


        registerBoradcastReceiver();

    }


    private String s1;
    private String s2;
    LoginBean loginBean;
    String tgt;

    private void netWorkLogin() {
        try {
            String phone = (String) SPUtils.get(MyApp.getInstance(), "phone", "");
            String pwd = (String) SPUtils.get(MyApp.getInstance(), "pwd", "");

//            URLEncoder.encode( ,"UTF-8")
            s1 = URLEncoder.encode(AesEncryptUtile.encrypt(phone, key), "UTF-8");
            s2 = URLEncoder.encode(AesEncryptUtile.encrypt(pwd, key), "UTF-8");


        } catch (Exception e) {
            e.printStackTrace();
        }


        OkGo.<String>get(UrlRes.HOME2_URL + "/cas/casApiLoginController")
                .tag(this)
                .params("openid", "123456")
                .params("username", s1)
                .params("password", s2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("result1", response.body());

                        loginBean = JSON.parseObject(response.body(), LoginBean.class);
                        if (loginBean.isSuccess()) {

                            try {
                                CookieManager cookieManager = CookieManager.getInstance();
                                cookieManager.removeAllCookie();

                                tgt = AesEncryptUtile.decrypt(loginBean.getAttributes().getTgt(), key);
                                String userName = AesEncryptUtile.decrypt(loginBean.getAttributes().getUsername(), key);


                                String userId = AesEncryptUtile.encrypt(userName + "_" + Calendar.getInstance().getTimeInMillis(), key);
                                SPUtils.put(MyApp.getInstance(), "time", Calendar.getInstance().getTimeInMillis() + "");
                                SPUtils.put(MyApp.getInstance(), "userId", userId);
                                SPUtils.put(MyApp.getInstance(), "personName", userName);
                                SPUtils.put(getApplicationContext(), "TGC", tgt);
                                SPUtils.put(getApplicationContext(), "username", s1);
                                SPUtils.put(getApplicationContext(), "password", s2);
                                webView.setWebViewClient(mWebViewClient);
                                webView.loadUrl(HOME_URL+"/portal/login/appLogin");
                                Intent intent = new Intent();
                                intent.putExtra("refreshService", "dongtai");
                                intent.setAction("refresh2");
                                sendBroadcast(intent);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            SPUtils.put(getApplicationContext(), "username", "");
                        }
                    }
                });
    }

    CountBean countBean1;

    private void netWorkSystemMsg() {


        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.countUserMessagesByTypeUrl)
                .tag(this)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .params("type", "0")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("系统消息数量", response.body());
                        countBean1 = JSON.parseObject(response.body(), CountBean.class);
//                        //yy_msg_num.setText(countBean.getCount()+"");
                        netWorkOAToDoMsg();//OA待办

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("s", response.toString());
                    }
                });
    }

    CountBean countBean2;

    /**
     * OA消息列表
     */
    /**OA消息列表*/
    private void netWorkOAToDoMsg() {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                .tag(this)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .params("type", "db")
                .params("workType", "workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s", response.toString());

                        countBean2 = JSON.parseObject(response.body(), CountBean.class);
                        netWorkDyMsg();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("sssssss", response.toString());
                    }
                });
    }


    CountBean countBean3;

    private void netWorkDyMsg() {

        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Query_count)
                .tag(this)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("type", "dy")
                .params("workType", "workdb")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("s",response.body());

                        countBean3 = JSON.parseObject(response.body(), CountBean.class);


                        count = countBean2.getCount() + countBean1.getCount() + countBean3.getCount() + "";
                        if(null == count){
                            count = "0";
                        }

                        SPUtils.put(MyApp.getInstance(),"count",count+"");
                        if(!count.equals("") && !"0".equals(count)){
                            remind();
                            SPUtils.get(MainActivity.this,"count","");
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

    private void isOpen() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        Log.e("isOpen", areNotificationsEnabled + "");

    }

    //请求悬浮窗权限
    @TargetApi(Build.VERSION_CODES.O)
    private void getOverlayPermission() {
        perTag = 1;
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 0);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        isrRunIng = "0";
        unregisterReceiver(broadcastReceiver);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        //        /**网址拦截*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                url = request.getUrl().toString();
            }


            if (url.contains(HOME_URL+"/portal/login/appLogin")) {
                if (StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(), "username", ""))) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
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
            if (url.contains(HOME_URL+"/portal/login/appLogin")) {
                if (StringUtils.isEmpty((String) SPUtils.get(MyApp.getInstance(), "username", ""))) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity2.class);
                    startActivity(intent);
                    finish();

                    return true;
                }
            }

            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {


            String tgc = (String) SPUtils.get(MainActivity.this, "TGC", "");
            CookieUtils.syncCookie("http://platform.gilight.cn", "CASTGC=" + tgc, getApplication());


        }

    };





    private MyDialog m_Dialog2;


    private void logOut() {
        m_Dialog2 = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog2.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_student, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);

        //int width = ScreenSizeUtils.getWidth(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        m_Dialog2.setContentView(view, layoutParams);
        m_Dialog2.show();
        m_Dialog2.setCanceledOnTouchOutside(false);

        rl_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FaceNewActivity.class);
                startActivity(intent);


            }
        });

        m_Dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //需要调用onRequestPermissionsResult
        permissionsUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        QRCodeManager.getInstance().with(this).onActivityResult(requestCode, resultCode, data);
        //监听跳转到权限设置界面后再回到应用
        permissionsUtil.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPermissionsGranted(int requestCode, String... permission) {
        Log.e("权限获取回调主页","onPermissionsGranted");

        if(requestCode == 0){
            getMyLocation();
            addMobieInfo();
        }else if(requestCode == 1){
            onScanQR();
        }


    }
    @Override
    public void onPermissionsDenied(int requestCode, String... permission) {
        Log.e("权限被拒绝回调","onPermissionsDenied");
        getMyLocation();
        addMobieInfo();

    }
    private void onScanQR() {
        QRCodeManager.getInstance()
                .with(this)
                .setReqeustType(0)
                .setRequestCode(55846)
                .scanningQRCode(new OnQRCodeListener() {
                    @Override
                    public void onCompleted(String result) {
                        Intent intent = new Intent(MyApp.getInstance(), BaseWebActivity4.class);
                        intent.putExtra("appUrl",result);
                        intent.putExtra("scan","scan");
                        startActivity(intent);

                    }

                    @Override
                    public void onError(Throwable errorMsg) {

                    }

                    @Override
                    public void onCancel() {

                    }


                    @Override
                    public void onManual(int requestCode, int resultCode, Intent data) {

                    }
                });
    }


    private void getMyLocation() {


        LocationClientOption option = new LocationClientOption();

        //设置坐标类型
        option.setCoorType("bd09ll");
        //设置是否需要地址信息，默认为无地址
        option.setIsNeedAddress(true);
        option.setIsNeedAltitude(true);
        option.setIsNeedLocationDescribe(true);
        option.setNeedDeviceDirect(true);
        //设置是否打开gps进行定位
        //option.setOpenGps(true);
        //设置扫描间隔为1秒
        option.setScanSpan(300000);
        //传入设置好的信息
        mLocationClient.setLocOption(option);

       /* option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");

        option.setScanSpan(36*1000);

        option.setOpenGps(true);

        option.setLocationNotify(true);

        option.setIgnoreKillProcess(false);

        option.SetIgnoreCacheException(false);

        //option.setWifiCacheTimeOut(5*60*1000);

        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);*/

        mLocationClient.start();
    }

}
