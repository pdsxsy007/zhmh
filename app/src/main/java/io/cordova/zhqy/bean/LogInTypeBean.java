package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2019/6/21 0021.
 */

public class LogInTypeBean {
    private String name;
    private int imageId;

    public LogInTypeBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
