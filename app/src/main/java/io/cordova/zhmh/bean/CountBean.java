package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/4/16 0016.
 */

public class CountBean {
    private boolean success;

    private String msg;

    private String obj;

    private int count;

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
    public void setObj(String obj){
        this.obj = obj;
    }
    public String getObj(){
        return this.obj;
    }
    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setAttributes(String attributes){
        this.attributes = attributes;
    }
    public String getAttributes(){
        return this.attributes;
    }
}
