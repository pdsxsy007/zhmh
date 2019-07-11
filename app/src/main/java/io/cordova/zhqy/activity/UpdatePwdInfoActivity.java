package io.cordova.zhqy.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;

import butterknife.BindView;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.CurrencyBean;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;

import static io.cordova.zhqy.UrlRes.updatePasswordUrl;
import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class UpdatePwdInfoActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_next)
    RelativeLayout rl_next;

    @BindView(R.id.tv_app_setting)
    ImageView tv_app_setting;

    @BindView(R.id.et_01)
    EditText et_01;

    @BindView(R.id.et_02)
    EditText et_02;

    @BindView(R.id.et_03)
    EditText et_03;

    @BindView(R.id.msg_notice)
    ImageView msg_notice;

    private String type = "2";


    @Override
    protected int getResourceId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_app_setting.setOnClickListener(this);
        rl_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = et_01.getText().toString().trim();
                String newP = et_02.getText().toString().trim();
                String newConfirmP = et_03.getText().toString().trim();
                if(oldP.equals("")){
                    ToastUtils.showToast(UpdatePwdInfoActivity.this,"请输入旧密码!");
                    return;
                }
                if(newP.equals("")){
                    ToastUtils.showToast(UpdatePwdInfoActivity.this,"请输入新密码!");
                    return;
                }

                if(newConfirmP.equals("")){
                    ToastUtils.showToast(UpdatePwdInfoActivity.this,"请确认输入新密码!");
                    return;
                }
                if(!isLetterDigit(newP) || !isLetterDigit(newConfirmP)){
                    ToastUtils.showToast(UpdatePwdInfoActivity.this,"您输入的密码格式不正确!");
                    return;

                }
                if(!newP.equals(newConfirmP)){
                    ToastUtils.showToast(UpdatePwdInfoActivity.this,"两次输入的密码不一致!");
                    return;
                }
                if(oldP.equals(newP) || oldP.equals(newConfirmP)){
                    ToastUtils.showToast(UpdatePwdInfoActivity.this,"新密码不能和旧密码一致!");
                    return;
                }


                try {
                    String member = (String) SPUtils.get(UpdatePwdInfoActivity.this,"username","");
                    String pwdOld = URLEncoder.encode(AesEncryptUtile.encrypt(oldP, key), "UTF-8");
                    String pwdNew = URLEncoder.encode(AesEncryptUtile.encrypt(newConfirmP, key), "UTF-8");
                    String type0 = URLEncoder.encode(AesEncryptUtile.encrypt(type, key), "UTF-8");
                    OkGo.<String>get(UrlRes.HOME2_URL +updatePasswordUrl)
                            .params("openId","123456")
                            .params("memberId",member)
                            .params("oldPassword",pwdOld)
                            .params("memberPwd",pwdNew)
                            .params("isUpdateDr",type0)//1修改 2不修改
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("修改密码",response.body());

                                    T.showShort(MyApp.getInstance(), "修改密码成功");
                                    netExit();

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);

                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

      /*  msg_notice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    type = "1";
                }else {
                    type = "2";
                }
            }
        });*/
        msg_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type.equals("2")){
                    msg_notice.setImageResource(R.mipmap.switch_open_icon);
                    type = "1";
                }else {
                    msg_notice.setImageResource(R.mipmap.switch_close_icon);
                    type = "2";
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_next:

                break;
            case R.id.tv_app_setting:
                finish();

                break;

        }
    }

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{8,20}$";
        //boolean isRight = isDigit && isLetter && str.matches(regex);
        boolean isRight = str.matches(regex);
        return isRight;
    }

    private void netExit() {

        OkGo.<String>post(UrlRes.HOME2_URL+UrlRes.Exit_Out)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("netExit", response.body());
                        initRelieve();

                        String update = (String) SPUtils.get(MyApp.getInstance(), "update", "");
                        String home01 = (String) SPUtils.get(MyApp.getInstance(), "home01", "");
                        String home02 = (String) SPUtils.get(MyApp.getInstance(), "home02", "");
                        String home03 = (String) SPUtils.get(MyApp.getInstance(), "home03", "");
                        String home04 = (String) SPUtils.get(MyApp.getInstance(), "home04", "");
                        String home05 = (String) SPUtils.get(MyApp.getInstance(), "home05", "");

                        //SPUtils.clear(getApplicationContext());
                        SPUtils.put(getApplicationContext(),"username","");
                        SPUtils.put(getApplicationContext(),"TGC","");
                        SPUtils.put(getApplicationContext(),"userId","");
                        SPUtils.put(getApplicationContext(),"rolecodes","");
                        SPUtils.put(getApplicationContext(),"count","0");

                        if(home01.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home01","1");
                        }
                        if(home02.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home02","1");
                        }
                        if(home03.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home03","1");
                        }
                        if(home04.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home04","1");
                        }
                        if(home05.equals("1")){
                            SPUtils.put(MyApp.getInstance(),"home05","1");
                        }

                        Intent intent = new Intent();
                        intent.putExtra("refreshService","dongtai");
                        intent.setAction("refresh");
                        sendBroadcast(intent);
                        Intent intent1 = new Intent(UpdatePwdInfoActivity.this,LoginActivity2.class);
                        intent1.putExtra("update","update");
                        startActivity(intent1);
                        FinishActivity.clearActivity();
                        finish();

                    }
                });

    }

    CurrencyBean currencyBean;
    private void initRelieve() {
        OkGo.<String>get(UrlRes.HOME4_URL + UrlRes.Relieve_Registration_Id)
                .tag("Jpush")
                .params("userId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .params("portalEquipmentMemberEquipmentId", (String) SPUtils.get(MyApp.getInstance(), "registrationId", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("JPush", response.body());
                        currencyBean = JSON.parseObject(response.body(), CurrencyBean.class);
                        if (currencyBean.isSuccess()) {
                            //解除绑定成功
                            Log.e("JPush", currencyBean.getMsg());
                        } else {
                            //解除绑定失败
                            Log.e("JPush", currencyBean.getMsg());
                        }
                    }
                });
    }

}
