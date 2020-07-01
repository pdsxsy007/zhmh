package io.cordova.zhmh.bean;

/**
 * Created by Administrator on 2018/11/17 0017.
 */

public class LoginBean {


    /**
     * success : true
     * msg : 操作成功
     * attributes : {"tgt":"W5FCsCLWGc59rHVz5GN1UpButJ220P5wU6HOBgwTrJkSLwlPuyY2DLALCJ9MIGMUChjiaUzOsaDHX1peFxgq2ygDu6JVa38GVqyCTITxWA4=","username":"7fN1UjMt/+0qqJqw+jW2wg=="}
     */

    private boolean success;
    private String msg;
    private AttributesBean attributes;

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

    public AttributesBean getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesBean attributes) {
        this.attributes = attributes;
    }

    public static class AttributesBean {
        /**
         * tgt : W5FCsCLWGc59rHVz5GN1UpButJ220P5wU6HOBgwTrJkSLwlPuyY2DLALCJ9MIGMUChjiaUzOsaDHX1peFxgq2ygDu6JVa38GVqyCTITxWA4=
         * username : 7fN1UjMt/+0qqJqw+jW2wg==
         */

        private String tgt;
        private String username;

        public String getTgt() {
            return tgt;
        }

        public void setTgt(String tgt) {
            this.tgt = tgt;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
