package io.cordova.zhqy.utils.WebUtil;

/**
 * Created by Administrator on 2018/12/3 0003.
 */
import android.view.KeyEvent;

/**
 * @author cenxiaozhong
 * @since 1.0.0
 */
public interface IEventHandler {

    boolean onKeyDown(int keyCode, KeyEvent event);


    boolean back();
}
