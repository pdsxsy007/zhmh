package io.cordova.zhqy.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
public class ToastUtils {

    private static Toast toast;
    // 是否显示
    private static boolean isShow = true;
    private static ImageView mImgIcon;
    private static TextView mTxtMsg;
    private static LinearLayout mLytShow;
    @SuppressLint("WrongConstant")
    public static void showToast(Context context, String message) {
        if (isShow) {
            toast = new Toast(context);
            // 消息内容
            mTxtMsg = new TextView(context);
            LinearLayout.LayoutParams lParams1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mTxtMsg.setTextColor(Color.parseColor("#ffffff"));
            mTxtMsg.setTextSize(13);
            mTxtMsg.setPadding(25, 20, 25, 20);

            mTxtMsg.setLayoutParams(lParams1);

            mLytShow = new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLytShow.setOrientation(LinearLayout.HORIZONTAL);
            mLytShow.setLayoutParams(params);

            int roundRadius = 8; // 8dp 圆角半径
            int fillColor = Color.parseColor("#AB000000");// 内部填充颜色
            GradientDrawable gd = new GradientDrawable();// 创建drawable
            gd.setCornerRadius(roundRadius);
            gd.setColor(fillColor);

            mLytShow.setBackgroundDrawable(gd);
            mLytShow.addView(mTxtMsg);

            if (message != null) {
                mTxtMsg.setText(message);
            }
            toast.setView(mLytShow);
            toast.setGravity(Gravity.CENTER, 0,
                    0);
            toast.setDuration(2);
            toast.show();
        }
    }

}
