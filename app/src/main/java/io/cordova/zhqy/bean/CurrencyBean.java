package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2018/12/24 0024.
 */

public class CurrencyBean {


    /**
     * success : true
     * msg : 修改用户头像成功
     * obj : null
     * count : null
     * attributes : null
     */

    private boolean success;
    private String msg;
    private Object obj;
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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
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
