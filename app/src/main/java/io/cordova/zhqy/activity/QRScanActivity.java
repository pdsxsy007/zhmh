package io.cordova.zhqy.activity;

import android.content.Intent;
import android.util.Log;

import com.jwsd.libzxing.OnQRCodeListener;
import com.jwsd.libzxing.QRCodeManager;

import io.cordova.zhqy.R;
import io.cordova.zhqy.utils.BaseActivity;

/**
 * Created by Administrator on 2019/1/8 0008.
 */

public class QRScanActivity extends BaseActivity {
    @Override
    protected int getResourceId() {
        onScanQR();
        return R.layout.qr_scan_activity;

    }


    String controlLog;
    /**
     * 进入扫描二维码页面
     *
     * @param
     */
    public void onScanQR() {
//        QRCodeManager.getInstance().with(getActivity()).scanningQRCode(1);
        Log.e("tag  = ","点击了");
        QRCodeManager.getInstance()
                .with(this)
                .setReqeustType(0)
//                .setRequestCode(1001)
                .scanningQRCode(new OnQRCodeListener() {
                    @Override
                    public void onCompleted(String result) {
                        //controlLog.append("\n\n(结果)" + result);
                        Log.e("Tag = ",result);
                    }

                    @Override
                    public void onError(Throwable errorMsg) {
                        //   controlLog.append("\n\n(错误)" + errorMsg.toString());
                        Log.e("Tag = = ",errorMsg.toString());
                    }

                    @Override
                    public void onCancel() {
                        //controlLog.append("\n\n(取消)扫描任务取消了");
                        Log.e("Tag = = = ","扫描任务取消了");
                    }

                    /**
                     * 当点击手动添加时回调
                     *
                     * @param requestCode
                     * @param resultCode
                     * @param data
                     */
                    @Override
                    public void onManual(int requestCode, int resultCode, Intent data) {
                        Log.e("tag","点击了手动添加了");
                    }
                });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //注册onActivityResult
        QRCodeManager.getInstance().with(this).onActivityResult(requestCode, resultCode, data);
    }
}
