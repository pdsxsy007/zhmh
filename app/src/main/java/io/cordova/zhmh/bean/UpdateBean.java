package io.cordova.zhmh.bean;

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

        public void setportalVersionId(int portalVersionId){
            this.portalVersionId = portalVersionId;
        }
        public int getportalVersionId(){
            return this.portalVersionId;
        }
        public void setportalVersionNumber(String portalVersionNumber){
            this.portalVersionNumber = portalVersionNumber;
        }
        public String getportalVersionNumber(){
            return this.portalVersionNumber;
        }
        public void setportalVersionType(String portalVersionType){
            this.portalVersionType = portalVersionType;
        }
        public String getportalVersionType(){
            return this.portalVersionType;
        }
        public void setportalVersionUpdate(int portalVersionUpdate){
            this.portalVersionUpdate = portalVersionUpdate;
        }
        public int getportalVersionUpdate(){
            return this.portalVersionUpdate;
        }
        public void setportalVersionDownloadAdress(String portalVersionDownloadAdress){
            this.portalVersionDownloadAdress = portalVersionDownloadAdress;
        }
        public String getportalVersionDownloadAdress(){
            return this.portalVersionDownloadAdress;
        }
        public void setportalVersionPublisher(String portalVersionPublisher){
            this.portalVersionPublisher = portalVersionPublisher;
        }
        public String getportalVersionPublisher(){
            return this.portalVersionPublisher;
        }

        public String getportalVersionPublishTime() {
            return portalVersionPublishTime;
        }

        public void setportalVersionPublishTime(String portalVersionPublishTime) {
            this.portalVersionPublishTime = portalVersionPublishTime;
        }

        public void setportalVersionUpdateDescription(String portalVersionUpdateDescription){
            this.portalVersionUpdateDescription = portalVersionUpdateDescription;
        }
        public String getportalVersionUpdateDescription(){
            return this.portalVersionUpdateDescription;
        }

    }
}
