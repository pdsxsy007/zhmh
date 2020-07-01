package io.cordova.zhmh.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.net.URLEncoder;

import butterknife.BindView;
import io.cordova.zhmh.R;
import io.cordova.zhmh.UrlRes;
import io.cordova.zhmh.bean.VerCodeBean;
import io.cordova.zhmh.utils.AesEncryptUtile;
import io.cordova.zhmh.utils.BaseActivity;
import io.cordova.zhmh.utils.FinishActivity;
import io.cordova.zhmh.utils.JsonUtil;
import io.cordova.zhmh.utils.ToastUtils;

import static io.cordova.zhmh.UrlRes.updatePasswordUrl;
import static io.cordova.zhmh.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class FindPwdNextActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_next)
    RelativeLayout rl_next;

    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @BindView(R.id.et_01)
    EditText et_01;

    @BindView(R.id.et_02)
    EditText et_02;

    @BindView(R.id.tv_app_setting)
    ImageView tv_app_setting;

    String member;
    String code;
    String typeU;
    @Override
    protected int getResourceId() {
        return R.layout.activity_find_pwd2;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_app_setting.setOnClickListener(this);
        rl_next.setOnClickListener(this);
        rl_back.setOnClickListener(this);

        member = getIntent().getStringExtra("member");
        code = getIntent().getStringExtra("verificationCode");
        typeU = getIntent().getStringExtra("type");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_next:

                String pwd01 = et_01.getText().toString().trim();
                String pwd02 = et_02.getText().toString().trim();
                if(pwd01.equals("") || pwd02.equals("")){
                    ToastUtils.showToast(this,"请输入密码");
                    return;
                }

                if(!pwd01.equals(pwd02)){
                    ToastUtils.showToast(this,"两次输入的密码不一致");
                    return;
                }

                if(!isLetterDigit(pwd01) || !isLetterDigit(pwd02)){
                    ToastUtils.showToast(FindPwdNextActivity.this,"您输入的密码格式不正确!");
                    return;

                }

                chagePwd(pwd01);

                break;
            case R.id.tv_app_setting:
                finish();
                break;
            case R.id.rl_back:
                finish();
                break;
        }
    }
    String updateType;
    private void chagePwd(String pwd01) {
        try {
            String pwd = URLEncoder.encode(AesEncryptUtile.encrypt(pwd01, key), "UTF-8");
            String type = URLEncoder.encode(AesEncryptUtile.encrypt("1", key), "UTF-8");
            if(typeU.equals("3")){//邮箱
                updateType = URLEncoder.encode(AesEncryptUtile.encrypt("3", key), "UTF-8");
            }else if(typeU.equals("2")){//手机
                updateType = URLEncoder.encode(AesEncryptUtile.encrypt("2", key), "UTF-8");
            }
            OkGo.<String>get(UrlRes.HOME2_URL +updatePasswordUrl)
                    .params("openId","123456")
                    .params("memberId",member)
                    .params("memberPwd",pwd)
                    .params("code",code)
                    .params("isUpdateDr",type)
                    .params("type",updateType)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            //Log.e("校验验证吗",response.body());

                            VerCodeBean verCodeBean = JsonUtil.parseJson(response.body(),VerCodeBean.class);
                            boolean success = verCodeBean.getSuccess();
                            if(success){
                                Intent intent = new Intent(FindPwdNextActivity.this,FindPwdCompleteActivity.class);
                                startActivity(intent);
                                FinishActivity.addActivity(FindPwdNextActivity.this);
                            }else {
                                ToastUtils.showToast(FindPwdNextActivity.this,verCodeBean.getMsg());
                            }

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

}
