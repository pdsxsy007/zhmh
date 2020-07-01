package io.cordova.zhmh.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/24 0024.
 */

public class UserMsgBean {


    /**
     * success : true
     * msg : 操作成功
     * obj : {"modules":{"memberId":"2012016","memberUsername":"2012016","memberPwd":"bb0a1de70bc6c704b4fb97c07d897e96","memberNickname":"何子轶(密码nic@2017)","memberSex":2,"memberPhone":"18039507059","memberIdNumber":"411102198911220218","memberCreateTime":1537511338000,"memberState":1,"memberAcademicNumber":"2012016","memberMailbox":"wxbbjwork@163.com","memberSign":"1","memberImage":"\\2019\\2\\1551253059443.jpeg","memberOtherId":null,"memberOtherSchoolNumber":null,"memberOtherNation":null,"memberOtherDepartment":null,"memberOtherMajor":null,"memberOtherGrade":null,"memberOtherClass":null,"memberOtherBirthday":null,"memberOtherNative":null,"loginLogTime":1553667074000,"rolecodes":[{"roleCode":"system_admin","roleName":"管理员","roleState":null,"roleComment":null}]}}
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
         * modules : {"memberId":"2012016","memberUsername":"2012016","memberPwd":"bb0a1de70bc6c704b4fb97c07d897e96","memberNickname":"何子轶(密码nic@2017)","memberSex":2,"memberPhone":"18039507059","memberIdNumber":"411102198911220218","memberCreateTime":1537511338000,"memberState":1,"memberAcademicNumber":"2012016","memberMailbox":"wxbbjwork@163.com","memberSign":"1","memberImage":"\\2019\\2\\1551253059443.jpeg","memberOtherId":null,"memberOtherSchoolNumber":null,"memberOtherNation":null,"memberOtherDepartment":null,"memberOtherMajor":null,"memberOtherGrade":null,"memberOtherClass":null,"memberOtherBirthday":null,"memberOtherNative":null,"loginLogTime":1553667074000,"rolecodes":[{"roleCode":"system_admin","roleName":"管理员","roleState":null,"roleComment":null}]}
         */

        private ModulesBean modules;

        public ModulesBean getModules() {
            return modules;
        }

        public void setModules(ModulesBean modules) {
            this.modules = modules;
        }

        public static class ModulesBean {
            /**
             * memberId : 2012016
             * memberUsername : 2012016
             * memberPwd : bb0a1de70bc6c704b4fb97c07d897e96
             * memberNickname : 何子轶(密码nic@2017)
             * memberSex : 2
             * memberPhone : 18039507059
             * memberIdNumber : 411102198911220218
             * memberCreateTime : 1537511338000
             * memberState : 1
             * memberAcademicNumber : 2012016
             * memberMailbox : wxbbjwork@163.com
             * memberSign : 1
             * memberImage : \2019\2\1551253059443.jpeg
             * memberOtherId : null
             * memberOtherSchoolNumber : null
             * memberOtherNation : null
             * memberOtherDepartment : null
             * memberOtherMajor : null
             * memberOtherGrade : null
             * memberOtherClass : null
             * memberOtherBirthday : null
             * memberOtherNative : null
             * loginLogTime : 1553667074000
             * rolecodes : [{"roleCode":"system_admin","roleName":"管理员","roleState":null,"roleComment":null}]
             */

            private String memberId;
            private String memberUsername;
            private String memberPwd;
            private String memberNickname;
            private int memberSex;
            private String memberPhone;
            private String memberIdNumber;
            private long memberCreateTime;
            private int memberState;
            private String memberAcademicNumber;
            private String memberMailbox;
            private String memberSign;
            private String memberImage;
            private String memberOtherId;
            private String memberOtherSchoolNumber;
            private String memberOtherNation;
            private String memberOtherDepartment;
            private String memberOtherMajor;
            private String memberOtherGrade;
            private String memberOtherClass;
            private String memberOtherBirthday;
            private String memberOtherNative;
            private long loginLogTime;
            private List<RolecodesBean> rolecodes;

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getMemberUsername() {
                return memberUsername;
            }

            public void setMemberUsername(String memberUsername) {
                this.memberUsername = memberUsername;
            }

            public String getMemberPwd() {
                return memberPwd;
            }

            public void setMemberPwd(String memberPwd) {
                this.memberPwd = memberPwd;
            }

            public String getMemberNickname() {
                return memberNickname;
            }

            public void setMemberNickname(String memberNickname) {
                this.memberNickname = memberNickname;
            }

            public int getMemberSex() {
                return memberSex;
            }

            public void setMemberSex(int memberSex) {
                this.memberSex = memberSex;
            }

            public String getMemberPhone() {
                return memberPhone;
            }

            public void setMemberPhone(String memberPhone) {
                this.memberPhone = memberPhone;
            }

            public String getMemberIdNumber() {
                return memberIdNumber;
            }

            public void setMemberIdNumber(String memberIdNumber) {
                this.memberIdNumber = memberIdNumber;
            }

            public long getMemberCreateTime() {
                return memberCreateTime;
            }

            public void setMemberCreateTime(long memberCreateTime) {
                this.memberCreateTime = memberCreateTime;
            }

            public int getMemberState() {
                return memberState;
            }

            public void setMemberState(int memberState) {
                this.memberState = memberState;
            }

            public String getMemberAcademicNumber() {
                return memberAcademicNumber;
            }

            public void setMemberAcademicNumber(String memberAcademicNumber) {
                this.memberAcademicNumber = memberAcademicNumber;
            }

            public String getMemberMailbox() {
                return memberMailbox;
            }

            public void setMemberMailbox(String memberMailbox) {
                this.memberMailbox = memberMailbox;
            }

            public String getMemberSign() {
                return memberSign;
            }

            public void setMemberSign(String memberSign) {
                this.memberSign = memberSign;
            }

            public String getMemberImage() {
                return memberImage;
            }

            public void setMemberImage(String memberImage) {
                this.memberImage = memberImage;
            }

            public String getMemberOtherId() {
                return memberOtherId;
            }

            public void setMemberOtherId(String memberOtherId) {
                this.memberOtherId = memberOtherId;
            }

            public String getMemberOtherSchoolNumber() {
                return memberOtherSchoolNumber;
            }

            public void setMemberOtherSchoolNumber(String memberOtherSchoolNumber) {
                this.memberOtherSchoolNumber = memberOtherSchoolNumber;
            }

            public String getMemberOtherNation() {
                return memberOtherNation;
            }

            public void setMemberOtherNation(String memberOtherNation) {
                this.memberOtherNation = memberOtherNation;
            }

            public String getMemberOtherDepartment() {
                return memberOtherDepartment;
            }

            public void setMemberOtherDepartment(String memberOtherDepartment) {
                this.memberOtherDepartment = memberOtherDepartment;
            }

            public String getMemberOtherMajor() {
                return memberOtherMajor;
            }

            public void setMemberOtherMajor(String memberOtherMajor) {
                this.memberOtherMajor = memberOtherMajor;
            }

            public String getMemberOtherGrade() {
                return memberOtherGrade;
            }

            public void setMemberOtherGrade(String memberOtherGrade) {
                this.memberOtherGrade = memberOtherGrade;
            }

            public String getMemberOtherClass() {
                return memberOtherClass;
            }

            public void setMemberOtherClass(String memberOtherClass) {
                this.memberOtherClass = memberOtherClass;
            }

            public String getMemberOtherBirthday() {
                return memberOtherBirthday;
            }

            public void setMemberOtherBirthday(String memberOtherBirthday) {
                this.memberOtherBirthday = memberOtherBirthday;
            }

            public String getMemberOtherNative() {
                return memberOtherNative;
            }

            public void setMemberOtherNative(String memberOtherNative) {
                this.memberOtherNative = memberOtherNative;
            }

            public long getLoginLogTime() {
                return loginLogTime;
            }

            public void setLoginLogTime(long loginLogTime) {
                this.loginLogTime = loginLogTime;
            }

            public List<RolecodesBean> getRolecodes() {
                return rolecodes;
            }

            public void setRolecodes(List<RolecodesBean> rolecodes) {
                this.rolecodes = rolecodes;
            }

            public static class RolecodesBean {
                /**
                 * roleCode : system_admin
                 * roleName : 管理员
                 * roleState : null
                 * roleComment : null
                 */

                private String roleCode;
                private String roleName;
                private Object roleState;
                private Object roleComment;

                public String getRoleCode() {
                    return roleCode;
                }

                public void setRoleCode(String roleCode) {
                    this.roleCode = roleCode;
                }

                public String getRoleName() {
                    return roleName;
                }

                public void setRoleName(String roleName) {
                    this.roleName = roleName;
                }

                public Object getRoleState() {
                    return roleState;
                }

                public void setRoleState(Object roleState) {
                    this.roleState = roleState;
                }

                public Object getRoleComment() {
                    return roleComment;
                }

                public void setRoleComment(Object roleComment) {
                    this.roleComment = roleComment;
                }
            }
        }
    }
}
