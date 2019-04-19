package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2019/4/17 0017.
 */

public class UpdateBean {
    private boolean success;

    private String msg;

    private Data obj;

    private String count;

    private String attributes;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }

    public Data getObj() {
        return obj;
    }

    public void setObj(Data obj) {
        this.obj = obj;
    }

    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setAttributes(String attributes){
        this.attributes = attributes;
    }
    public String getAttributes(){
        return this.attributes;
    }

    public class Data {
        private int portalVersionId;

        private String portalVersionNumber;

        private String portalVersionType;

        private int portalVersionUpdate;

        private String portalVersionDownloadAdress;

        private String portalVersionPublisher;

        private String portalVersionPublishTime;

        private String portalVersionUpdateDescription;

        public void setPortalVersionId(int portalVersionId){
            this.portalVersionId = portalVersionId;
        }
        public int getPortalVersionId(){
            return this.portalVersionId;
        }
        public void setPortalVersionNumber(String portalVersionNumber){
            this.portalVersionNumber = portalVersionNumber;
        }
        public String getPortalVersionNumber(){
            return this.portalVersionNumber;
        }
        public void setPortalVersionType(String portalVersionType){
            this.portalVersionType = portalVersionType;
        }
        public String getPortalVersionType(){
            return this.portalVersionType;
        }
        public void setPortalVersionUpdate(int portalVersionUpdate){
            this.portalVersionUpdate = portalVersionUpdate;
        }
        public int getPortalVersionUpdate(){
            return this.portalVersionUpdate;
        }
        public void setPortalVersionDownloadAdress(String portalVersionDownloadAdress){
            this.portalVersionDownloadAdress = portalVersionDownloadAdress;
        }
        public String getPortalVersionDownloadAdress(){
            return this.portalVersionDownloadAdress;
        }
        public void setPortalVersionPublisher(String portalVersionPublisher){
            this.portalVersionPublisher = portalVersionPublisher;
        }
        public String getPortalVersionPublisher(){
            return this.portalVersionPublisher;
        }

        public String getPortalVersionPublishTime() {
            return portalVersionPublishTime;
        }

        public void setPortalVersionPublishTime(String portalVersionPublishTime) {
            this.portalVersionPublishTime = portalVersionPublishTime;
        }

        public void setPortalVersionUpdateDescription(String portalVersionUpdateDescription){
            this.portalVersionUpdateDescription = portalVersionUpdateDescription;
        }
        public String getPortalVersionUpdateDescription(){
            return this.portalVersionUpdateDescription;
        }

    }
}
