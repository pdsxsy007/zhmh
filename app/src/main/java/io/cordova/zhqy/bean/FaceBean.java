package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2019/6/19 0019.
 */

public class FaceBean {
    private boolean success;

    private String msg;

    private Obj obj;

    private String attributes;

    private String count;

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
    public void setAttributes(String attributes){
        this.attributes = attributes;
    }
    public String getAttributes(){
        return this.attributes;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }

    public class Obj {
        private String passWord;

        private String userName;
        private String phone;
        private Boolean verification;

        public void setPassWord(String passWord){
            this.passWord = passWord;
        }
        public String getPassWord(){
            return this.passWord;
        }
        public void setUserName(String userName){
            this.userName = userName;
        }
        public String getUserName(){
            return this.userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Boolean getVerification() {
            return verification;
        }

        public void setVerification(Boolean verification) {
            this.verification = verification;
        }
    }
}
