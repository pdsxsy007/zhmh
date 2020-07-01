package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/8/15 0015.
 */

public class LocalVersonBean {
    /*{
        "success": 1,
            "message": "成功",
            "latitude": "34.819860",
            "longitude": "113.557899"
    }*/

    private int success;

    private String message;

    private String localVersionName;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalVersionName() {
        return localVersionName;
    }

    public void setLocalVersionName(String localVersionName) {
        this.localVersionName = localVersionName;
    }
}
