package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/5/29 0029.
 */

public class DownLoadBean {
    private boolean success;

    private String msg;

    private List<String> obj ;

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
    public void setString(List<String> obj){
        this.obj = obj;
    }
    public List<String> getString(){
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

    }
}
