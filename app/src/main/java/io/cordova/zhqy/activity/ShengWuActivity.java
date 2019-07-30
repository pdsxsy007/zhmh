package io.cordova.zhqy.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import io.cordova.zhqy.Main2Activity;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.AddFaceBean;
import io.cordova.zhqy.bean.Constants;
import io.cordova.zhqy.bean.FaceBean;
import io.cordova.zhqy.fingerprint.FingerprintHelper;
import io.cordova.zhqy.utils.AesEncryptUtile;
import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.BaseActivity3;
import io.cordova.zhqy.utils.BitmapHelper;
import io.cordova.zhqy.utils.FinishActivity;
import io.cordova.zhqy.utils.JsonUtil;
import io.cordova.zhqy.utils.LQRPhotoSelectUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtil;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.utils.fingerUtil.FingerprintUtil;
import io.cordova.zhqy.widget.finger.CommonTipDialog;
import io.cordova.zhqy.widget.finger.FingerprintVerifyDialog;
import io.reactivex.functions.Consumer;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static io.cordova.zhqy.utils.AesEncryptUtile.key;

/**
 * Created by Administrator on 2019/6/19 0019.
 */

public class ShengWuActivity extends BaseActivity2 implements View.OnClickListener, FingerprintHelper.SimpleAuthenticationCallback, PopupWindow.OnDismissListener {

    private boolean isOpen;
    private FingerprintHelper helper;
    private FingerprintVerifyDialog fingerprintVerifyDialog;
    private CommonTipDialog fingerprintVerifyErrorTipDialog;
    private CommonTipDialog closeFingerprintTipDialog;
    private int type = 0;

    @BindView(R.id.iv_fingerprint_login_switch)
    ImageView iv_fingerprint_login_switch;

    @BindView(R.id.ll_finger)
    LinearLayout ll_finger;

    @BindView(R.id.ll_shengwu)
    LinearLayout ll_shengwu;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private PopupWindow popupWindow;

    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    String userId;

    private static final int ALBUM_REQUEST_CODE = 1;

    private static final int CAMERA_REQUEST_CODE = 2;

    private static final String ROOT_NAME = "UPLOAD_CACHE";

    @Override
    protected int getResourceId() {
        return R.layout.activity_shengwu;
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText("生物识别");

        userId = (String) SPUtils.get(MyApp.getInstance(), "userId", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            helper = FingerprintHelper.getInstance();
            helper.init(getApplicationContext());
            helper.setCallback(this);
            if (helper.checkFingerprintAvailable(this) != -1) {
                //设备支持指纹登录
                iv_fingerprint_login_switch.setEnabled(true);
            }else {
                ToastUtils.showToast(this,"设备不支持指纹登录");
            }
        }else {
            ll_finger.setVisibility(View.GONE);
        }
        isOpen = SPUtil.getInstance().getBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN);
        setSwitchStatus();
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调

                //ToastUtils.showToast(ShengWuActivity.this,outputFile.getAbsolutePath());


            }
        }, true);


//        ll_shengwu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openPopupWindow(view);
//            }
//        });

        ll_shengwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cameraTask();
            }
        });

        registerBoradcastReceiver();
    }
    @Override
    protected void onResume() {
        super.onResume();
        String isloadingUp = (String) SPUtils.get(ShengWuActivity.this, "isloadingUp", "");
        if(!isloadingUp .equals("")){
            SPUtils.put(getApplicationContext(),"isloadingUp","");
            ViewUtils.createLoadingDialog2(ShengWuActivity.this,true,"人脸上传中");

        }

    }

    private static final int RC_CAMERA_PERM = 123;

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!

            Intent intent = new Intent(ShengWuActivity.this, UpdateFaceActivity.class);

            startActivity(intent);
            ;//调用相机照相
        } else {//没有相应权限，获取相机权限
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "获取照相机权限",
                    RC_CAMERA_PERM, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
    private int imageid = 0;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("facedata")){
                String UpdateFaceActivity = intent.getStringExtra("UpdateFaceActivity");

                if(imageid == 0){
                    if(UpdateFaceActivity != null){
                        imageid = 1;
                        String bitmap  = (String) SPUtils.get(ShengWuActivity.this, "bitmap2", "");;
                        upToServer(bitmap);
                    }else {
                        imageid = 0;
                    }
                }
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("facedata");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }
    private void setSwitchStatus() {
        iv_fingerprint_login_switch.setImageResource(isOpen ? R.mipmap.switch_open_icon : R.mipmap.switch_close_icon);
    }

    @Override
    protected void initListener() {
        super.initListener();
        iv_fingerprint_login_switch.setOnClickListener(this);
    }
    boolean allowedScan = false;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_fingerprint_login_switch:
                dealOnOff(isOpen);
                break;
            case R.id.ll_01:
               /* PermissionGen.with(ShengWuActivity.this)
                        .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                        ).request();*/
                popupWindow.dismiss();
                if (allowedScan){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempCameraFile()));
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                }else {
                    setPermission();
                }



                break;
            case R.id.ll_02:
             /*   PermissionGen.needPermission(ShengWuActivity.this,
                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                );
                */

                popupWindow.dismiss();
                if (allowedScan){
                    Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
                    startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);
                }else {
                    setPermission();
                }

                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    private void dealOnOff(boolean isOpen) {
        if (isOpen) {
            type = 0;
            showCloseFingerprintTipDialog();
        } else {
            openFingerprintLogin();
        }
    }

    /**
     * @description 开启指纹登录功能
     * @author HaganWu
     * @date 2019/1/29-10:20
     */
    private void openFingerprintLogin() {
        Log.e("hagan", "openFingerprintLogin");

        helper.generateKey();
        if (fingerprintVerifyDialog == null) {
            fingerprintVerifyDialog = new FingerprintVerifyDialog(this);
        }
        fingerprintVerifyDialog.setContentText("请验证指纹");
        fingerprintVerifyDialog.setOnCancelButtonClickListener(new FingerprintVerifyDialog.OnDialogCancelButtonClickListener() {
            @Override
            public void onCancelClick(View v) {
                helper.stopAuthenticate();
            }
        });
        fingerprintVerifyDialog.show();
        helper.setPurpose(KeyProperties.PURPOSE_ENCRYPT);
        helper.authenticate();
    }



    private void showCloseFingerprintTipDialog() {
        if (closeFingerprintTipDialog == null) {
            closeFingerprintTipDialog = new CommonTipDialog(this);
        }
        closeFingerprintTipDialog.setContentText("确定关闭指纹登录?");
        closeFingerprintTipDialog.setSingleButton(false);
        closeFingerprintTipDialog.setOnDialogButtonsClickListener(new CommonTipDialog.OnDialogButtonsClickListener() {
            @Override
            public void onCancelClick(View v) {

            }

            @Override
            public void onConfirmClick(View v) {
                closeFingerprintLogin();
            }
        });
        closeFingerprintTipDialog.show();
    }


    @Override
    public void onAuthenticationSucceeded(String value) {
        SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, true);
        if(type == 0){
            if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
                fingerprintVerifyDialog.dismiss();
                Toast.makeText(this, "指纹登录已开启", Toast.LENGTH_SHORT).show();
                isOpen = true;
                setSwitchStatus();
                saveLocalFingerprintInfo();
            }
        }else {
            if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
                fingerprintVerifyDialog.dismiss();
                Toast.makeText(this, "指纹登录已关闭", Toast.LENGTH_SHORT).show();
                isOpen = false;
                SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false);
                setSwitchStatus();
                helper.closeAuthenticate();
            }
        }

    }

    @Override
    public void onAuthenticationFail() {
        showFingerprintVerifyErrorInfo("指纹不匹配");
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.dismiss();
        }
        showTipDialog(errorCode, errString.toString());
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        showFingerprintVerifyErrorInfo(helpString.toString());
    }

    /**
     * @description 关闭指纹登录功能
     * @author HaganWu
     * @date 2019/1/24-14:41
     */
    private void closeFingerprintLogin() {
        type = 1;
        openFingerprintLogin();
       /* isOpen = false;
        SPUtil.getInstance().putBoolean(Constants.SP_HAD_OPEN_FINGERPRINT_LOGIN, false);
        setSwitchStatus();
        helper.closeAuthenticate();*/
    }




    private void saveLocalFingerprintInfo() {
        SPUtil.getInstance().putString(Constants.SP_LOCAL_FINGERPRINT_INFO, FingerprintUtil.getFingerprintInfoString(getApplicationContext()));
    }

    private void showFingerprintVerifyErrorInfo(String info) {
        if (fingerprintVerifyDialog != null && fingerprintVerifyDialog.isShowing()) {
            fingerprintVerifyDialog.setContentText(info);
        }
    }

    private void showTipDialog(int errorCode, CharSequence errString) {
        if (fingerprintVerifyErrorTipDialog == null) {
            fingerprintVerifyErrorTipDialog = new CommonTipDialog(this);
        }
        //fingerprintVerifyErrorTipDialog.setContentText("errorCode:" + errorCode + "," + errString);
        fingerprintVerifyErrorTipDialog.setContentText(errString+"");
        fingerprintVerifyErrorTipDialog.setSingleButton(true);
        fingerprintVerifyErrorTipDialog.setOnSingleConfirmButtonClickListener(new CommonTipDialog.OnDialogSingleConfirmButtonClickListener() {
            @Override
            public void onConfirmClick(View v) {
                helper.stopAuthenticate();
            }
        });
        fingerprintVerifyErrorTipDialog.show();
    }


    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:       // 照相机返回结果
                    File file = composBitmap(mTempCameraFile);
                    //sendImage(file);
//                    upToServer(file);
                    //ToastUtils.showToast(ShengWuActivity.this,file+"");
                    break;
                case ALBUM_REQUEST_CODE:        // 相册返回结果
                    Uri uri = data.getData();
                    File file2 = BitmapHelper.decodeUriAsFile(ShengWuActivity.this, uri);
                    file = composBitmap(file2);
//                    upToServer(file);
                    break;
            }
        }
     if(resultCode == 1){
            String imageData = data.getStringExtra("data");
            upToServer(imageData);
     }
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-i轻工大-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
//                intent.setData(Uri.parse("package:" + ShengWuActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }


    private void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(this).inflate(R.layout.view_popupwindow, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }

    private void setOnPopupViewClick(View view) {
        TextView tv_pick_phone, tv_pick_zone, tv_cancel;
        tv_pick_phone = (TextView) view.findViewById(R.id.ll_01);
        tv_pick_zone = (TextView) view.findViewById(R.id.ll_02);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_pick_phone.setOnClickListener(this);
        tv_pick_zone.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }


    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    private File composBitmap(File file) {
        Bitmap bitmap = BitmapHelper.revisionImageSize(file);
        return BitmapHelper.saveBitmap2file(this, bitmap);
    }

    private File mTempCameraFile;
    private File getTempCameraFile() {
        if (mTempCameraFile == null)
            mTempCameraFile = getTempMediaFile();
        return mTempCameraFile;
    }

    /**
     * 获取相机的file
     */
    public File getTempMediaFile() {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String fileName = getTempMediaFileName();
            file = new File(fileName);
        }
        return file;
    }

    public String getTempMediaFileName() {
        return getParentPath() + "image" + System.currentTimeMillis() + ".jpg";
    }

    private String getParentPath() {
        String parent = Environment.getExternalStorageDirectory()
                + File.separator + ROOT_NAME + File.separator;
        mkdirs(parent);
        return parent;
    }

    public boolean mkdirs(String path) {
        File file = new File(path);
        return !file.exists() && file.mkdirs();
    }


    public void upToServer(String sresult){
        OkGo.<String>post(UrlRes.HOME2_URL+ UrlRes.addFaceUrl)
                .params( "openId","123456")
                .params( "memberId",userId)
                .params( "img",sresult )
                .params( "code","" )
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e("上传图片",response.body());
                        AddFaceBean faceBean = JsonUtil.parseJson(response.body(),AddFaceBean.class);
                        boolean success = faceBean.getSuccess();
                        String msg = faceBean.getMsg();
                        ViewUtils.cancelLoadingDialog();
                        if(success == true){
                            ToastUtils.showToast(ShengWuActivity.this,msg);
                        }else {
                            ToastUtils.showToast(ShengWuActivity.this,msg);
                            imageid = 0;
                        }
                        ViewUtils.cancelLoadingDialog();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(getApplicationContext(),"找不到服务器了，请稍后再试");
                        imageid = 0;
                    }
                });
    }

    /**请求权限*/
    private void setPermission() {
        //同时请求多个权限
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.e("用户已经同意该权限", permission.name + " is granted.");
//                            Intent intent = new Intent(MyApp.getInstance(), QRScanActivity.class);
//                            startActivity(intent);
                            allowedScan = true;
                            //   Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Log.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");
                            allowedScan = false;
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   Log.d(TAG, permission.name + " is denied.");
                            Log.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
                            allowedScan = true;
                        }
                    }
                });
    }


    /**
     *
     * @param requestCode
     * @param permissions 请求的权限
     * @param grantResults 请求权限返回的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            // camear 权限回调

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                // 表示用户授权
                Toast.makeText(getApplicationContext(), " user Permission" , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ShengWuActivity.this,UpdateFaceActivity.class);

                startActivity(intent);


            } else {

                //用户拒绝权限
                Toast.makeText(getApplicationContext(), " no Permission" , Toast.LENGTH_SHORT).show();

            }



        }

    }

}
