package io.cordova.zhqy.bean;


/**
 * Created by Administrator on 2019/2/26 0026.
 */

public class NoticeMsgBean {

    private String sendername;
    private String ywlx;
    private String senderdeptname;
    private String title;
    private String sendtime;
    private String messageSender;
    private int messageId;
    private long messageSendTime;

    public String getSendername() {
        return sendername == null ? "" : sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getYwlx() {
        return ywlx == null ? "" : ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public String getSenderdeptname() {
        return senderdeptname == null ? "" : senderdeptname;
    }

    public void setSenderdeptname(String senderdeptname) {
        this.senderdeptname = senderdeptname;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendtime() {
        return sendtime == null ? "" : sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getMessageSender() {
        return messageSender == null ? "" : messageSender;
    }

    public void setMessageSender(String messageSender) {
        this.messageSender = messageSender;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public long getMessageSendTime() {
        return messageSendTime;
    }

    public void setMessageSendTime(long messageSendTime) {
        this.messageSendTime = messageSendTime;
    }


}
