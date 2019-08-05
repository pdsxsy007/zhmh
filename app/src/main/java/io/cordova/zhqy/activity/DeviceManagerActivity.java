package io.cordova.zhqy.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.cordova.zhqy.R;
import io.cordova.zhqy.UrlRes;
import io.cordova.zhqy.adapter.DeviceAdapter;
import io.cordova.zhqy.bean.AddTrustBean;
import io.cordova.zhqy.bean.DeviceListBean;
import io.cordova.zhqy.bean.UserMsgBean;
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.DensityUtil;
import io.cordova.zhqy.utils.MobileInfoUtils;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.ScreenSizeUtils;
import io.cordova.zhqy.utils.SystemInfoUtils;
import io.cordova.zhqy.utils.T;
import io.cordova.zhqy.utils.TimeUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.utils.ViewUtils;
import io.cordova.zhqy.widget.MyDialog;
import io.cordova.zhqy.widget.SlideRecyclerView;

/**
 * Created by Administrator on 2019/7/29.
 */

public class DeviceManagerActivity extends BaseActivity {

    @BindView(R.id.set_device)
    TextView setDevice;
    @BindView(R.id.tv_app_setting)
    ImageView back;
    @BindView(R.id.set_device2)
    TextView xrdeviceDevice;
    @BindView(R.id.device_name)
    TextView deviceName;
    MyDialog m_Dialog;
    @BindView(R.id.recycler_view)
    SlideRecyclerView recyclerView;
    DeviceAdapter inventoryAdapter;
    LinearLayoutManager mLinearLayoutManager;
    List<DeviceListBean.Obj> dataList = new ArrayList<>();
    boolean isdelete = false;
    String mobile;
    @Override
    protected int getResourceId() {
        return R.layout.activity_device_manager;
    }

    @Override
    protected void initView() {
        super.initView();
        mLinearLayoutManager = new LinearLayoutManager(DeviceManagerActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        setDevice.setVisibility(View.VISIBLE);
        xrdeviceDevice.setVisibility(View.VISIBLE);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        getDeviceList();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               getData();

//                addDevice();

            }
        });
        xrdeviceDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDeviceList2();
            }
        });
        deviceName.setText(android.os.Build.MANUFACTURER +" "+ SystemInfoUtils.getDeviceModel() + "（本机）");
        String time = TimeUtils.timeStamp2Date((String) SPUtils.get(MyApp.getInstance(), "time", Calendar.getInstance().getTimeInMillis() + ""), "yyyy-MM-dd HH:mm:ss");
        registerBoradcastReceiver();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            addDevice();
        }
    }

    /**
     * 添加主设备
     */
    private void addDevice() {

        String DEVICE_ID = MobileInfoUtils.getIMEI(this);
          Log.e("获取到的数据为",DEVICE_ID);

            OkGo.<String>post(UrlRes.HOME_URL+UrlRes.addTrustDevice)
                    .params("portalTrustDeviceNumber",DEVICE_ID)
                    .params("portalTrustDeviceType","android")
                    .params("portalTrustDeviceName", android.os.Build.DEVICE )
                    .params("portalTrustDeviceInfo",android.os.Build.MANUFACTURER + "  "+SystemInfoUtils.getDeviceModel())
                    .params("portalTrustDeviceMaster",1)
                    .params("portalTrustDeviceDelete",0)
                    .params("userName",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            AddTrustBean addTrustBean = JSON.parseObject(response.body(),AddTrustBean.class);
                            if(addTrustBean.isSuccess()){
                                ToastUtils.showToast(DeviceManagerActivity.this,addTrustBean.getMsg());
                                getDeviceList();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);

                        }
                    }
            );


    }

    private  void showDialog(){

        m_Dialog = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bind_main, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        RelativeLayout rl_sure1 = view.findViewById(R.id.rl_sure1);
        TextView content = view.findViewById(R.id.message_yanzheng);
        String phone = mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
        content.setText("设置主设备需要进行短信验证,是否向" + phone +"发送验证码");
        int width = ScreenSizeUtils.getWidth(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(this,24),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        m_Dialog.setContentView(view, layoutParams);
        m_Dialog.show();
        m_Dialog.setCanceledOnTouchOutside(true);
        rl_sure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
            }
        });
        rl_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
                Intent intent = new Intent(DeviceManagerActivity.this, CodeBind2Activity.class);
                intent.putExtra("phone", mobile);
                startActivityForResult(intent, 1);
            }
        });
    }
    private  void showDialog2(){

        m_Dialog = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bind_error, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        int width = ScreenSizeUtils.getWidth(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(this,24),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        m_Dialog.setContentView(view, layoutParams);
        m_Dialog.show();
        m_Dialog.setCanceledOnTouchOutside(true);
        rl_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();

            }
        });
    }
    /**
     * 添加信任设备
     */
    private void addDeviceList2(){
       //
        String DEVICE_ID = MobileInfoUtils.getIMEI(this);
        OkGo.<String>post(UrlRes.HOME_URL+UrlRes.addTrustDevice)
                .params("portalTrustDeviceNumber",DEVICE_ID)
                .params("portalTrustDeviceType","android")
                .params("portalTrustDeviceName", android.os.Build.DEVICE )
                .params("portalTrustDeviceInfo",android.os.Build.MANUFACTURER + " " +SystemInfoUtils.getDeviceModel())
                .params("portalTrustDeviceMaster",0)
                .params("portalTrustDeviceDelete",0)
                .params("userName",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(Response<String> response) {
                                 AddTrustBean addTrustBean = JSON.parseObject(response.body(),AddTrustBean.class);
                                 if(addTrustBean.isSuccess()){
                                     ToastUtils.showToast(DeviceManagerActivity.this,addTrustBean.getMsg());
                                     getDeviceList();
                                 }
                             }

                             @Override
                             public void onError(Response<String> response) {
                                 super.onError(response);

                             }
                         }
                );


    }
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("xianshi");
        //注册广播
       registerReceiver(broadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();


            String DEVICE_ID = MobileInfoUtils.getIMEI(DeviceManagerActivity.this);


            if(action.equals("xianshi")){
              if(!intent.getStringExtra("data1").equals(DEVICE_ID)){

                  setDevice.setVisibility(View.VISIBLE);
                  isdelete = false;

              }else{
                  setDevice.setVisibility(View.GONE);
                    xrdeviceDevice.setVisibility(View.GONE);
                  isdelete = true;
              }

            }
        }
    };
    private void getData() {
        ViewUtils.createLoadingDialog(DeviceManagerActivity.this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.User_Msg)
                .params("userId", (String) SPUtils.get(MyApp.getInstance(), "userId", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserMsgBean   userMsgBean = JSON.parseObject(response.body(), UserMsgBean.class);
                        ViewUtils.cancelLoadingDialog();
                        if (userMsgBean.isSuccess()) {
                            if (userMsgBean.getObj() != null) {



                                mobile = userMsgBean.getObj().getModules().getMemberPhone();

                                if(mobile == null || mobile.equals("")){
                                   showDialog2();
                                    return;
                                }else{
                                    showDialog();
                                }


                            } else {
//                                ToastUtils.showToast(DeviceManagerActivity.this, "获取个人信息失败!");
//                                ViewUtils.cancelLoadingDialog();
                            }
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
    private void getDeviceList(){

        final String DEVICE_ID = MobileInfoUtils.getIMEI(this);
        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.trustDeviceList)
                .params("userName",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("得到的设备列表为",response.body());
                        DeviceListBean listBean = JSON.parseObject(response.body(),DeviceListBean.class);

                        if(listBean.getObj()== null){
                            dataList.clear();
                            setDevice.setVisibility(View.VISIBLE);
                            xrdeviceDevice.setVisibility(View.VISIBLE);
                            return;
                        }

                        dataList.clear();
                        dataList.addAll(listBean.getObj());

                        inventoryAdapter = new DeviceAdapter(DeviceManagerActivity.this,R.layout.item_device,dataList);

                        recyclerView.setAdapter(inventoryAdapter);
                        for(int i=0;i<dataList.size();i++){
                            if(DEVICE_ID.equals(dataList.get(i).getPortalTrustDeviceNumber())) {
                                xrdeviceDevice.setVisibility(View.GONE);

                            }
                        }

                                inventoryAdapter.setOnDeleteClickListener(new DeviceAdapter.OnDeleteClickLister() {
                                    @Override
                                    public void onDeleteClick(View view, int position) {
                                        inventoryAdapter.notifyDataSetChanged();
                                        Log.e("当前的位子", position+ "");
                                            if(isdelete == true){
                                                if(DEVICE_ID.equals(dataList.get(position).getPortalTrustDeviceNumber())) {
                                                    ToastUtils.showToast(getApplicationContext(),"不能删除主设备");
                                                }else{
                                                    OkGo.<String>post(UrlRes.HOME_URL + UrlRes.updateTrustDevice)
                                                            .params("portalTrustDeviceNumber", dataList.get(position).getPortalTrustDeviceNumber())
                                                            .params("portalTrustDeviceType", dataList.get(position).getPortalTrustDeviceType())
                                                            .params("portalTrustDeviceName", dataList.get(position).getPortalTrustDeviceName())
                                                            .params("portalTrustDeviceInfo", dataList.get(position).getPortalTrustDeviceInfo())
                                                            .params("portalTrustDeviceMaster", dataList.get(position).getPortalTrustDeviceMaster())
                                                            .params("portalTrustDeviceDelete", 1)
                                                            .params("portalTrustDeviceId", dataList.get(position).getPortalTrustDeviceId())
                                                            .params("userName", (String) SPUtils.get(MyApp.getInstance(), "userId", "")).execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(Response<String> response) {
                                                            Log.e("删除的结果为：", response.body());
                                                            AddTrustBean addTrustBean = JSON.parseObject(response.body(), AddTrustBean.class);
                                                            if (addTrustBean.isSuccess()) {
                                                                ToastUtils.showToast(DeviceManagerActivity.this, addTrustBean.getMsg());
                                                                getDeviceList();
                                                            }

                                                        }


                                                        @Override
                                                        public void onError(Response<String> response) {
                                                            super.onError(response);
                                                        }
                                                    });
                                                }

                                            }else{
                                                recyclerView.closeMenu();
                                                ToastUtils.showToast(getApplicationContext(),"当前设备无删除权限");
                                                recyclerView.closeMenu();

                                            }

                                        }

//                                    }
                                });




                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });



    }
}
