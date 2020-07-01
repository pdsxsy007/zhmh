package io.cordova.zhmh.activity;


import android.view.View;
import android.widget.EditText;

import butterknife.BindView;

import butterknife.OnClick;
import io.cordova.zhmh.R;
import io.cordova.zhmh.utils.BaseActivity2;
import io.cordova.zhmh.utils.StringUtils;

/**
 * Created by Administrator on 2018/11/29 0029.
 */

public class MyDataChangesActivity extends BaseActivity2 {
    @BindView(R.id.tv_mobile)
    EditText tvMobile;

    @Override
    protected int getResourceId() {
        return R.layout.change_data_activity;
    }

    String mMobile;

    @Override
    protected void initView() {
        super.initView();
        mMobile = getIntent().getStringExtra("mMobile");

        if (!StringUtils.isEmpty(mMobile)) {
            tvMobile.setText(mMobile);
        }
    }


    @OnClick({R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_ok:

                if (!StringUtils.getEditTextData(tvMobile).isEmpty()&& !StringUtils.getEditTextData(tvMobile).equals(mMobile)){
                    //网络请求  修改手机号
                }else {
                    //提示用户输入符合要求的数据
                }

                break;
        }
    }
}
