package io.cordova.zhqy.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import io.cordova.zhqy.utils.BaseActivity;
import io.cordova.zhqy.utils.MyApp;
import io.cordova.zhqy.utils.SPUtils;
import io.cordova.zhqy.utils.TimeUtils;
import io.cordova.zhqy.utils.ToastUtils;
import io.cordova.zhqy.widget.SlideRecyclerView;

/**
 * Created by Administrator on 2019/7/29.
 */

public class DeviceManagerActivity extends BaseActivity {

    @BindView(R.id.set_device)
    TextView setDevice;

    @BindView(R.id.set_device2)
    TextView xrdeviceDevice;
    @BindView(R.id.device_name)
    TextView deviceName;
//    @BindView(R.id.login_time)
//    TextView loginTime;
//    @BindView(R.id.iv_main_device)
//    ImageView mainDeviceIv;
    @BindView(R.id.recycler_view)
    SlideRecyclerView recyclerView;
    DeviceAdapter inventoryAdapter;
    LinearLayoutManager mLinearLayoutManager;
    List<DeviceListBean.Obj> dataList = new ArrayList<>();
    @Override
    protected int getResourceId() {
        return R.layout.activity_device_manager;
    }

    @Override
    protected void initView() {
        super.initView();
        mLinearLayoutManager = new LinearLayoutManager(DeviceManagerActivity.this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        getDeviceList();
        setDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDevice();
            }
        });
        xrdeviceDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDeviceList2();
            }
        });
        deviceName.setText(android.os.Build.MANUFACTURER + android.os.Build.DEVICE + "（本机）");
        String time = TimeUtils.timeStamp2Date( (String) SPUtils.get(MyApp.getInstance(),"time", Calendar.getInstance().getTimeInMillis()+""),"yyyy-MM-dd HH:mm:ss");

    }

    private void addDevice() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        String DEVICE_ID = tm.getDeviceId();
          Log.e("获取到的数据为",DEVICE_ID);

            OkGo.<String>post(UrlRes.HOME_URL+UrlRes.addTrustDevice)
                    .params("portalTrustDeviceNumber",DEVICE_ID)
                    .params("portalTrustDeviceType","android")
                    .params("portalTrustDeviceName", android.os.Build.DEVICE )
                    .params("portalTrustDeviceInfo",android.os.Build.MANUFACTURER + android.os.Build.DEVICE)
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
    private void addDeviceList2(){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        String DEVICE_ID = tm.getDeviceId();
        OkGo.<String>post(UrlRes.HOME_URL+UrlRes.addTrustDevice)
                .params("portalTrustDeviceNumber",DEVICE_ID)
                .params("portalTrustDeviceType","android")
                .params("portalTrustDeviceName", android.os.Build.DEVICE )
                .params("portalTrustDeviceInfo",android.os.Build.MANUFACTURER + android.os.Build.DEVICE)
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

    private void getDeviceList(){

        OkGo.<String>post(UrlRes.HOME_URL + UrlRes.trustDeviceList)
                .params("userName",(String) SPUtils.get(MyApp.getInstance(),"userId",""))
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("得到的设备列表为",response.body());
                        DeviceListBean listBean = JSON.parseObject(response.body(),DeviceListBean.class);

                        if(listBean.getObj()== null){
                            return;
                        }
                        dataList.clear();

                        dataList.addAll(listBean.getObj());

                        inventoryAdapter = new DeviceAdapter(DeviceManagerActivity.this,R.layout.item_device,dataList);

                        recyclerView.setAdapter(inventoryAdapter);
                        xrdeviceDevice.setVisibility(View.VISIBLE);
                        setDevice.setVisibility(View.VISIBLE);
                        inventoryAdapter.setOnDeleteClickListener(new DeviceAdapter.OnDeleteClickLister() {
                            @Override
                            public void onDeleteClick(View view, int position) {
                                inventoryAdapter.notifyDataSetChanged();
                                Log.e("当前的位子", position+ "");
//                                dataList.remove(position);
                                OkGo.<String>post(UrlRes.HOME_URL + UrlRes.updateTrustDevice)
                                        .params("portalTrustDeviceNumber", dataList.get(position).getPortalTrustDeviceNumber())
                                        .params("portalTrustDeviceType", dataList.get(position).getPortalTrustDeviceType())
                                        .params("portalTrustDeviceName",dataList.get(position).getPortalTrustDeviceName())
                                        .params("portalTrustDeviceInfo", dataList.get(position).getPortalTrustDeviceInfo())
                                        .params("portalTrustDeviceMaster",dataList.get(position).getPortalTrustDeviceMaster())
                                        .params("portalTrustDeviceDelete",1 )
                                        .params("portalTrustDeviceId",dataList.get(position).getPortalTrustDeviceId())
                                        .params("userName",(String) SPUtils.get(MyApp.getInstance(),"userId","")).execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        Log.e("删除的结果为：",response.body());
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
                                });
                            }
                        });

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });



    }
}
