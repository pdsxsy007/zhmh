package io.cordova.zhqy.bean;

/**
 * Created by Administrator on 2019/6/19 0019.
 */

public class AppOrthBean {
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
        private String invocationLogId;

        private String invocationLogAppId;

        private String invocationLogTime;

        private String invocationLogFunction;

        private String invocationLogMember;

        private String appName;

        private String memberNickname;

        public void setInvocationLogId(String invocationLogId){
            this.invocationLogId = invocationLogId;
        }
        public String getInvocationLogId(){
            return this.invocationLogId;
        }
        public void setInvocationLogAppId(String invocationLogAppId){
            this.invocationLogAppId = invocationLogAppId;
        }
        public String getInvocationLogAppId(){
            return this.invocationLogAppId;
        }

        public String getInvocationLogTime() {
            return invocationLogTime;
        }

        public void setInvocationLogTime(String invocationLogTime) {
            this.invocationLogTime = invocationLogTime;
        }

        public void setInvocationLogFunction(String invocationLogFunction){
            this.invocationLogFunction = invocationLogFunction;
        }
        public String getInvocationLogFunction(){
            return this.invocationLogFunction;
        }
        public void setInvocationLogMember(String invocationLogMember){
            this.invocationLogMember = invocationLogMember;
        }
        public String getInvocationLogMember(){
            return this.invocationLogMember;
        }
        public void setAppName(String appName){
            this.appName = appName;
        }
        public String getAppName(){
            return this.appName;
        }
        public void setMemberNickname(String memberNickname){
            this.memberNickname = memberNickname;
        }
        public String getMemberNickname(){
            return this.memberNickname;
        }

    }
}
