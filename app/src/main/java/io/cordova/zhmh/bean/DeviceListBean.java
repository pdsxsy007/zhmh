package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/7/29.
 */

public class DeviceListBean {

    private Attributes attributes;
    private int count;
    private String msg;

    public List<Obj> getObj() {
        return obj;
    }

    public void setObj(List<Obj> obj) {
        this.obj = obj;
    }

    private List<Obj> obj;
    private boolean success;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public class Attributes{
    }
    public class Obj{
        private String portalTrustDeviceId;
        private String portalTrustDeviceMemberId;
        private String portalTrustDeviceNumber;
        private String portalTrustDeviceType;
        private String portalTrustDeviceSourceType;
        private String portalTrustDeviceName;
        private String portalTrustDeviceInfo;
        private String portalTrustDeviceMaster;

        public String getportalTrustDeviceSourceType() {
            return portalTrustDeviceSourceType;
        }

        public void setportalTrustDeviceSourceType(String portalTrustDeviceSourceType) {
            this.portalTrustDeviceSourceType = portalTrustDeviceSourceType;
        }

        public String getportalTrustDeviceMaster() {
            return portalTrustDeviceMaster;
        }

        public void setportalTrustDeviceMaster(String portalTrustDeviceMaster) {
            this.portalTrustDeviceMaster = portalTrustDeviceMaster;
        }

        public String getportalTrustDeviceId() {
            return portalTrustDeviceId;
        }

        public void setportalTrustDeviceId(String portalTrustDeviceId) {
            this.portalTrustDeviceId = portalTrustDeviceId;
        }

        public String getportalTrustDeviceMemberId() {
            return portalTrustDeviceMemberId;
        }

        public void setportalTrustDeviceMemberId(String portalTrustDeviceMemberId) {
            this.portalTrustDeviceMemberId = portalTrustDeviceMemberId;
        }

        public String getportalTrustDeviceNumber() {
            return portalTrustDeviceNumber;
        }

        public void setportalTrustDeviceNumber(String portalTrustDeviceNumber) {
            this.portalTrustDeviceNumber = portalTrustDeviceNumber;
        }

        public String getportalTrustDeviceType() {
            return portalTrustDeviceType;
        }

        public void setportalTrustDeviceType(String portalTrustDeviceType) {
            this.portalTrustDeviceType = portalTrustDeviceType;
        }



        public String getportalTrustDeviceName() {
            return portalTrustDeviceName;
        }

        public void setportalTrustDeviceName(String portalTrustDeviceName) {
            this.portalTrustDeviceName = portalTrustDeviceName;
        }

        public String getportalTrustDeviceInfo() {
            return portalTrustDeviceInfo;
        }

        public void setportalTrustDeviceInfo(String portalTrustDeviceInfo) {
            this.portalTrustDeviceInfo = portalTrustDeviceInfo;
        }



        public int getportalTrustDeviceDelete() {
            return portalTrustDeviceDelete;
        }

        public void setportalTrustDeviceDelete(int portalTrustDeviceDelete) {
            this.portalTrustDeviceDelete = portalTrustDeviceDelete;
        }

        public String getportalTrustDeviceLastLoginTime() {
            return portalTrustDeviceLastLoginTime;
        }

        public void setportalTrustDeviceLastLoginTime(String portalTrustDeviceLastLoginTime) {
            this.portalTrustDeviceLastLoginTime = portalTrustDeviceLastLoginTime;
        }

        private int portalTrustDeviceDelete;
        private String portalTrustDeviceLastLoginTime;
    }
}
