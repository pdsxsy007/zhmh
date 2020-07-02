package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26 0026.
 */

public class HotApplistBean {

    /**
     * success : true
     * msg : 操作成功
     * obj : [{"appId":101,"appName":"123","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540891796366.png","appIntroduction":"","appLinkedWay":2,"appIosSchema":"cxStudy://{memberid}","appIosDownloadLink":"http://","appAndroidSchema":"cxStudy://{memberid}","appAndroidDownloadLink":"http://","appIntranet":1,"appUpordown":0,"appRecommend":0,"appSort":12,"appHeat":8,"appCreateTime":1540891808000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://www.baidu.com","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891796366.png","templetCreatTime":null},"systemName":null,"iconCount":null,"systemIdList":null},{"appId":102,"appName":"mkyy","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540891858389.png","appIntroduction":"","appLinkedWay":2,"appIosSchema":"cxStudy://","appIosDownloadLink":"https://itunes.apple.com/cn/app/超星学习通/id977946724?mt=8","appAndroidSchema":"cxStudy://","appAndroidDownloadLink":"http://app.chaoxing.com","appIntranet":0,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1540891863000,"appLoginFlag":1,"appModulesId":null,"appUrl":"http://www.baidu.com","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891858389.png","templetCreatTime":null},"systemName":null,"iconCount":null,"systemIdList":null},{"appId":103,"appName":"sdfwe","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540892250574.jpg","appIntroduction":"","appLinkedWay":1,"appIosSchema":"cxStudy://{memberid}","appIosDownloadLink":"http://","appAndroidSchema":"cxStudy://{memberid}","appAndroidDownloadLink":"http://","appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":12,"appHeat":1,"appCreateTime":1540892268000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://192.168.30.98:8087/portal/portal-app/app-5/test.html","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540892250574.jpg","templetCreatTime":null},"systemName":null,"iconCount":null,"systemIdList":null},{"appId":105,"appName":"一卡通查询","appBelongSystem":"3b79d77599f24fe3ade7af5bef05e869","appImages":"\\2018\\11\\1542940968200.png","appIntroduction":"微应用","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":2,"appCreateTime":1542940991000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://192.168.30.68:8080/microapplication/wyy/yktcx.html","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\11\\1542940968200.png","templetCreatTime":null},"systemName":null,"iconCount":null,"systemIdList":null}]
     * count : 4
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
         * appId : 101
         * appName : 123
         * appBelongSystem : 322278f588054ebd920ed8a8635406f0
         * appImages : \2018\10\1540891796366.png
         * appIntroduction :
         * appLinkedWay : 2
         * appIosSchema : cxStudy://{memberid}
         * appIosDownloadLink : http://
         * appAndroidSchema : cxStudy://{memberid}
         * appAndroidDownloadLink : http://
         * appIntranet : 1
         * appUpordown : 0
         * appRecommend : 0
         * appSort : 12
         * appHeat : 8
         * appCreateTime : 1540891808000
         * appLoginFlag : 0
         * appModulesId : null
         * appUrl : http://www.baidu.com
         * portalAppIcon : {"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891796366.png","templetCreatTime":null}
         * systemName : null
         * iconCount : null
         * systemIdList : null
         */

        private int appId;
        private String appName;
        private String appBelongSystem;
        private String appImages;
        private String appIntroduction;
        private int appLinkedWay;
        private String appIosSchema;
        private String appIosDownloadLink;
        private String appAndroidSchema;
        private String appAndroidDownloadLink;
        private int appIntranet;
        private int appUpordown;
        private int appRecommend;
        private int appSort;
        private int appHeat;
        private long appCreateTime;
        private int appLoginFlag;
        private Object appModulesId;
        private String appUrl;
        private portalAppIconBean portalAppIcon;
        private Object systemName;
        private Object iconCount;
        private Object systemIdList;

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppBelongSystem() {
            return appBelongSystem;
        }

        public void setAppBelongSystem(String appBelongSystem) {
            this.appBelongSystem = appBelongSystem;
        }

        public String getAppImages() {
            return appImages;
        }

        public void setAppImages(String appImages) {
            this.appImages = appImages;
        }

        public String getAppIntroduction() {
            return appIntroduction;
        }

        public void setAppIntroduction(String appIntroduction) {
            this.appIntroduction = appIntroduction;
        }

        public int getAppLinkedWay() {
            return appLinkedWay;
        }

        public void setAppLinkedWay(int appLinkedWay) {
            this.appLinkedWay = appLinkedWay;
        }

        public String getAppIosSchema() {
            return appIosSchema;
        }

        public void setAppIosSchema(String appIosSchema) {
            this.appIosSchema = appIosSchema;
        }

        public String getAppIosDownloadLink() {
            return appIosDownloadLink;
        }

        public void setAppIosDownloadLink(String appIosDownloadLink) {
            this.appIosDownloadLink = appIosDownloadLink;
        }

        public String getAppAndroidSchema() {
            return appAndroidSchema;
        }

        public void setAppAndroidSchema(String appAndroidSchema) {
            this.appAndroidSchema = appAndroidSchema;
        }

        public String getAppAndroidDownloadLink() {
            return appAndroidDownloadLink;
        }

        public void setAppAndroidDownloadLink(String appAndroidDownloadLink) {
            this.appAndroidDownloadLink = appAndroidDownloadLink;
        }

        public int getAppIntranet() {
            return appIntranet;
        }

        public void setAppIntranet(int appIntranet) {
            this.appIntranet = appIntranet;
        }

        public int getAppUpordown() {
            return appUpordown;
        }

        public void setAppUpordown(int appUpordown) {
            this.appUpordown = appUpordown;
        }

        public int getAppRecommend() {
            return appRecommend;
        }

        public void setAppRecommend(int appRecommend) {
            this.appRecommend = appRecommend;
        }

        public int getAppSort() {
            return appSort;
        }

        public void setAppSort(int appSort) {
            this.appSort = appSort;
        }

        public int getAppHeat() {
            return appHeat;
        }

        public void setAppHeat(int appHeat) {
            this.appHeat = appHeat;
        }

        public long getAppCreateTime() {
            return appCreateTime;
        }

        public void setAppCreateTime(long appCreateTime) {
            this.appCreateTime = appCreateTime;
        }

        public int getAppLoginFlag() {
            return appLoginFlag;
        }

        public void setAppLoginFlag(int appLoginFlag) {
            this.appLoginFlag = appLoginFlag;
        }

        public Object getAppModulesId() {
            return appModulesId;
        }

        public void setAppModulesId(Object appModulesId) {
            this.appModulesId = appModulesId;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public portalAppIconBean getportalAppIcon() {
            return portalAppIcon;
        }

        public void setportalAppIcon(portalAppIconBean portalAppIcon) {
            this.portalAppIcon = portalAppIcon;
        }

        public Object getSystemName() {
            return systemName;
        }

        public void setSystemName(Object systemName) {
            this.systemName = systemName;
        }

        public Object getIconCount() {
            return iconCount;
        }

        public void setIconCount(Object iconCount) {
            this.iconCount = iconCount;
        }

        public Object getSystemIdList() {
            return systemIdList;
        }

        public void setSystemIdList(Object systemIdList) {
            this.systemIdList = systemIdList;
        }

        public static class portalAppIconBean {
            /**
             * templetId : null
             * templetCode : null
             * templetName : null
             * templetAppId : null
             * templetAppImage : \2018\10\1540891796366.png
             * templetCreatTime : null
             */

            private Object templetId;
            private Object templetCode;
            private Object templetName;
            private Object templetAppId;
            private String templetAppImage;
            private Object templetCreatTime;

            public Object getTempletId() {
                return templetId;
            }

            public void setTempletId(Object templetId) {
                this.templetId = templetId;
            }

            public Object getTempletCode() {
                return templetCode;
            }

            public void setTempletCode(Object templetCode) {
                this.templetCode = templetCode;
            }

            public Object getTempletName() {
                return templetName;
            }

            public void setTempletName(Object templetName) {
                this.templetName = templetName;
            }

            public Object getTempletAppId() {
                return templetAppId;
            }

            public void setTempletAppId(Object templetAppId) {
                this.templetAppId = templetAppId;
            }

            public String getTempletAppImage() {
                return templetAppImage;
            }

            public void setTempletAppImage(String templetAppImage) {
                this.templetAppImage = templetAppImage;
            }

            public Object getTempletCreatTime() {
                return templetCreatTime;
            }

            public void setTempletCreatTime(Object templetCreatTime) {
                this.templetCreatTime = templetCreatTime;
            }
        }
    }
}
