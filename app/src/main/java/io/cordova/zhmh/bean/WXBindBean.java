package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/8/20 0020.
 */

public class WXBindBean {
    private boolean success;

    private String msg;

    private String obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
