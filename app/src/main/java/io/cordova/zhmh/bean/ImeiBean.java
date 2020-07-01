package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/8/15 0015.
 */

public class ImeiBean {
    /*{
        "success": 1,
            "message": "成功",
            "equipmentId": "34.819860",
    }*/

    private int success;

    private String message;

    private String equipmentId;

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

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }
}
