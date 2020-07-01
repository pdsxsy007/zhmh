package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class VerCodeBean {
    private boolean success;

    private String msg;

    private String obj;

    private Attributes attributes;

    private String count;

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
    public void setObj(String obj){
        this.obj = obj;
    }
    public String getObj(){
        return this.obj;
    }
    public void setAttributes(Attributes attributes){
        this.attributes = attributes;
    }
    public Attributes getAttributes(){
        return this.attributes;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }

    public class Attributes {
        private int frequency;

        public void setFrequency(int frequency){
            this.frequency = frequency;
        }
        public int getFrequency(){
            return this.frequency;
        }

    }
}
