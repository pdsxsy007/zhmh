package io.cordova.zhqy.utils;

import io.cordova.zhqy.R;

/**
 * Activity的基类
 *
 * @author lilingfei
 */

public abstract class BaseActivity2 extends BaseActivity {
    @Override
    protected void initSystemBar() {
        super.initSystemBar();
        //SystemBarTintUtils.initSystemBarColor(this, R.color.title_bar_bg);
    }



}
