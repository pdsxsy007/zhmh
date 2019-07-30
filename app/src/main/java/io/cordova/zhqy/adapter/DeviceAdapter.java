package io.cordova.zhqy.adapter;

import android.content.Context;
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
    protected void convert(ViewHolder holder, final DeviceListBean.Obj obj, int position) {

        /*holder.setOnClickListener((R.id.tv_delete, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        })*/
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
        if((android.os.Build.MANUFACTURER + android.os.Build.DEVICE).equals(obj.getPortalTrustDeviceInfo())){

        }
        if(obj.getPortalTrustDeviceMaster().equals("1")){
            holder.setText(R.id.device_name,obj.getPortalTrustDeviceInfo() + "(主设备)");
        }else{
            holder.setText(R.id.device_name,obj.getPortalTrustDeviceInfo());

        }

        holder.setText(R.id.loginTime, "最近登录：" + TimeUtils.timeStamp2Date(obj.getPortalTrustDeviceLastLoginTime(),"yyyy-MM-dd HH:mm:ss"));

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
