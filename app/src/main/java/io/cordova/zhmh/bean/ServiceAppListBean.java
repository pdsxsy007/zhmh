package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/16 0016.
 */

public class ServiceAppListBean {

    /**
     * success : true
     * msg : 操作成功
     * obj : [{"modulesId":76,"modulesCode":"d323099ef7d0407885ca780031243288","modulesParentCode":"root","modulesName":"管理","modulesSort":3,"modulesCreateTime":1533612537000,"modulesState":0,"modulesImages":"","childMenus":[],"apps":[{"appId":117,"appName":"我的工资条","appBelongSystem":"76e8e86b88764362ac2243d05dfb17ea","appImages":"\\2019\\1\\1547779411722.png","appIntroduction":"","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1547779450000,"appLoginFlag":0,"appSecret":null,"appNewWindow":null,"appModulesId":493,"appUrl":"http://192.168.30.68:8282/microapplication/kfdx/wdgzt.html","portalAppIcon":null,"systemName":null,"iconCount":null,"systemIdList":null}],"modulesParentName":null}]
     * count : null
     * attributes : null
     */

    private boolean success;
    private String msg;
    private Object count;
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

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
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
         * modulesId : 76
         * modulesCode : d323099ef7d0407885ca780031243288
         * modulesParentCode : root
         * modulesName : 管理
         * modulesSort : 3
         * modulesCreateTime : 1533612537000
         * modulesState : 0
         * modulesImages :
         * childMenus : []
         * apps : [{"appId":117,"appName":"我的工资条","appBelongSystem":"76e8e86b88764362ac2243d05dfb17ea","appImages":"\\2019\\1\\1547779411722.png","appIntroduction":"","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1547779450000,"appLoginFlag":0,"appSecret":null,"appNewWindow":null,"appModulesId":493,"appUrl":"http://192.168.30.68:8282/microapplication/kfdx/wdgzt.html","portalAppIcon":null,"systemName":null,"iconCount":null,"systemIdList":null}]
         * modulesParentName : null
         */

        private int modulesId;
        private String modulesCode;
        private String modulesParentCode;
        private String modulesName;
        private int modulesSort;
        private long modulesCreateTime;
        private int modulesState;
        private String modulesImages;
        private Object modulesParentName;
        private List<?> childMenus;
        private List<AppsBean> apps;

        public int getModulesId() {
            return modulesId;
        }

        public void setModulesId(int modulesId) {
            this.modulesId = modulesId;
        }

        public String getModulesCode() {
            return modulesCode;
        }

        public void setModulesCode(String modulesCode) {
            this.modulesCode = modulesCode;
        }

        public String getModulesParentCode() {
            return modulesParentCode;
        }

        public void setModulesParentCode(String modulesParentCode) {
            this.modulesParentCode = modulesParentCode;
        }

        public String getModulesName() {
            return modulesName;
        }

        public void setModulesName(String modulesName) {
            this.modulesName = modulesName;
        }

        public int getModulesSort() {
            return modulesSort;
        }

        public void setModulesSort(int modulesSort) {
            this.modulesSort = modulesSort;
        }

        public long getModulesCreateTime() {
            return modulesCreateTime;
        }

        public void setModulesCreateTime(long modulesCreateTime) {
            this.modulesCreateTime = modulesCreateTime;
        }

        public int getModulesState() {
            return modulesState;
        }

        public void setModulesState(int modulesState) {
            this.modulesState = modulesState;
        }

        public String getModulesImages() {
            return modulesImages;
        }

        public void setModulesImages(String modulesImages) {
            this.modulesImages = modulesImages;
        }

        public Object getModulesParentName() {
            return modulesParentName;
        }

        public void setModulesParentName(Object modulesParentName) {
            this.modulesParentName = modulesParentName;
        }

        public List<?> getChildMenus() {
            return childMenus;
        }

        public void setChildMenus(List<?> childMenus) {
            this.childMenus = childMenus;
        }

        public List<AppsBean> getApps() {
            return apps;
        }

        public void setApps(List<AppsBean> apps) {
            this.apps = apps;
        }

        public static class AppsBean {

            private int appId;
            private String appName;
            private String appBelongSystem;
            private String appImages;
            private String appIntroduction;
            private int appLinkedWay;
            private Object appIosSchema;
            private Object appIosDownloadLink;
            private String appAndroidSchema;
            private Object appAndroidDownloadLink;
            private int appIntranet;
            private int appUpordown;
            private int appRecommend;
            private int appSort;
            private int appHeat;
            private long appCreateTime;
            private int appLoginFlag;
            private Object appSecret;
            private Object appNewWindow;
            private int appModulesId;
            private String appUrl;

            private Object systemName;
            private Object iconCount;
            private Object systemIdList;

            private portalAppIconBean portalAppIcon;
            private portalAppAuthentication portalAppAuthentication;

            public portalAppAuthentication getportalAppAuthentication() {
                return portalAppAuthentication;
            }

            public void setportalAppAuthentication(portalAppAuthentication portalAppAuthentication) {
                this.portalAppAuthentication = portalAppAuthentication;
            }

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

            public Object getAppIosSchema() {
                return appIosSchema;
            }

            public void setAppIosSchema(Object appIosSchema) {
                this.appIosSchema = appIosSchema;
            }

            public Object getAppIosDownloadLink() {
                return appIosDownloadLink;
            }

            public void setAppIosDownloadLink(Object appIosDownloadLink) {
                this.appIosDownloadLink = appIosDownloadLink;
            }

            public String getAppAndroidSchema() {
                return appAndroidSchema;
            }

            public void setAppAndroidSchema(String appAndroidSchema) {
                this.appAndroidSchema = appAndroidSchema;
            }

            public Object getAppAndroidDownloadLink() {
                return appAndroidDownloadLink;
            }

            public void setAppAndroidDownloadLink(Object appAndroidDownloadLink) {
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

            public Object getAppSecret() {
                return appSecret;
            }

            public void setAppSecret(Object appSecret) {
                this.appSecret = appSecret;
            }

            public Object getAppNewWindow() {
                return appNewWindow;
            }

            public void setAppNewWindow(Object appNewWindow) {
                this.appNewWindow = appNewWindow;
            }

            public int getAppModulesId() {
                return appModulesId;
            }

            public void setAppModulesId(int appModulesId) {
                this.appModulesId = appModulesId;
            }

            public String getAppUrl() {
                return appUrl;
            }

            public void setAppUrl(String appUrl) {
                this.appUrl = appUrl;
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

            public portalAppIconBean getportalAppIcon() {
                return portalAppIcon;
            }

            public void setportalAppIcon(portalAppIconBean portalAppIcon) {
                this.portalAppIcon = portalAppIcon;
            }

            public static class portalAppIconBean {

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

            public static class portalAppAuthentication {
                private String appAuthenticationAppId;

                private String appAuthenticationFace;

                private int appAuthenticationFingerprint;

                private int appAuthenticationGesture;

                private int appAuthenticationPassword;

                private String count;

                public void setAppAuthenticationAppId(String appAuthenticationAppId){
                    this.appAuthenticationAppId = appAuthenticationAppId;
                }
                public String getAppAuthenticationAppId(){
                    return this.appAuthenticationAppId;
                }

                public String getAppAuthenticationFace() {
                    return appAuthenticationFace;
                }

                public void setAppAuthenticationFace(String appAuthenticationFace) {
                    this.appAuthenticationFace = appAuthenticationFace;
                }

                public void setAppAuthenticationFingerprint(int appAuthenticationFingerprint){
                    this.appAuthenticationFingerprint = appAuthenticationFingerprint;
                }
                public int getAppAuthenticationFingerprint(){
                    return this.appAuthenticationFingerprint;
                }
                public void setAppAuthenticationGesture(int appAuthenticationGesture){
                    this.appAuthenticationGesture = appAuthenticationGesture;
                }
                public int getAppAuthenticationGesture(){
                    return this.appAuthenticationGesture;
                }
                public void setAppAuthenticationPassword(int appAuthenticationPassword){
                    this.appAuthenticationPassword = appAuthenticationPassword;
                }
                public int getAppAuthenticationPassword(){
                    return this.appAuthenticationPassword;
                }
                public void setCount(String count){
                    this.count = count;
                }
                public String getCount(){
                    return this.count;
                }

            }

        }
    }
}
