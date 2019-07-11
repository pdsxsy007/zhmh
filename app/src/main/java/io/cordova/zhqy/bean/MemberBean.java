package io.cordova.zhqy.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/5/16 0016.
 */

public class MemberBean {
    private boolean success;

    private String msg;

    private MemberUserBean obj;

    private String attributes;

    private String count;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }

    public MemberUserBean getObj() {
        return obj;
    }

    public void setObj(MemberUserBean obj) {
        this.obj = obj;
    }

    public void setAttributes(String attributes){
        this.attributes = attributes;
    }
    public String getAttributes(){
        return this.attributes;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }

    public class MemberUserBean {
        private String memberId;

        private String memberUsername;

        private String memberPwd;

        private String memberNickname;

        private int memberSex;

        private String memberPhone;

        private String memberIdNumber;

        private String memberCreateTime;

        private int memberState;

        private String memberAcademicNumber;

        private String memberMailbox;

        private String memberSign;

        private String opneId;

        private String quicklyTicket;

        //private List<DeptList> deptList ;

        private List<RoleCodeList> roleCodeList ;

        private int memberRole;

        private String memberImage;

        private String memberRemarks;

        private String memberSalt;

        public void setMemberId(String memberId){
            this.memberId = memberId;
        }
        public String getMemberId(){
            return this.memberId;
        }
        public void setMemberUsername(String memberUsername){
            this.memberUsername = memberUsername;
        }
        public String getMemberUsername(){
            return this.memberUsername;
        }
        public void setMemberPwd(String memberPwd){
            this.memberPwd = memberPwd;
        }
        public String getMemberPwd(){
            return this.memberPwd;
        }
        public void setMemberNickname(String memberNickname){
            this.memberNickname = memberNickname;
        }
        public String getMemberNickname(){
            return this.memberNickname;
        }
        public void setMemberSex(int memberSex){
            this.memberSex = memberSex;
        }
        public int getMemberSex(){
            return this.memberSex;
        }
        public void setMemberPhone(String memberPhone){
            this.memberPhone = memberPhone;
        }
        public String getMemberPhone(){
            return this.memberPhone;
        }
        public void setMemberIdNumber(String memberIdNumber){
            this.memberIdNumber = memberIdNumber;
        }
        public String getMemberIdNumber(){
            return this.memberIdNumber;
        }

        public String getMemberCreateTime() {
            return memberCreateTime;
        }

        public void setMemberCreateTime(String memberCreateTime) {
            this.memberCreateTime = memberCreateTime;
        }

        public void setMemberState(int memberState){
            this.memberState = memberState;
        }
        public int getMemberState(){
            return this.memberState;
        }
        public void setMemberAcademicNumber(String memberAcademicNumber){
            this.memberAcademicNumber = memberAcademicNumber;
        }
        public String getMemberAcademicNumber(){
            return this.memberAcademicNumber;
        }
        public void setMemberMailbox(String memberMailbox){
            this.memberMailbox = memberMailbox;
        }
        public String getMemberMailbox(){
            return this.memberMailbox;
        }
        public void setMemberSign(String memberSign){
            this.memberSign = memberSign;
        }
        public String getMemberSign(){
            return this.memberSign;
        }
        public void setOpneId(String opneId){
            this.opneId = opneId;
        }
        public String getOpneId(){
            return this.opneId;
        }
        public void setQuicklyTicket(String quicklyTicket){
            this.quicklyTicket = quicklyTicket;
        }
        public String getQuicklyTicket(){
            return this.quicklyTicket;
        }
       /* public void setDeptList(List<DeptList> deptList){
            this.deptList = deptList;
        }
        public List<DeptList> getDeptList(){
            return this.deptList;
        }*/
        public void setRoleCodeList(List<RoleCodeList> roleCodeList){
            this.roleCodeList = roleCodeList;
        }
        public List<RoleCodeList> getRoleCodeList(){
            return this.roleCodeList;
        }
        public void setMemberRole(int memberRole){
            this.memberRole = memberRole;
        }
        public int getMemberRole(){
            return this.memberRole;
        }
        public void setMemberImage(String memberImage){
            this.memberImage = memberImage;
        }
        public String getMemberImage(){
            return this.memberImage;
        }
        public void setMemberRemarks(String memberRemarks){
            this.memberRemarks = memberRemarks;
        }
        public String getMemberRemarks(){
            return this.memberRemarks;
        }
        public void setMemberSalt(String memberSalt){
            this.memberSalt = memberSalt;
        }
        public String getMemberSalt(){
            return this.memberSalt;
        }

    }

    public class RoleCodeList {
        private String roleCode;

        private String roleName;

        private String roleState;

        private String roleComment;

        public void setRoleCode(String roleCode){
            this.roleCode = roleCode;
        }
        public String getRoleCode(){
            return this.roleCode;
        }
        public void setRoleName(String roleName){
            this.roleName = roleName;
        }
        public String getRoleName(){
            return this.roleName;
        }
        public void setRoleState(String roleState){
            this.roleState = roleState;
        }
        public String getRoleState(){
            return this.roleState;
        }
        public void setRoleComment(String roleComment){
            this.roleComment = roleComment;
        }
        public String getRoleComment(){
            return this.roleComment;
        }

    }
}
