package io.cordova.zhqy.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/27 0027.
 */

public class OAMsgListBean2 {


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
        private String messageId;
        private String messageTitle;
        private String messageSender;
        private String messageSendTime;
        private String messageUrl;
        private String messageContent;
        private String messageAppOpenid;
        private String messageAppName;
        private String messageIdentification;
        private int messageType;
        private String messageThirdSendTime;

        private List<PortalMessageDetailList> portalMessageDetailList;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public String getMessageSender() {
            return messageSender;
        }

        public void setMessageSender(String messageSender) {
            this.messageSender = messageSender;
        }

        public String getMessageSendTime() {
            return messageSendTime;
        }

        public void setMessageSendTime(String messageSendTime) {
            this.messageSendTime = messageSendTime;
        }

        public String getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(String messageUrl) {
            this.messageUrl = messageUrl;
        }

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }

        public String getMessageAppOpenid() {
            return messageAppOpenid;
        }

        public void setMessageAppOpenid(String messageAppOpenid) {
            this.messageAppOpenid = messageAppOpenid;
        }

        public String getMessageAppName() {
            return messageAppName;
        }

        public void setMessageAppName(String messageAppName) {
            this.messageAppName = messageAppName;
        }

        public String getMessageIdentification() {
            return messageIdentification;
        }

        public void setMessageIdentification(String messageIdentification) {
            this.messageIdentification = messageIdentification;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getMessageThirdSendTime() {
            return messageThirdSendTime;
        }

        public void setMessageThirdSendTime(String messageThirdSendTime) {
            this.messageThirdSendTime = messageThirdSendTime;
        }

        public List<PortalMessageDetailList> getPortalMessageDetailList() {
            return portalMessageDetailList;
        }

        public void setPortalMessageDetailList(List<PortalMessageDetailList> portalMessageDetailList) {
            this.portalMessageDetailList = portalMessageDetailList;
        }

        public static class PortalMessageDetailList{
            private String messageDetailId;
            private String messageDetailMessageId;
            private String messageDetailChecker;
            private int messageDetailState;
            private String messageDetailCheckTime;

            public String getMessageDetailId() {
                return messageDetailId;
            }

            public void setMessageDetailId(String messageDetailId) {
                this.messageDetailId = messageDetailId;
            }

            public String getMessageDetailMessageId() {
                return messageDetailMessageId;
            }

            public void setMessageDetailMessageId(String messageDetailMessageId) {
                this.messageDetailMessageId = messageDetailMessageId;
            }

            public String getMessageDetailChecker() {
                return messageDetailChecker;
            }

            public void setMessageDetailChecker(String messageDetailChecker) {
                this.messageDetailChecker = messageDetailChecker;
            }

            public int getMessageDetailState() {
                return messageDetailState;
            }

            public void setMessageDetailState(int messageDetailState) {
                this.messageDetailState = messageDetailState;
            }

            public String getMessageDetailCheckTime() {
                return messageDetailCheckTime;
            }

            public void setMessageDetailCheckTime(String messageDetailCheckTime) {
                this.messageDetailCheckTime = messageDetailCheckTime;
            }
        }

    }


}
