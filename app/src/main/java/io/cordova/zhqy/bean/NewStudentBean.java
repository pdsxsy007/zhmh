package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2019/7/16 0016.
 */

public class NewStudentBean {
    private boolean success;

    private String msg;

    private Obj obj;

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
    public void setObj(Obj obj){
        this.obj = obj;
    }
    public Obj getObj(){
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
        private String type;

        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }

    }
}
