package io.cordova.zhqy;

/**
 * Created by Administrator on 2018/11/24 0024.
 */

public class UrlRes {

    private UrlRes() {
        throw new Error("Do not need instantiate!");
    }


    /**
     * 服务器地址
     */
    public static String HOME_URL ="http://iapp.zzuli.edu.cn";
//    public static String HOME_URL ="http://192.168.30.30:8081";
    public static String Native_URL ="http://192.168.30.30:8081";
    /*tgt  相关*/
    public static String HOME2_URL ="http://kys.zzuli.edu.cn";
    /*绑定极光*/
    public static String HOME4_URL ="http://iapp.zzuli.edu.cn";

    /*图片前缀*/
    public static String HOME3_URL ="http://iapp.zzuli.edu.cn/portal/public/getImg?path=";

    /*校验用户名密码*/
    public static String Check_Pwd ="/portal-pc/login/authentication?username=admin&password=123456";

    /*获取lt*/
    public static String Get_Lt = "/cas/login?action=getlt&service.=http://iapp.zzuli.edu.cn:80/portal/login/appLoginn&callback=?";

    /*表单提交登录*/
    public static String From_Login = "/cas/login?service=http://iapp.zzuli.edu.cn:80/portal/login/appLogin";

    /**
     *  获取UserId
     * portal/mobile/login  Get_UserId
     * */
    public static String Get_UserId = "/portal/mobile/login";

    /**
     *  获取极光绑定ID
     * portal/mobile/login
     * */
    public static String Registration_Id = "/portal/mobile/equipment/add";

     /**
     * 解除极光绑定ID
     * portal/mobile/login
     * */
    public static String Relieve_Registration_Id = "/portal/mobile/equipment/del";

    /**
     * 退出登录*/
    public static String Exit_Out = "/cas/logout";

    /**
     * 应用列表
     * */
    public static String APP_List ="/portal/mobile/findAppList";

   /**
     * 应用服务列表
     * */
    public static String Service_APP_List ="/portal/mobile/memberApp/getMemberApp";

    /**
     * 个人信息
     * */
    public static String User_Msg ="/portal/mobile/casMember/getMemberByUsername";

    /**
     *  获取服务器返回的图片地址
     * portal/mobile/casMember/updateUserInfo\
     * portal/mobile/public/getUploadFileUrl
     * */
    public static String Get_Img_uri ="/portal/mobile/casMember/updateUserInfo";
    /**
     *
     * 上传图片到服务器
     * portal/mobile/public/photoUpload
     *
        /mobile/public/photoUploadBase64
     * */
    public static String Upload_Img ="/portal/mobile/public/photoUpload";
    /**
     * 系统信息
     * */

    public static String System_Msg_List ="/portal/mobile/weiMessage/listMessageIniDtoForCurrentUser";
     /**
     * OA信息
      *
      * workFolwDbList  workFolwDbCount
      *
      * (key)workType   (value)  workdb，worksq
     * */
    public static String OA_Msg_List ="/portal/mobile/oa/getAppPortalOaController";

    /**我的收藏*/
    public static String My_Collection ="/portal/mobile/collectionApp/findCollectionAppList";

    /**添加收藏*/
    public static String Add_Collection ="/portal/mobile/collectionApp/addCollectionApp";

    /**取消收藏*/
    public static String Cancel_Collection ="/portal/mobile/collectionApp/delCollectionApp";

    /**查询应用收藏状态*/
    public static String Query_IsCollection ="/portal/mobile/collectionApp/isCollectionApp";
    /**统计当前访问目标（四大模块） */
    public static String Four_Modules ="/portal/mobile/portalAccessLog/insertPortalAccessLog";
    /**统计应用响应时间 */
    public static String APP_Time ="/portal/mobile/response/responseTime";
     /**统计应用访问量*/
    public static String APP_Click_Number ="/portal/mobile/statistical/appAccessStatistical";
    /**OA*/
    public static String Query_count="/portal/mobile/oa/workFolwDbCount";

    /**OA*/
    public static String Query_workFolwDbList="/portal/mobile/oa/workFolwDbList";
    /**
     统计该用户未读的消息数量*/
    public static String Query_countUnreadMessagesForCurrentUser="/portal/mobile/weiMessage/countUnreadMessagesForCurrentUser";

    /**
     * 更新
     */
    public static String getNewVersionInfo="/portal/mobile/config/getNewVersionInfo";

}
