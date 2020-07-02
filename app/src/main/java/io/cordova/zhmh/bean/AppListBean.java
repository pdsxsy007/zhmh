package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/24 0024.
 */

public class AppListBean {

    /**
     * success : true
     * msg : 操作成功
     * obj : {"total":4,"list":[{"appId":101,"appName":"123","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540891796366.png","appIntroduction":"","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":0,"appSort":12,"appHeat":8,"appCreateTime":1540891808000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://www.baidu.com","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891796366.png","templetCreatTime":null},"systemName":"资产系统","iconCount":null,"systemIdList":null},{"appId":102,"appName":"mkyy","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540891858389.png","appIntroduction":"","appLinkedWay":2,"appIosSchema":"cxStudy://","appIosDownloadLink":"https://itunes.apple.com/cn/app/超星学习通/id977946724?mt=8","appAndroidSchema":"cxStudy://","appAndroidDownloadLink":"http://app.chaoxing.com","appIntranet":0,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1540891863000,"appLoginFlag":1,"appModulesId":null,"appUrl":"http://www.baidu.com","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891858389.png","templetCreatTime":null},"systemName":"资产系统","iconCount":null,"systemIdList":null},{"appId":103,"appName":"sdfwe","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540892250574.jpg","appIntroduction":"","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":12,"appHeat":1,"appCreateTime":1540892268000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540892250574.jpg","templetCreatTime":null},"systemName":"资产系统","iconCount":null,"systemIdList":null},{"appId":105,"appName":"一卡通查询","appBelongSystem":"3b79d77599f24fe3ade7af5bef05e869","appImages":"\\2018\\11\\1542940968200.png","appIntroduction":"微应用","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1542940991000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://192.168.30.68:8080/microapplication/wyy/yktcx.html","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\11\\1542940968200.png","templetCreatTime":null},"systemName":"科研系统","iconCount":null,"systemIdList":null}],"pageNum":1,"pageSize":4,"size":4,"startRow":0,"endRow":3,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1,"firstPage":1,"lastPage":1}
     * count : null
     * attributes : null
     */

    private boolean success;
    private String msg;
    private ObjBean obj;
    private Object count;
    private Object attributes;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
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

    public static class ObjBean {
        /**
         * total : 4
         * list : [{"appId":101,"appName":"123","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540891796366.png","appIntroduction":"","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":0,"appSort":12,"appHeat":8,"appCreateTime":1540891808000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://www.baidu.com","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891796366.png","templetCreatTime":null},"systemName":"资产系统","iconCount":null,"systemIdList":null},{"appId":102,"appName":"mkyy","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540891858389.png","appIntroduction":"","appLinkedWay":2,"appIosSchema":"cxStudy://","appIosDownloadLink":"https://itunes.apple.com/cn/app/超星学习通/id977946724?mt=8","appAndroidSchema":"cxStudy://","appAndroidDownloadLink":"http://app.chaoxing.com","appIntranet":0,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1540891863000,"appLoginFlag":1,"appModulesId":null,"appUrl":"http://www.baidu.com","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540891858389.png","templetCreatTime":null},"systemName":"资产系统","iconCount":null,"systemIdList":null},{"appId":103,"appName":"sdfwe","appBelongSystem":"322278f588054ebd920ed8a8635406f0","appImages":"\\2018\\10\\1540892250574.jpg","appIntroduction":"","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":12,"appHeat":1,"appCreateTime":1540892268000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\10\\1540892250574.jpg","templetCreatTime":null},"systemName":"资产系统","iconCount":null,"systemIdList":null},{"appId":105,"appName":"一卡通查询","appBelongSystem":"3b79d77599f24fe3ade7af5bef05e869","appImages":"\\2018\\11\\1542940968200.png","appIntroduction":"微应用","appLinkedWay":1,"appIosSchema":null,"appIosDownloadLink":null,"appAndroidSchema":null,"appAndroidDownloadLink":null,"appIntranet":1,"appUpordown":0,"appRecommend":1,"appSort":1,"appHeat":1,"appCreateTime":1542940991000,"appLoginFlag":0,"appModulesId":null,"appUrl":"http://192.168.30.68:8080/microapplication/wyy/yktcx.html","portalAppIcon":{"templetId":null,"templetCode":null,"templetName":null,"templetAppId":null,"templetAppImage":"\\2018\\11\\1542940968200.png","templetCreatTime":null},"systemName":"科研系统","iconCount":null,"systemIdList":null}]
         * pageNum : 1
         * pageSize : 4
         * size : 4
         * startRow : 0
         * endRow : 3
         * pages : 1
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         * firstPage : 1
         * lastPage : 1
         */

        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private int firstPage;
        private int lastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(int firstPage) {
            this.firstPage = firstPage;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * appId : 101
             * appName : 123
             * appBelongSystem : 322278f588054ebd920ed8a8635406f0
             * appImages : \2018\10\1540891796366.png
             * appIntroduction :
             * appLinkedWay : 1
             * appIosSchema : null
             * appIosDownloadLink : null
             * appAndroidSchema : null
             * appAndroidDownloadLink : null
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
             * systemName : 资产系统
             * iconCount : null
             * systemIdList : null
             */


           /*appIntranet  1 需要内网*/
           /*appLoginFlag  0 需要登录*/
            private int appId;
            private String appName;
            private String appBelongSystem;
            private String appImages;
            private String appIntroduction;
            private String appSecret;
            private int appLinkedWay;
            private Object appIosSchema;
            private Object appIosDownloadLink;
            private Object appAndroidSchema;
            private Object appAndroidDownloadLink;
            private int appIntranet;
            private int appUpordown;
            private int appRecommend;
            private int appSort;
            private int appHeat;
            private long appCreateTime;
            private int appLoginFlag;
            private Object appModulesId;
            private String appUrl;
            private portalAppIconBean portalAppIcon ;
            private String systemName;
            private Object iconCount;
            private Object systemIdList;
            private portalAppAuthentication portalAppAuthentication;
            public String getAppSecret() {
                return appSecret;
            }

            public void setAppSecret(String appSecret) {
                this.appSecret = appSecret;
            }



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

            public Object getAppAndroidSchema() {
                return appAndroidSchema;
            }

            public void setAppAndroidSchema(Object appAndroidSchema) {
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

            public String getSystemName() {
                return systemName;
            }

            public void setSystemName(String systemName) {
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
