package io.cordova.zhqy.adapter;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import io.cordova.zhqy.R;
import io.cordova.zhqy.activity.DeviceManagerActivity;
import io.cordova.zhqy.bean.DeviceListBean;
import io.cordova.zhqy.bean.LogInTypeBean;
import io.cordova.zhqy.utils.ScreenSizeUtils;
import io.cordova.zhqy.utils.TimeUtils;
import io.cordova.zhqy.utils.ToastUtils;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class DeviceAdapter extends CommonAdapter<DeviceListBean.Obj> {
    Context mContext;
    private OnDeleteClickLister mDeleteClickListener;
    String device;
    String deviceid;
    public DeviceAdapter(Context context, int layoutId, List<DeviceListBean.Obj> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }



    @Override
    protected void convert(ViewHolder holder, final DeviceListBean.Obj obj, final int position) {

        holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDeleteClickListener != null) {
                    mDeleteClickListener.onDeleteClick(view, position);
                }
            }
        });
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
        View view = holder.getView(R.id.tv_delete);

        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
            device = obj.getPortalTrustDeviceInfo();
            getDeviceName();
            deviceid = obj.getPortalTrustDeviceMaster();
            getDeviceId();
        }

        if(obj.getPortalTrustDeviceMaster().equals("1")){
            holder.setText(R.id.device_name,obj.getPortalTrustDeviceInfo() + "(主设备)");
            if(DEVICE_ID.equals(obj.getPortalTrustDeviceNumber())){{
                holder.getView(R.id.tv_main).setVisibility(View.VISIBLE);
            }}
        }else{
            holder.setText(R.id.device_name,obj.getPortalTrustDeviceInfo());
        }

        holder.setText(R.id.loginTime, "最近登录：" + TimeUtils.timeStamp2Date(obj.getPortalTrustDeviceLastLoginTime(),"yyyy-MM-dd HH:mm:ss"));

        Intent intent2 = new Intent();
        intent2.setAction("xianshi");
        if(obj.getPortalTrustDeviceMaster().equals("1"))

//        intent2.putExtra("state",obj.getPortalTrustDeviceNumber());
        intent2.putExtra("data1",obj.getPortalTrustDeviceNumber());
        mContext.sendBroadcast(intent2);
    }


    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
    public String getDeviceName(){
        return device;
    }

    public String getDeviceId(){
        return deviceid;
    }

}
