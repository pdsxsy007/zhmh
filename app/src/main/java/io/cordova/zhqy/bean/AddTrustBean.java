package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2019/7/29.
 */

public class AddTrustBean {

    private Attributes attributes;
    private int count;
    private String msg;
    private Obj obj;
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

    public Obj getObj() {
        return obj;
    }

    public void setObj(Obj obj) {
        this.obj = obj;
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
    }

}
