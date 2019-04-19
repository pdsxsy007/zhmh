package io.cordova.zhqy;


import android.*;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.jwsd.libzxing.QRCodeManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ycbjie.ycupdatelib.PermissionUtils;
import com.ycbjie.ycupdatelib.UpdateFragment;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.cordova.zhqy.activity.LoginActivity;
import io.cordova.zhqy.bean.BaseBean;
import io.cordova.zhqy.bean.CountBean;
import io.cordova.zhqy.bean.CurrencyBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.bean.UpdateBean;
import io.cordova.zhqy.bean.UserMsgBean;
import io.cordova.zhqy.fragment.home.FindPreFragment;
import io.cordova.zhqy.fragment.home.HomePreFragment;
import io.cordova.zhqy.fragment.home.MyPre2Fragment;

import io.cordova.zhqy.fragment.home.ServicePreFragment;
import io.cordova.zhqy.jpushutil.NotificationsUtils;
import io.cordova.zhqy.utils.ActivityUtils;
import io.cordova.zhqy.utils.BadgeView;
import io.cordova.zhqy.utils.BaseActivity2;

import io.cordova.zhqy.utils.CircleCrop;
import io.cordova.zhqy.utils.MobileInfoUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.web.AgentWebFragment;

import static io.cordova.zhqy.utils.MyApp.*;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class Main2Activity extends BaseActivity2 {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_my)
     RadioButton rbMy;

    @BindView(R.id.main_radioGroup)
    public  RadioGroup mainRadioGroup;



    boolean isFirst = true,isHome = true,isFind = true,isService = true, isMy = true,isFive = true,isLogin = false;
    String insertPortalAccessLog ;
    HomePreFragment homePreFragment;
    AgentWebFragment mAgentWebFragment  ;
    AgentWebFragment mAgentWebFragment2;

    FindPreFragment findPreFragment;
    ServicePreFragment servicePreFragment;

    MyPre2Fragment myPre2Fragment;
    private String registrationId;
    CurrencyBean currencyBean;
    //    boolean enabled = NotificationsUtils.isNotificationEnabled2(MyApp.getInstance());
    //这个是你的包名
    private static final String apkName = "io.cordova.zhqy";
    private static final String firstUrl = "http://xxx/xxx/xxx.apk";
    private static final String[] mPermission = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE};
    private Button button4;
    private BadgeView badge1;
    int perTag;


    @Override
    protected int getResourceId() {
        return R.layout.activity_main2;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        super.initView();
        isLogin = !StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""));
        if (isLogin){
            if (StringUtils.isEmpty(MyApp.registrationId)){
                MyApp.registrationId =  JPushInterface.getRegistrationID(this);
            }

            SPUtils.put(this,"registrationId", MyApp.registrationId);
            bindJpush();
          /*悬浮窗权限检测*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (!Settings.canDrawOverlays(this)) {
                        //若未授权则请求权限
                        perTag = 0;
                        showDialog();
                    }else if (!NotificationsUtils.isNotificationEnabled(getApplicationContext())){
                        perTag = 1;
                        showDialog();
                    }
                }
            }

        }

        button4 = (Button) findViewById(R.id.btn_my);
//        提示消息
        badge1 = new BadgeView(this, button4);
        badge1.hide();
        Log.d("TAG", " registrationId : " + MyApp.registrationId);
        setPer();
        mainRadioGroup.check(R.id.rb_home_page);
        showFragment(0);
        insertPortalAccessLog = "1";
        if (isHome){
            netInsertPortal(insertPortalAccessLog);
        }
        setPermission();//权限判断
        isOpen();//判断悬浮窗权限
//     查询通知权限

//        remind(button4);
        //getUpdateInfo();

    }
    private void remind() { //BadgeView的具体使用
        // 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge1.setText(countBean2.getCount()+Integer.parseInt(countBean1.getObj())+countBean3.getCount()+""); // 需要显示的提醒类容
        // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(10); // 文本大小
        badge1.setBadgeMargin(50, 5); // 水平和竖直方向的间距
//        badge1.setBadgeMargin(5); //各边间隔
        // badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
        badge1.show();// 只有显示
        // badge1.hide();//影藏显示
    }

    @Override
    protected void initListener() {
        super.initListener();
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_page:
                        showFragment(0);

                        insertPortalAccessLog = "5";
                        if (isFive){
                            netInsertPortal(insertPortalAccessLog);
                        }
                        break;
                    case R.id.rb_recommend:
                        showFragment(1);
                        insertPortalAccessLog = "2";
                        if (isFind){
                            netInsertPortal(insertPortalAccessLog);
                        }
                        break;
                    case R.id.rb_shopping:
                        showFragment(2);
                        insertPortalAccessLog = "3";
                        if (isService){
                            netInsertPortal(insertPortalAccessLog);
                        }
                        break;
                    case R.id.rb_my:
                        if (!StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
                            showFragment(3);
                            insertPortalAccessLog = "4";
                            if (isMy){
                                netInsertPortal(insertPortalAccessLog);
                            }
                        }else {
                            mainRadioGroup.check(R.id.rb_home_page);
                            showFragment(0);
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }
        });
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
                    homePreFragment = new HomePreFragment();
                    ft.add(R.id.frameLayout, homePreFragment);
                }
                break;
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (findPreFragment != null)
                    ft.show(findPreFragment);
                else {
                    findPreFragment = new FindPreFragment();
                    ft.add(R.id.frameLayout, findPreFragment);
                }
                break;
            case 2:
                // 如果fragment1已经存在则将其显示出来
                if (servicePreFragment != null)
                    ft.show(servicePreFragment);
                    // 否则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    servicePreFragment = new ServicePreFragment();
                    ft.add(R.id.frameLayout, servicePreFragment);
                }
                break;
            case 3:

              if ( myPre2Fragment!= null)
                    ft.show(myPre2Fragment);

                else {
                    myPre2Fragment = new MyPre2Fragment();
                    ft.add(R.id.frameLayout, myPre2Fragment);
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
        if (findPreFragment != null)
            ft2.hide(findPreFragment);
       if (myPre2Fragment != null)
           ft2.hide(myPre2Fragment);
    }

    /**
     * 第一次进入统计四大模块是否点击
     *
     * @param insertPortalAccessLog*/
    BaseBean baseBean ;
    private void netInsertPortal(final String insertPortalAccessLog) {
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.Four_Modules)
                .params("portalAccessLogEquipmentId", MobileInfoUtils.getIMEI(getApplicationContext()))//设备ID
                .params("portalAccessLogTarget", insertPortalAccessLog)//访问目标
                .params("portalAccessLogVersionNumber", (String) SPUtils.get(getApplicationContext(),"VersionName", ""))//版本号
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("result1",response.body()+"   --防空");
                        if (null != response.body()){
                            baseBean = JSON.parseObject(response.body(),BaseBean.class);
                            if (baseBean.isSuccess()){
                                if (insertPortalAccessLog.equals("1")){
                                    isHome = false;
                                }else  if (insertPortalAccessLog.equals("2")){
                                    isFind = false;
                                }else  if (insertPortalAccessLog.equals("3")){
                                    isService = false;
                                }else  if (insertPortalAccessLog.equals("4")){
                                    isMy = false;
                                }else  if (insertPortalAccessLog.equals("5")){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Tag","走了这里了"+requestCode+"---"+resultCode);
        //注册onActivityResult
        QRCodeManager.getInstance().with(this).onActivityResult(requestCode, resultCode, data);
    }



    /**
     * 根据UserId 绑定Jpush*/
    private void bindJpush() {
        OkGo.<String>get(UrlRes.HOME4_URL+UrlRes.Registration_Id)
                .tag("Jpush")
                .params("equipType","android")
                .params("userId",(String) SPUtils.get(getInstance(),"userId",""))
                .params("portalEquipmentMemberEquipmentId", MyApp.registrationId)
                .params("portalEquipmentMemberGroup", "")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("JPush",response.body());
                        currencyBean = JSON.parseObject(response.body(),CurrencyBean.class);
                        if (currencyBean.isSuccess()){
                            //绑定成功
                            isFirst = false;
                            Log.e("JPush","绑定成功");
                        }else {
                            //绑定失败
                            isFirst = true;
                            Log.e("JPush","绑定失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //绑定失败
                        isFirst = true;
                        Log.e("JPush","绑定失败");
                    }
                });
    }


    /** 开启通知权限 */
    private AlertDialog mAlertDialog;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setPer() {
        if ( !NotificationsUtils.isNotificationEnabled(getApplicationContext())){
            showDialogs();
        }
    }
    /**推送设置弹窗*/
    private void showDialogs() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您需要去开启推送权限么?\n")
                    .setNegativeButton("不需要", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            NotificationsUtils.gotoSet(getApplicationContext());
                        }
                    }).create();
        }
        mAlertDialog.show();

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("msgId","啦啦 我走过这里");
//        initData();
//        msgId = (String) SPUtils.get(getApplicationContext(),"msgId","");
//        if (!StringUtils.isEmpty(msgId)){
//            String lgUrl =UrlRes.HOME_URL+UrlRes.Redirect_Url+msgId+"&type=socket";
//
//            try {
//                baseUrl = UrlRes.Login_Home+userName+"&password="+userPwd+"&loginUrl="+UrlRes.HOME_URL+ UrlRes.Login_Url+"&redirect="+ URLEncoder.encode(lgUrl, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            SPUtils.remove(getApplicationContext(),"msgId");
//        }else {
//            baseUrl =UrlRes.Login_Home+userName+"&password="+userPwd+"&loginUrl="+UrlRes.HOME_URL+ UrlRes.Login_Url+"&redirect=http://iapp.zzuli.edu.cn/portal/";
//        }
//
////            Log.e("TAGNewIntent",baseUrl);
//        mAgentWeb.getWebCreator().getWebView().loadUrl(baseUrl);
//        isYinDao = false;
//        isYindao();

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
                T.showShort(Main2Activity.this, "再按一次退出");
                touchTime = currentTime;
            } else {
                ActivityUtils.getActivityManager().finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getUpdateInfo() {
        PermissionUtils.init(this);
        boolean granted = PermissionUtils.isGranted(mPermission);
        if(!granted){
            PermissionUtils permission = PermissionUtils.permission(mPermission);
            permission.callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {

                }
                @Override
                public void onDenied() {
                    PermissionUtils.openAppSettings();
                    Toast.makeText(Main2Activity.this,"请允许权限",Toast.LENGTH_SHORT).show();
                }
            });
            permission.request();
        }

        OkGo.<String>get("http://192.168.30.5:8080"+UrlRes.getNewVersionInfo)
                .params("system","android")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("ss",response.body());
                        UpdateBean updateBean = JSON.parseObject(response.body(),UpdateBean.class);
                        String portalVersionNumber = updateBean.getObj().getPortalVersionNumber();
                        int portalVersionUpdate = updateBean.getObj().getPortalVersionUpdate();
                        if (portalVersionUpdate == 1) {//1代表强制更新
                            UpdateFragment.showFragment(Main2Activity.this,
                                    true,firstUrl,apkName,updateBean.getObj().getPortalVersionUpdateDescription(), BuildConfig.APPLICATION_ID);
                        }else {//普通更新
                            UpdateFragment.showFragment(Main2Activity.this,
                                    false,firstUrl,apkName,updateBean.getObj().getPortalVersionUpdateDescription(), BuildConfig.APPLICATION_ID);
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty((String)SPUtils.get(MyApp.getInstance(),"username",""))){
            netWorkSystemMsg();
        }else {
            badge1.hide();
        }

    }
    CountBean countBean1;
    /** 获取消息数量*/

    private void netWorkSystemMsg() {
        String userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
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
       /* OkGo.<String>post(UrlRes.HOME_URL + UrlRes.OA_Msg_List)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .params("platformtype", "H5手机端")
                .params("sizes",5)
                .params("type", "db")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        oaMsgListBean = JSON.parseObject(response.body(), OAMsgListBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (oaMsgListBean.isSuccess()) {
                            Log.i("OA消息列表",response.body());
                            if (!oaMsgListBean.getObj().isEmpty()){
                                llOa.setVisibility(View.VISIBLE);
                                synchronized (object) {
                                        allMsgNum = allMsgNum + oaMsgListBean.getObj().size();
                                    Log.e("allMsgNum",allMsgNum+"");
                                }
                                //tvMyToDoMsgNum.setText(oaMsgListBean.getObj().size()+"");
                                setRvOAMsgList();
                            }
                        }else {
                            llOa.setVisibility(View.GONE);
                            T.showShort(MyApp.getInstance(), "没有数据");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        llOa.setVisibility(View.GONE);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(MyApp.getInstance(), "没有数据");
                    }
                });*/
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

                    }
                });
    }

    CountBean countBean3;
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

                        //tvMyToDoMsgNum.setText(countBean2.getCount()+Integer.parseInt(countBean1.getObj())+countBean3.getCount()+"");

                        remind();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }

    //获去唯一标识CAMERA,WRITE_EXTERNAL_STORAGE
    private void setPermission() {
        //同时请求多个权限
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(
                        android.Manifest.permission.READ_PHONE_STATE,
//                        ,
                        android.Manifest.permission.SYSTEM_ALERT_WINDOW
                        ,
//               Manifest.permission.WRITE_EXTERNAL_STORAGE,
//               Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission. RECEIVE_WAP_PUSH
                )
                .subscribe(new io.reactivex.functions.Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.e("用户已经同意该权限", permission.name + " is granted.");
                            //   Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {

                            Log.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");

                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   Log.d(TAG, permission.name + " is denied.");
                            Log.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
//                            BaseWebActivity.this.finish();
                        }
                    }
                });
    }

    private void isOpen() {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
        Log.e("isOpen",areNotificationsEnabled+"");
    }

    //请求悬浮窗权限
    @TargetApi(Build.VERSION_CODES.O)
    private void getOverlayPermission() {
        perTag = 1;
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 0);
    }
    /**推送设置弹窗*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showDialog() {

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("您需要去开启推送权限么?\n")
                    .setNegativeButton("不需要", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                        }
                    })//
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mAlertDialog != null) {
                                mAlertDialog.dismiss();
                            }
                            if (perTag == 0){
                                getOverlayPermission();
                            }else if (perTag == 1){
                                perTag =0;
                                NotificationsUtils.requestNotify(getApplicationContext());
                            }
                        }
                    }).create();
        }

        mAlertDialog.show();
    }

}
