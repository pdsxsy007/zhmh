package io.cordova.zhqy.activity;

import io.cordova.zhqy.utils.BaseActivity;

/**
 * Created by Administrator on 2018/11/15 0015.
 */

public class ZxingScanningActivity extends BaseActivity {
    @Override
    protected int getResourceId() {
        return 0;
    }
//    @BindView(R.id.click)
//    Button click;
//    @BindView(R.id.tv1)
//    TextView tv1;
//    private int REQUEST_CODE_SCAN = 111;
//
//    @Override
//    protected int getResourceId() {
//        return R.layout.scanning_activity;
//    }
//
//    @Override
//    protected void initData() {
//        super.initData();
//        setPermission();
//    }
//
//    @OnClick(R.id.click)
//    public void onViewClicked() {
//
//        Intent intent = new Intent(MyApp.getInstance(), CaptureActivity.class);
//        startActivityForResult(intent, REQUEST_CODE_SCAN);
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // 扫描二维码/条码回传
//        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
//            if (data != null) {
//                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                tv1.setText("扫描结果为：" + content);
//            }
//        }
//    }
//
//    private void setPermission() {
//        //同时请求多个权限
//        RxPermissions rxPermission = new RxPermissions(this);
//        rxPermission
//                .requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.CAMERA
//                )
//                .subscribe(new Consumer<Permission>() {
//                    @Override
//                    public void accept(Permission permission) throws Exception {
//                        if (permission.granted) {
//                            // 用户已经同意该权限
//                            L.e("用户已经同意该权限", permission.name + " is granted.");
//                            //   Log.d(TAG, permission.name + " is granted.");
//                        } else if (permission.shouldShowRequestPermissionRationale) {
//                            L.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
//                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");
//                        } else {
//                            // 用户拒绝了该权限，并且选中『不再询问』
//                            //   Log.d(TAG, permission.name + " is denied.");
//                            L.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
//                        }
//                    }
//                });
//    }
}
