package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/8/22 0022.
 */

public class BannerBean {
    private boolean success;

    private String msg;

    private Obj obj;

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
    public void setObj(Obj obj){
        this.obj = obj;
    }
    public Obj getObj(){
        return this.obj;
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


    public class Obj {


        private List<Banners> list ;

        public List<Banners> getList() {
            return list;
        }

        public void setList(List<Banners> list) {
            this.list = list;
        }
    }



    public class Banners {
        private int broadcastId;

        private String broadcastTitle;

        private String broadcastUrl;

        private String broadcastComment;

        private String broadcastImage;

        private String broadcastCreateTime;

        private int broadcastState;

        private int broadcastEquipment;

        private int equipmentOrder;

        public void setBroadcastId(int broadcastId){
            this.broadcastId = broadcastId;
        }
        public int getBroadcastId(){
            return this.broadcastId;
        }
        public void setBroadcastTitle(String broadcastTitle){
            this.broadcastTitle = broadcastTitle;
        }
        public String getBroadcastTitle(){
            return this.broadcastTitle;
        }
        public void setBroadcastUrl(String broadcastUrl){
            this.broadcastUrl = broadcastUrl;
        }
        public String getBroadcastUrl(){
            return this.broadcastUrl;
        }
        public void setBroadcastComment(String broadcastComment){
            this.broadcastComment = broadcastComment;
        }
        public String getBroadcastComment(){
            return this.broadcastComment;
        }
        public void setBroadcastImage(String broadcastImage){
            this.broadcastImage = broadcastImage;
        }
        public String getBroadcastImage(){
            return this.broadcastImage;
        }

        public String getBroadcastCreateTime() {
            return broadcastCreateTime;
        }

        public void setBroadcastCreateTime(String broadcastCreateTime) {
            this.broadcastCreateTime = broadcastCreateTime;
        }

        public void setBroadcastState(int broadcastState){
            this.broadcastState = broadcastState;
        }
        public int getBroadcastState(){
            return this.broadcastState;
        }
        public void setBroadcastEquipment(int broadcastEquipment){
            this.broadcastEquipment = broadcastEquipment;
        }
        public int getBroadcastEquipment(){
            return this.broadcastEquipment;
        }
        public void setEquipmentOrder(int equipmentOrder){
            this.equipmentOrder = equipmentOrder;
        }
        public int getEquipmentOrder(){
            return this.equipmentOrder;
        }

    }
}
