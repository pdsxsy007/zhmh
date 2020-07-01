package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/2/25 0025.
 */

public class SysMsgBean {

    /**
     * success : true
     * msg : 操作成功
     * obj : [{"messageId":2,"messageTitle":"全体处级干部会议通知","messageSender":"admin","messageSendTime":1536022230000,"messageUrl":null,"messageContent":"时间：2018年8月30日（周四）下午15:00\n地点：莲花街校区学术会议中心D205报告厅\n人员：校领导、全体处级干部 \n内容：安排部署新学期工作\n \n请各单位办公室通知相关人员准时参会。\n党委办公室 校长办公室\n2018年8月30日","messageDetailId":2,"messageDetailMessageId":2,"messageDetailChecker":"admin","messageDetailState":1,"messageDetailCheckTime":1536025859000,"checkNum":null,"read":null,"notRead":null}]
     * count : 8
     * attributes : null
     */

    private boolean success;
    private String msg;
    private int count;
    private Object attributes;
    private List<ObjBean> obj;

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
         * messageId : 2
         * messageTitle : 全体处级干部会议通知
         * messageSender : admin
         * messageSendTime : 1536022230000
         * messageUrl : null
         * messageContent : 时间：2018年8月30日（周四）下午15:00
         地点：莲花街校区学术会议中心D205报告厅
         人员：校领导、全体处级干部
         内容：安排部署新学期工作
         请各单位办公室通知相关人员准时参会。
         党委办公室 校长办公室
         2018年8月30日
         * messageDetailId : 2
         * messageDetailMessageId : 2
         * messageDetailChecker : admin
         * messageDetailState : 1
         * messageDetailCheckTime : 1536025859000
         * checkNum : null
         * read : null
         * notRead : null
         */

        private int messageId;
        private String messageTitle;
        private String messageSender;
        private long messageSendTime;
        private Object messageUrl;
        private String messageContent;
        private int messageDetailId;
        private int messageDetailMessageId;
        private String messageDetailChecker;
        private int messageDetailState;
        private long messageDetailCheckTime;
        private Object checkNum;
        private Object read;
        private Object notRead;
        private String messageAppName;

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
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

        public long getMessageSendTime() {
            return messageSendTime;
        }

        public void setMessageSendTime(long messageSendTime) {
            this.messageSendTime = messageSendTime;
        }

        public Object getMessageUrl() {
            return messageUrl;
        }

        public void setMessageUrl(Object messageUrl) {
            this.messageUrl = messageUrl;
        }

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }

        public int getMessageDetailId() {
            return messageDetailId;
        }

        public void setMessageDetailId(int messageDetailId) {
            this.messageDetailId = messageDetailId;
        }

        public int getMessageDetailMessageId() {
            return messageDetailMessageId;
        }

        public void setMessageDetailMessageId(int messageDetailMessageId) {
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

        public long getMessageDetailCheckTime() {
            return messageDetailCheckTime;
        }

        public void setMessageDetailCheckTime(long messageDetailCheckTime) {
            this.messageDetailCheckTime = messageDetailCheckTime;
        }

        public Object getCheckNum() {
            return checkNum;
        }

        public void setCheckNum(Object checkNum) {
            this.checkNum = checkNum;
        }

        public Object getRead() {
            return read;
        }

        public void setRead(Object read) {
            this.read = read;
        }

        public Object getNotRead() {
            return notRead;
        }

        public void setNotRead(Object notRead) {
            this.notRead = notRead;
        }

        public String getMessageAppName() {
            return messageAppName;
        }

        public void setMessageAppName(String messageAppName) {
            this.messageAppName = messageAppName;
        }
    }
}
