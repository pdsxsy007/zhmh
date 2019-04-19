package io.cordova.zhqy.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.bean.CurrencyBean;
import io.cordova.zhqy.bean.GetServiceImgBean;
import io.cordova.zhqy.bean.UserMsgBean;

import io.cordova.zhqy.utils.BaseActivity2;
import io.cordova.zhqy.utils.CircleCrop;

import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.StringUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.ViewUtils;


/**
 * Created by Administrator on 2018/11/21 0021.
 */

public class MyDataActivity extends BaseActivity2 {

    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    //    @BindView(R.id.tv_company)
//    TextView tvCompany;
    @BindView(R.id.tv_student_number)
    TextView tvStudentNumber;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_grade)
    TextView tvGender;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_native_place)
    TextView tvNativePlace;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
//    @BindView(R.id.rv_user_data)
//    RecyclerView rvUserData;

    String mMobile;
    boolean allowedScan = false;
    @Override
    protected int getResourceId() {
        return R.layout.activity_my_data;
    }

    @OnClick({R.id.iv_user_head,R.id.ll_my_mobile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:

                //打开相册选择图片
//                setPermission();
//                if (allowedScan){
//                  selectImg();
//                }else {
//                    Toast.makeText(this,"请允许权限后尝试",Toast.LENGTH_SHORT).show();
//                    setPermission();
//                }
                break;
            case R.id.ll_my_mobile:
                //跳转至修改手机页面
//                Intent intent = new Intent(MyApp.getInstance(),MyDataChangesActivity.class);
//                if (!StringUtils.isEmpty(mMobile)){
//                    intent.putExtra("mMobile",mMobile);
//                }
//                startActivity(intent);
                break;
        }
    }


    @Override
    protected void initView() {
        super.initView();
        setToolBarBack();
        netWorkUserMsg();
    }

    private void setToolBarBack() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    UserMsgBean userMsgBean;
    private void netWorkUserMsg() {
        ViewUtils.createLoadingDialog(this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.User_Msg)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        userMsgBean = JSON.parseObject(response.body(), UserMsgBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (userMsgBean.isSuccess()) {
                            tvUserName.setText(userMsgBean.getObj().getModules().getMemberNickname());
//                            Glide.with(getApplication())
//                                    .load(UrlRes.HOME3_URL + userMsgBean.getObj().getModules().getMemberImage())
//                                    .transform(new CircleCrop(getApplicationContext()))
//                                    .error(R.mipmap.tabbar_user_pre)
//                                    .into(ivUserHead);
                            // tvStudentNumber.setText(userMsgBean.getObj().getModules().getMemberAcademicNumber());
                            tvStudentNumber.setText(userMsgBean.getObj().getModules().getMemberUsername());
                            tvName.setText(userMsgBean.getObj().getModules().getMemberNickname());
                            if (userMsgBean.getObj().getModules().getMemberSex() == 1) {
                                tvSex.setText("男");
                            } else {
                                tvSex.setText("女");
                            }


//                            tvNation.setText(userMsgBean.getObj().getModules().getMemberOtherNation());
//                            tvDepartment.setText(userMsgBean.getObj().getModules().getMemberOtherDepartment());
//                            tvMajor.setText(userMsgBean.getObj().getModules().getMemberOtherMajor());
//                            tvGender.setText(userMsgBean.getObj().getModules().getMemberOtherGrade());
//                            tvClass.setText(userMsgBean.getObj().getModules().getMemberOtherClass());
//                            tvBirthday.setText(userMsgBean.getObj().getModules().getMemberOtherBirthday());
//                            tvNativePlace.setText(userMsgBean.getObj().getModules().getMemberOtherNative());
                            tvMobile.setText(userMsgBean.getObj().getModules().getMemberPhone());
                            mMobile = userMsgBean.getObj().getModules().getMemberPhone();
                            netGetUserHead();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ViewUtils.cancelLoadingDialog();
                        T.showShort(MyApp.getInstance(), "没有数据哦，请稍后再试");
                    }
                });

    }


    private void netGetUserHead() {
//        ?memberId=admin&pwd=d632eeeb1548643667060e18656e0112
        try {
            String pwd = URLEncoder.encode(userMsgBean.getObj().getModules().getMemberPwd(),"UTF-8");
            String ingUrl =  "http://kys.zzuli.edu.cn/authentication/public/getHeadImg?memberId="+userMsgBean.getObj().getModules().getMemberUsername()+"&pwd="+pwd;

            Glide.with(getApplicationContext())
                    .load(ingUrl)
                    .transform(new CircleCrop(getApplicationContext()))
                    .error(R.mipmap.tabbar_user_pre)
                    .into(ivUserHead);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String img64Head;
    private String headPath;
    private static final int UP_Header = 55;//定义请求码常量
    private List<Uri> result;
    private List<String> path;
    private void selectImg() {
        Matisse.from(this)
                .choose(MimeType.ofImage())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(1)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.niu.qianyuan.jiancai.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(new GlideEngine())//图片加载引擎
                .forResult(UP_Header);//
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UP_Header&& resultCode == RESULT_OK) {
            result = Matisse.obtainResult(data);
            path = Matisse.obtainPathResult(data);
//            Glide.with(MyApp.getInstance())
//                        .load(path.get(0))
//                        .transform(new CircleCrop(getApplicationContext()))
//                        .error(R.mipmap.tabbar_user_pre)
//                        .into(ivUserHead);
            //  textView.setText(result.toString());
    //        L.e("path", path.toString());
//            for (int i = 0; i <path.size() ; i++) {
//                //   PictureUtil.compressImage(result.get(i).toString(), path.get(i), 30);
//                Glide.with(MyApp.getInstance())
//                        .load(path.get(i))
//                        .error(R.mipmap.tabbar_user_pre)
//                        .into(ivUserHead);
//
//                headPath = path.get(i);
//             //   img64Head = StringUtils.imageToBase64(path.get(i));
//               // Log.i("base64",img64Head);
//                upImg();//"data:image/png;base64,"
//            }
            upImg();//"data:image/png;base64,"
        }
    }

    GetServiceImgBean getServiceImgBean;
    //上传图片的到服务器
    private void upImg() {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Upload_Img)
                .tag(this)
                .isMultipart(true)
                .params( "file",new File(path.get(0)) )
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        //handleResponse(response);
                        Log.e("tag",response.body());
                        getServiceImgBean = JSON.parseObject(response.body(),GetServiceImgBean.class);
                        if (getServiceImgBean.isSuccess()){
                            gettImgUrl(getServiceImgBean.getObj());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.showShort(getApplicationContext(),"找不到服务器了，请稍后再试");
                    }
                });
    }
//  Log.e("UPImg",response.body());
//    /*获取服务器返回的Url*/
//    gettImgUrl();
    /*修改头像请求*/
    CurrencyBean currencyBean;//通用Bean
    private void gettImgUrl(final String obj) {
        OkGo.<String>post(UrlRes.HOME_URL+ UrlRes.Get_Img_uri)
                .tag(this)
                .params("imgageUrl",obj)
                .params("userId",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("gettImgUrl",response.body());
                        currencyBean = JSON.parseObject(response.body(),CurrencyBean.class);
                            if (!obj.isEmpty()){
                                Glide.with(MyApp.getInstance())
                                        .load(UrlRes.HOME3_URL+ obj)
                                        .transform(new CircleCrop(getApplicationContext()))
                                        .error(R.mipmap.tabbar_user_pre)
                                        .into(ivUserHead);
                            }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        T.showShort(getApplicationContext(),"找不到服务器了，请稍后再试");
                    }
                });
    }

    //获取服务器返回的url
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
                            //   Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Log.e("用户拒绝了该权限", permission.name + " is denied. More info should be provided.");
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            //   Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            //   Log.d(TAG, permission.name + " is denied.");
                            Log.e("用户拒绝了该权限", permission.name + permission.name + " is denied.");
                        }
                    }
                });
    }

}
