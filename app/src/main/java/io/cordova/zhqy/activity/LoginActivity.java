package io.cordova.zhqy.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.GetUserIdBean;
import io.cordova.zhqy.bean.LoginBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.SystemBarTintUtils;
import io.cordova.zhqy.utils.T;

import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2018/11/15 0015.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.et_password)
    EditText etPassword;
    String userName, passWord;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_login_3)
    Button btnLogin3;
    private Object W;
    private String s1;
    private String s2;

    @Override
    protected int getResourceId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initSystemBar() {
        super.initSystemBar();
        SystemBarTintUtils.initSystemBarColor(this, R.color.colorPrimary);
    }

    @Override
    protected void initView() {
        super.initView();
        userName = "admin";
        passWord = "123456";
    }



    GetUserIdBean getUserIdBean;






    @OnClick({R.id.l_1, R.id.btn_login,R.id.btn_login_3,R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.l_1:
                break;
            case R.id.btn_login:

                if (StringUtils.getEditTextData(etPhoneNum).isEmpty() && StringUtils.getEditTextData(etPassword).isEmpty()){
                    T.showShort(MyApp.getInstance(),"账号或密码为空........");
                }else {
                    netWorkLogin();
                }



//                if (StringUtils.getEditTextData(etPassword).isEmpty() && StringUtils.getEditTextData(etPhoneNum).isEmpty()){
//                    T.showShort(MyApp.getInstance(),"账号或密码不能为空");
//                }else {
//                    netGetLt();
//                }

                break;
            case R.id.btn_login_3:
             //   netExit();
                break;
            case R.id.iv_close:
                this.finish();
                break;
        }
    }
    LoginBean loginBean;
    private void netWorkLogin() {
        try {
//            URLEncoder.encode( ,"UTF-8")


            s1 = URLEncoder.encode(AesEncryptUtile.encrypt(StringUtils.getEditTextData(etPhoneNum),key),"UTF-8");
            s2 =  URLEncoder.encode(AesEncryptUtile.encrypt(StringUtils.getEditTextData(etPassword),key),"UTF-8");

            Log.e("login","s1 = "+ s1 + "  ,s2  = " + s2);

        } catch (Exception e) {
            e.printStackTrace();
        }


        OkGo.<String>get(UrlRes.HOME2_URL +"/cas/casApiLoginController")
//        OkGo.<String>get("http://192.168.30.29:8080" +"/cas/casApiLoginController")
                .params("openid","123456")
                .params("username",s1)
                .params("password",s2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("result1",response.body());

                        loginBean = JSON.parseObject(response.body(),LoginBean.class);
                        if (loginBean.isSuccess() ) {

                            try {
                                String tgt = AesEncryptUtile.decrypt(loginBean.getAttributes().getTgt(),key) ;
                                String userName = AesEncryptUtile.decrypt(loginBean.getAttributes().getUsername(),key) ;
                                String userId  = AesEncryptUtile.encrypt(userName+ "_"+ Calendar.getInstance().getTimeInMillis(),key);


                                SPUtils.put(MyApp.getInstance(),"userId",userId);
//                                SPUtils.put(MyApp.getInstance(),"tgt",tgt);
                                SPUtils.put(getApplicationContext(),"TGC",tgt);
                                SPUtils.put(getApplicationContext(),"username",s1);
                                SPUtils.put(getApplicationContext(),"password",s2);
                                Intent intent = new Intent(MyApp.getInstance(),Main2Activity.class);
                                intent.putExtra("userId",userName);
                                startActivity(intent);

                                Log.e("cookie2", Calendar.getInstance().getTimeInMillis() + " -- 防空导弹--");
                                Log.e("login","tgt = "+ tgt + "  ,userName  = " + userName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            T.showShort(MyApp.getInstance(),loginBean.getMsg());
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //    Bean bean;
//    private void netGetLt() {
//        final String urlen = URLEncoder.encode( "http://keys.zzuli.edu.cn/authentication/login/casLogin");
//        final List<String> list = new ArrayList<String>();
//        list.add(urlen);
//
//
//        OkGo.<String>get(UrlRes.HOME2_URL + UrlRes.Get_Lt).execute(new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
//                Log.d("respones",response.body());
//                String str = response.body();
//                String result1 = str.substring(str.indexOf("{"), str.indexOf("}") + 1);
//                bean = JSON.parseObject(result1, Bean.class);
//                Log.e("lt",bean.getLt());
//                Log.e("ex",bean.getExecution());
//                OkGo.<String>post(UrlRes.HOME2_URL +UrlRes.From_Login )//
//                        .params("username","2012016")
//                        .params("password","nic@2017")
//                        .params("lt",bean.getLt())
//                        .params("execution",bean.getExecution())
//                        .params("_eventId","submit")
//                        .params("submit","login")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(Response<String> response) {
//                                Log.d("respones",response.getRawResponse().header("Set-Cookie") );
//                                final String cookie = response.getRawResponse().header("Set-Cookie");
//                                final String result1 = cookie.substring(cookie.indexOf("=") + 1,cookie.lastIndexOf(";"));
//                                SPUtils.put(getApplicationContext(),"TGC",cookie);
//                                Log.e("cookie2", result1);
//                                Log.e("cookie2", cookie);
//                                SPUtils.put(MyApp.getInstance(),"cookies",result1);
//                                OkGo.<String>post(UrlRes.HOME_URL +UrlRes.Get_UserId)
//                                        .params("tgt",result1)
//                                        .execute(new StringCallback() {
//                                            @Override
//                                            public void onSuccess(Response<String> response) {
//                                                getUserIdBean = JSON.parseObject(response.body(),GetUserIdBean.class);
//                                                if (getUserIdBean.isSuccess()){
//                                                    String userId = getUserIdBean.getObj();
//                                                    SPUtils.put(MyApp.getInstance(),"userId",userId);
//                                                    Intent intent = new Intent(MyApp.getInstance(),Main2Activity.class);
//                                                    //    intent.putExtra("tgc",result1);
//                                                    intent.putExtra("cookie2",cookie);
//                                                    intent.putExtra("userId",userId);
//                                                    startActivity(intent);
//                                                }
//                                            }
//                                        });
//
//                            }
//                            @Override
//                            public void onError(Response<String> response) {
//                            }
//                        });
//
//            }
//        });
//
//
}
