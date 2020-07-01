package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/27 0027.
 */

public class OAMsgListBean {


    /**
     * success : true
     * msg : 获取待办成功
     *
     * obj : [{"sendername":"系统管理员","ywlx":"会议室申请","senderdeptname":"系统维护","todourl":"http://myoa.zzuli.edu.cn/hnrdapp/platforms/h5/assets/www/processRuntime.action?method=openTodo&todounid=J3cb0dc7bb92444383078ef0eedab14c&todousertype=U","title":"测试2018001  2018-10-12 09:22:46","sendtime":"2018-10-12 09:21:59"},{"sendername":"系统管理员","ywlx":"信息化管理中心文件","senderdeptname":"系统维护","todourl":"http://myoa.zzuli.edu.cn/hnrdapp/platforms/h5/assets/www/processRuntime.action?method=openTodo&todounid=J88ae1cf186340f39647796ee5dbc63d&todousertype=U","title":"特殊字符'待办测试'","sendtime":"2018-09-10 19:33:19"},{"sendername":"系统管理员","ywlx":"会议通知","senderdeptname":"系统维护","todourl":"http://myoa.zzuli.edu.cn/hnrdapp/platforms/h5/assets/www/commonweb.action?method=page&formid=J890d0fbe6d14d9a92b87d2e577aa3c0&tzunid=J58ae5b89df34640b40377ff6f7da0de&todousertype=U","title":"测试测试","sendtime":"2018-07-18 14:30:25"},{"sendername":"系统管理员","ywlx":"信息化管理中心文件","senderdeptname":"系统维护","todourl":"http://myoa.zzuli.edu.cn/hnrdapp/platforms/h5/assets/www/processRuntime.action?method=openTodo&todounid=Je9d0d2b94dd4edb8ed3b0b9de38a499&todousertype=U","title":"测试文件","sendtime":"2018-06-22 19:02:57"},{"sendername":"系统管理员","ywlx":"信息化管理中心文件","senderdeptname":"系统维护","todourl":"http://myoa.zzuli.edu.cn/hnrdapp/platforms/h5/assets/www/processRuntime.action?method=openTodo&todounid=Jac49f164bc74591800c30d01b5b6f7f&todousertype=U","title":"测试功能","sendtime":"2018-06-11 21:16:13"}]
     * count : 5
     * attributes : null
     */

    private boolean success;
    private String msg;
    private int count;
    private Object attributes;
    private List<ObjBean> obj;
    /**
     * obj : null
     */



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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }



    public static class ObjBean {
        /**
         * sendername : 系统管理员
         * ywlx : 会议室申请
         * senderdeptname : 系统维护
         * todourl : http://myoa.zzuli.edu.cn/hnrdapp/platforms/h5/assets/www/processRuntime.action?method=openTodo&todounid=J3cb0dc7bb92444383078ef0eedab14c&todousertype=U
         * title : 测试2018001  2018-10-12 09:22:46
         * sendtime : 2018-10-12 09:21:59
         */

        private String sendername;
        private String ywlx;
        private String senderdeptname;
        private String todourl;
        private String title;
        private String sendtime;

        public String getSendername() {
            return sendername;
        }

        public void setSendername(String sendername) {
            this.sendername = sendername;
        }

        public String getYwlx() {
            return ywlx;
        }

        public void setYwlx(String ywlx) {
            this.ywlx = ywlx;
        }

        public String getSenderdeptname() {
            return senderdeptname;
        }

        public void setSenderdeptname(String senderdeptname) {
            this.senderdeptname = senderdeptname;
        }

        public String getTodourl() {
            return todourl;
        }

        public void setTodourl(String todourl) {
            this.todourl = todourl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSendtime() {
            return sendtime;
        }

        public void setSendtime(String sendtime) {
            this.sendtime = sendtime;
        }
    }
}
