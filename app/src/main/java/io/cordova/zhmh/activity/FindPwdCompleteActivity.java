package io.cordova.zhmh.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import io.cordova.zhmh.R;
import io.cordova.zhmh.utils.BaseActivity;
import io.cordova.zhmh.utils.FinishActivity;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class FindPwdCompleteActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_next)
    RelativeLayout rl_next;



    @BindView(R.id.tv_app_setting)
    ImageView tv_app_setting;


    @Override
    protected int getResourceId() {
        return R.layout.activity_find_pwd3;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_app_setting.setOnClickListener(this);
        rl_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_next:
                Intent intent = new Intent(FindPwdCompleteActivity.this,LoginActivity2.class);
                startActivity(intent);
                FinishActivity.clearActivity();
                finish();
                break;
            case R.id.tv_app_setting:
                //finish();
                Intent intent2 = new Intent(FindPwdCompleteActivity.this,LoginActivity2.class);
                startActivity(intent2);
                FinishActivity.clearActivity();
                finish();
                break;

        }
    }
}
