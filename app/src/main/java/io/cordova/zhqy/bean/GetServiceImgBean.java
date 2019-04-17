package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2018/12/21 0021.
 */

public class GetServiceImgBean {

    /**
     * success : true
     * msg : 操作成功
     * obj : \2018\12\1545375576895.png
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
