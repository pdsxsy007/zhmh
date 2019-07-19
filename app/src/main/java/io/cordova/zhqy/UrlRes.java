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
    //    public static String HOME_URL ="http://192.168.30.30:8081";  //郭亚东
        //public static String HOME_URL ="http://192.168.30.28:8090";  //毛兵



    /*tgt  相关*/
    public static String HOME2_URL ="http://kys.zzuli.edu.cn";
    //    public static String HOME2_URL ="http://192.168.30.30:8080";  //郭亚东
    //public static String HOME2_URL ="http://192.168.30.28:8090";  //毛兵



    /*绑定极光*/
    public static String HOME4_URL ="http://iapp.zzuli.edu.cn";

    /*图片前缀*/
    public static String HOME3_URL ="http://iapp.zzuli.edu.cn/portal/public/getImg?path=";


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
     * 获取用户头像
     * */
    public static String User_Head_Image="http://kys.zzuli.edu.cn/authentication/public/getHeadImg";
    /**
     *  获取服务器返回的图片地址
     * */
    public static String Get_Img_uri ="/portal/mobile/casMember/updateUserInfo";
    /**
     *
     * 上传图片到服务器
     * */
    public static String Upload_Img ="/portal/mobile/public/photoUpload";
    /**
     * 系统信息
     * */

    public static String System_Msg_List ="/portal/mobile/weiMessage/listMessageIniDtoForCurrentUser";

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
    /**
     * 查看消息接口
     */
    public static String searchMessageById="/portal/mobile/weiMessage/getMessageIniDtoForCurrentUserByDetailId";

    /**
     * 上传手机信息
     */
    public static String addMobileInfoUrl="/portal/mobile/equipment/addMobile";

    /**
     * 输入账号获取邮箱或者手机号
     */
    public static String getUserInfoByMemberIdUrl="/authentication/api/casMember/getUserInfoByMemberId";


    /**
     * 获取验证码
     */
    public static String sendVerificationUrl="/authentication/api/verification/sendVerification";

    /**
     * 验证验证码
     */
    public static String verificationUrl="/authentication/api/verification/verification";

    /**
     * 修改密码
     */
    public static String updatePasswordUrl="/authentication/api/casMember/updatePassword";

    /**
     * 获取下载类型
     */
    public static String getDownLoadTypeUrl="/portal/mobile/config/getDownLoadType";

    /**
     * app授权接口
     */
    public static String functionInvocationLogUrl="/portal/mobile/functionInvocationLog/addInvocationLog";

    /**
     * 人脸识别
     */
    public static String getPassByFaceUrl="/authentication/api/face/getPassByFace";

    /**
     * 人脸识别上传图片
     */
    public static String addFaceUrl="/authentication/api/face/addFace";

    /**
     * 一键登录
     */
    public static String loginTokenVerifyUrl="/authentication/api/jgMessage/loginTokenVerify";

    /**
     * webview上传位置阅读时间等信息
     */
    public static String addPortalReadingAccessUrl="/portal/mobile/portalReadingAccess/addPortalReadingAccess";

    /**
     * 5分钟一次上传信息
     */
    public static String insertPortalPositionUrl="/portal/mobile/portalPosition/insertPortalPosition";

    /**
     * 新生报到是否弹出人脸识别
     */
    public static String jugdeFaceUrl="/portal/mobile/newStudentRegister/jugdeFace";

    /**
     * 是否强制修改密码
     */
    public static String newStudentUpdatePwdStateUrl="/portal/mobile/newStudentRegister/newStudentUpdatePwdState";

}
