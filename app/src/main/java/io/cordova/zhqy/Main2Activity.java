package io.cordova.zhqy;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.jwsd.libzxing.QRCodeManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import io.cordova.zhqy.activity.LoginActivity;
import io.cordova.zhqy.bean.BaseBean;
import io.cordova.zhqy.bean.CurrencyBean;
import io.cordova.zhqy.bean.LoginBean;
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



    boolean isFirst = true,isHome = true,isFind = true,isService = true, isMy = true,isFive = true;
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
    @Override
    protected int getResourceId() {
        return R.layout.activity_main2;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView() {
        super.initView();
        Button button4 = (Button) findViewById(R.id.btn_my);
        if (StringUtils.isEmpty(MyApp.registrationId)){
            MyApp.registrationId =  JPushInterface.getRegistrationID(this);;
        }
        Log.d("TAG", " registrationId : " + MyApp.registrationId);
        setPer();
        mainRadioGroup.check(R.id.rb_home_page);
        showFragment(0);
        insertPortalAccessLog = "1";
        if (isHome){
            netInsertPortal(insertPortalAccessLog);
        }


        remind(button4);


    }
    private void remind(View view) { //BadgeView的具体使用
        BadgeView badge1 = new BadgeView(this, view);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge1.setText("120"); // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(10); // 文本大小
        //badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
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

    @Override
    protected void initData() {
        super.initData();

        SPUtils.put(this,"registrationId", MyApp.registrationId);
        bindJpush();
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
}
