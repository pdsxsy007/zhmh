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

        public String getPortalTrustDeviceSourceType() {
            return portalTrustDeviceSourceType;
        }

        public void setPortalTrustDeviceSourceType(String portalTrustDeviceSourceType) {
            this.portalTrustDeviceSourceType = portalTrustDeviceSourceType;
        }

        public String getPortalTrustDeviceMaster() {
            return portalTrustDeviceMaster;
        }

        public void setPortalTrustDeviceMaster(String portalTrustDeviceMaster) {
            this.portalTrustDeviceMaster = portalTrustDeviceMaster;
        }

        public String getPortalTrustDeviceId() {
            return portalTrustDeviceId;
        }

        public void setPortalTrustDeviceId(String portalTrustDeviceId) {
            this.portalTrustDeviceId = portalTrustDeviceId;
        }

        public String getPortalTrustDeviceMemberId() {
            return portalTrustDeviceMemberId;
        }

        public void setPortalTrustDeviceMemberId(String portalTrustDeviceMemberId) {
            this.portalTrustDeviceMemberId = portalTrustDeviceMemberId;
        }

        public String getPortalTrustDeviceNumber() {
            return portalTrustDeviceNumber;
        }

        public void setPortalTrustDeviceNumber(String portalTrustDeviceNumber) {
            this.portalTrustDeviceNumber = portalTrustDeviceNumber;
        }

        public String getPortalTrustDeviceType() {
            return portalTrustDeviceType;
        }

        public void setPortalTrustDeviceType(String portalTrustDeviceType) {
            this.portalTrustDeviceType = portalTrustDeviceType;
        }



        public String getPortalTrustDeviceName() {
            return portalTrustDeviceName;
        }

        public void setPortalTrustDeviceName(String portalTrustDeviceName) {
            this.portalTrustDeviceName = portalTrustDeviceName;
        }

        public String getPortalTrustDeviceInfo() {
            return portalTrustDeviceInfo;
        }

        public void setPortalTrustDeviceInfo(String portalTrustDeviceInfo) {
            this.portalTrustDeviceInfo = portalTrustDeviceInfo;
        }



        public int getPortalTrustDeviceDelete() {
            return portalTrustDeviceDelete;
        }

        public void setPortalTrustDeviceDelete(int portalTrustDeviceDelete) {
            this.portalTrustDeviceDelete = portalTrustDeviceDelete;
        }

        public String getPortalTrustDeviceLastLoginTime() {
            return portalTrustDeviceLastLoginTime;
        }

        public void setPortalTrustDeviceLastLoginTime(String portalTrustDeviceLastLoginTime) {
            this.portalTrustDeviceLastLoginTime = portalTrustDeviceLastLoginTime;
        }

        private int portalTrustDeviceDelete;
        private String portalTrustDeviceLastLoginTime;
    }
}
