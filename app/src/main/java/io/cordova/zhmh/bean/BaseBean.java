package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/2/22 0022.
 */

public class BaseBean {

    /**
     * success : true
     * msg : 已经收藏
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
