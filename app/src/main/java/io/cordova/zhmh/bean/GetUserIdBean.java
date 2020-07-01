package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2018/12/11 0011.
 */

public class GetUserIdBean {

    /**
     * success : true
     * msg : 获取数据成功
     * obj : rgCYcb2nMpl3SgA4pwL10w08wynSnUNkI1AoAcErHjs=
     * count : null
     * attributes : null
     */

    private boolean success;
    private String msg;
    private String obj;
    private Object count;
    private Object attributes;

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

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }
}
