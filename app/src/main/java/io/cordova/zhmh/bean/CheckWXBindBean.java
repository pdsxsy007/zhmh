package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2019/8/20 0020.
 */

public class CheckWXBindBean {
    private boolean success;

    private String msg;

    private Attributes attributes;

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
    public void setAttributes(Attributes attributes){
        this.attributes = attributes;
    }
    public Attributes getAttributes(){
        return this.attributes;
    }

    public class Attributes {
        private String tgt;

        private String code;

        private String username;
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setTgt(String tgt){
            this.tgt = tgt;
        }
        public String getTgt(){
            return this.tgt;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setUsername(String username){
            this.username = username;
        }
        public String getUsername(){
            return this.username;
        }

    }
}
