package io.cordova.zhmh.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2020/1/9.
 */

public class AppWidgetBean {
    private boolean success;

    private String msg;

    private List<Obj> obj ;

    private String count;

    private Attributes attributes;

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
    public void setObj(List<Obj> obj){
        this.obj = obj;
    }
    public List<Obj> getObj(){
        return this.obj;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setAttributes(Attributes attributes){
        this.attributes = attributes;
    }
    public Attributes getAttributes(){
        return this.attributes;
    }


    public class Obj {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }

    public class Attributes {
        private List<TuesdayList> tuesdayList ;

        private List<ThursdayList> thursdayList ;

        private List<String> dateToWeek ;

        private List<SundayList> sundayList ;

        private List<FridayList> fridayList ;

        private List<WednesdayList> wednesdayList ;


        private List<CourseListTopTwo> courseListTopTwo ;

        private List<MondayList> mondayList ;

        private List<SaturdayList> saturdayList ;

        private String courseTime;

        public void setTuesdayList(List<TuesdayList> tuesdayList){
            this.tuesdayList = tuesdayList;
        }
        public List<TuesdayList> getTuesdayList(){
            return this.tuesdayList;
        }
        public void setThursdayList(List<ThursdayList> thursdayList){
            this.thursdayList = thursdayList;
        }
        public List<ThursdayList> getThursdayList(){
            return this.thursdayList;
        }
        public void setString(List<String> dateToWeek){
            this.dateToWeek = dateToWeek;
        }
        public List<String> getString(){
            return this.dateToWeek;
        }
        public void setSundayList(List<SundayList> sundayList){
            this.sundayList = sundayList;
        }
        public List<SundayList> getSundayList(){
            return this.sundayList;
        }
        public void setFridayList(List<FridayList> fridayList){
            this.fridayList = fridayList;
        }
        public List<FridayList> getFridayList(){
            return this.fridayList;
        }

        public List<WednesdayList> getWednesdayList() {
            return wednesdayList;
        }

        public void setWednesdayList(List<WednesdayList> wednesdayList) {
            this.wednesdayList = wednesdayList;
        }

        public void setCourseListTopTwo(List<CourseListTopTwo> courseListTopTwo){
            this.courseListTopTwo = courseListTopTwo;
        }
        public List<CourseListTopTwo> getCourseListTopTwo(){
            return this.courseListTopTwo;
        }
        public void setMondayList(List<MondayList> mondayList){
            this.mondayList = mondayList;
        }
        public List<MondayList> getMondayList(){
            return this.mondayList;
        }
        public void setSaturdayList(List<SaturdayList> saturdayList){
            this.saturdayList = saturdayList;
        }
        public List<SaturdayList> getSaturdayList(){
            return this.saturdayList;
        }
        public void setCourseTime(String courseTime){
            this.courseTime = courseTime;
        }
        public String getCourseTime(){
            return this.courseTime;
        }

    }

    public class CourseListTopTwo {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }

    public class WednesdayList {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }



    public class DateToWeek {

    }

    public class TuesdayList {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }

    public class MondayList {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }
    public class ThursdayList {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;


        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }
    public class SundayList {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }

    public class SaturdayList {
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }

    public class FridayList implements Serializable{
        private String undergraduateJuniorCourseId;

        private String undergraduateJuniorCourseCode;

        private String undergraduateJuniorCourseName;

        private String undergraduateJuniorCourseDate;

        private String undergraduateJuniorCourseTeacherId;

        private String undergraduateJuniorCourseTeacherName;

        private String undergraduateJuniorCourseSchoolYear;

        private String undergraduateJuniorCourseTerm;

        private String undergraduateJuniorCourseClassCode;

        private String undergraduateJuniorCourseClassName;

        private String undergraduateJuniorCourseWeekly;

        private String undergraduateJuniorCourseSingleDoubleWeek;

        private String undergraduateJuniorCourseSection;

        private String undergraduateJuniorCourseAdressCode;

        private String undergraduateJuniorCourseAdressName;

        private String undergraduateJuniorCoursePeople;

        private String undergraduateJuniorCourseClassRoom;

        //////////////////////////////////////////学生
        private String courseId;

        private String courseCode;

        private String courseName;

        private String courseDate;

        private String courseAdressCode;

        private String courseSchoolYear;

        private String courseTerm;

        private String courseClassCode;

        private String courseClassName;

        private String courseStudentId;

        private String courseStudentName;

        private String courseWeekly;

        private String courseSingleDoubleWeek;

        private String courseSection;

        private String courseDepartmentCourse;

        private String courseCategoryName;

        private String courseCredit;

        private String courseHours;

        private String courseExaminationMethodCode;

        private String courseTeachingNumber;

        private String courseTotolHours;

        private String courseWeekNumber;

        private String courseClassRoomCode;

        private String courseClassRoomName;

        private String courseSubjectCourseNumber;

        private String courseSectionWeek;

        private String courseWeek;

        private String coursePlan;

        private String courseTeacherNumber;

        private String courseTeacherName;

        private String teacherName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseDate() {
            return courseDate;
        }

        public void setCourseDate(String courseDate) {
            this.courseDate = courseDate;
        }

        public String getCourseAdressCode() {
            return courseAdressCode;
        }

        public void setCourseAdressCode(String courseAdressCode) {
            this.courseAdressCode = courseAdressCode;
        }

        public String getCourseSchoolYear() {
            return courseSchoolYear;
        }

        public void setCourseSchoolYear(String courseSchoolYear) {
            this.courseSchoolYear = courseSchoolYear;
        }

        public String getCourseTerm() {
            return courseTerm;
        }

        public void setCourseTerm(String courseTerm) {
            this.courseTerm = courseTerm;
        }

        public String getCourseClassCode() {
            return courseClassCode;
        }

        public void setCourseClassCode(String courseClassCode) {
            this.courseClassCode = courseClassCode;
        }

        public String getCourseClassName() {
            return courseClassName;
        }

        public void setCourseClassName(String courseClassName) {
            this.courseClassName = courseClassName;
        }

        public String getCourseStudentId() {
            return courseStudentId;
        }

        public void setCourseStudentId(String courseStudentId) {
            this.courseStudentId = courseStudentId;
        }

        public String getCourseStudentName() {
            return courseStudentName;
        }

        public void setCourseStudentName(String courseStudentName) {
            this.courseStudentName = courseStudentName;
        }

        public String getCourseWeekly() {
            return courseWeekly;
        }

        public void setCourseWeekly(String courseWeekly) {
            this.courseWeekly = courseWeekly;
        }

        public String getCourseSingleDoubleWeek() {
            return courseSingleDoubleWeek;
        }

        public void setCourseSingleDoubleWeek(String courseSingleDoubleWeek) {
            this.courseSingleDoubleWeek = courseSingleDoubleWeek;
        }

        public String getCourseSection() {
            return courseSection;
        }

        public void setCourseSection(String courseSection) {
            this.courseSection = courseSection;
        }

        public String getCourseDepartmentCourse() {
            return courseDepartmentCourse;
        }

        public void setCourseDepartmentCourse(String courseDepartmentCourse) {
            this.courseDepartmentCourse = courseDepartmentCourse;
        }

        public String getCourseCategoryName() {
            return courseCategoryName;
        }

        public void setCourseCategoryName(String courseCategoryName) {
            this.courseCategoryName = courseCategoryName;
        }

        public String getCourseCredit() {
            return courseCredit;
        }

        public void setCourseCredit(String courseCredit) {
            this.courseCredit = courseCredit;
        }

        public String getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(String courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseExaminationMethodCode() {
            return courseExaminationMethodCode;
        }

        public void setCourseExaminationMethodCode(String courseExaminationMethodCode) {
            this.courseExaminationMethodCode = courseExaminationMethodCode;
        }

        public String getCourseTeachingNumber() {
            return courseTeachingNumber;
        }

        public void setCourseTeachingNumber(String courseTeachingNumber) {
            this.courseTeachingNumber = courseTeachingNumber;
        }

        public String getCourseTotolHours() {
            return courseTotolHours;
        }

        public void setCourseTotolHours(String courseTotolHours) {
            this.courseTotolHours = courseTotolHours;
        }

        public String getCourseWeekNumber() {
            return courseWeekNumber;
        }

        public void setCourseWeekNumber(String courseWeekNumber) {
            this.courseWeekNumber = courseWeekNumber;
        }

        public String getCourseClassRoomCode() {
            return courseClassRoomCode;
        }

        public void setCourseClassRoomCode(String courseClassRoomCode) {
            this.courseClassRoomCode = courseClassRoomCode;
        }

        public String getCourseClassRoomName() {
            return courseClassRoomName;
        }

        public void setCourseClassRoomName(String courseClassRoomName) {
            this.courseClassRoomName = courseClassRoomName;
        }

        public String getCourseSubjectCourseNumber() {
            return courseSubjectCourseNumber;
        }

        public void setCourseSubjectCourseNumber(String courseSubjectCourseNumber) {
            this.courseSubjectCourseNumber = courseSubjectCourseNumber;
        }

        public String getCourseSectionWeek() {
            return courseSectionWeek;
        }

        public void setCourseSectionWeek(String courseSectionWeek) {
            this.courseSectionWeek = courseSectionWeek;
        }

        public String getCourseWeek() {
            return courseWeek;
        }

        public void setCourseWeek(String courseWeek) {
            this.courseWeek = courseWeek;
        }

        public String getCoursePlan() {
            return coursePlan;
        }

        public void setCoursePlan(String coursePlan) {
            this.coursePlan = coursePlan;
        }

        public String getCourseTeacherNumber() {
            return courseTeacherNumber;
        }

        public void setCourseTeacherNumber(String courseTeacherNumber) {
            this.courseTeacherNumber = courseTeacherNumber;
        }

        public String getCourseTeacherName() {
            return courseTeacherName;
        }

        public void setCourseTeacherName(String courseTeacherName) {
            this.courseTeacherName = courseTeacherName;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public void setUndergraduateJuniorCourseId(String undergraduateJuniorCourseId){
            this.undergraduateJuniorCourseId = undergraduateJuniorCourseId;
        }
        public String getUndergraduateJuniorCourseId(){
            return this.undergraduateJuniorCourseId;
        }
        public void setUndergraduateJuniorCourseCode(String undergraduateJuniorCourseCode){
            this.undergraduateJuniorCourseCode = undergraduateJuniorCourseCode;
        }
        public String getUndergraduateJuniorCourseCode(){
            return this.undergraduateJuniorCourseCode;
        }
        public void setUndergraduateJuniorCourseName(String undergraduateJuniorCourseName){
            this.undergraduateJuniorCourseName = undergraduateJuniorCourseName;
        }
        public String getUndergraduateJuniorCourseName(){
            return this.undergraduateJuniorCourseName;
        }
        public void setUndergraduateJuniorCourseDate(String undergraduateJuniorCourseDate){
            this.undergraduateJuniorCourseDate = undergraduateJuniorCourseDate;
        }
        public String getUndergraduateJuniorCourseDate(){
            return this.undergraduateJuniorCourseDate;
        }
        public void setUndergraduateJuniorCourseTeacherId(String undergraduateJuniorCourseTeacherId){
            this.undergraduateJuniorCourseTeacherId = undergraduateJuniorCourseTeacherId;
        }
        public String getUndergraduateJuniorCourseTeacherId(){
            return this.undergraduateJuniorCourseTeacherId;
        }
        public void setUndergraduateJuniorCourseTeacherName(String undergraduateJuniorCourseTeacherName){
            this.undergraduateJuniorCourseTeacherName = undergraduateJuniorCourseTeacherName;
        }
        public String getUndergraduateJuniorCourseTeacherName(){
            return this.undergraduateJuniorCourseTeacherName;
        }
        public void setUndergraduateJuniorCourseSchoolYear(String undergraduateJuniorCourseSchoolYear){
            this.undergraduateJuniorCourseSchoolYear = undergraduateJuniorCourseSchoolYear;
        }
        public String getUndergraduateJuniorCourseSchoolYear(){
            return this.undergraduateJuniorCourseSchoolYear;
        }
        public void setUndergraduateJuniorCourseTerm(String undergraduateJuniorCourseTerm){
            this.undergraduateJuniorCourseTerm = undergraduateJuniorCourseTerm;
        }
        public String getUndergraduateJuniorCourseTerm(){
            return this.undergraduateJuniorCourseTerm;
        }
        public void setUndergraduateJuniorCourseClassCode(String undergraduateJuniorCourseClassCode){
            this.undergraduateJuniorCourseClassCode = undergraduateJuniorCourseClassCode;
        }
        public String getUndergraduateJuniorCourseClassCode(){
            return this.undergraduateJuniorCourseClassCode;
        }
        public void setUndergraduateJuniorCourseClassName(String undergraduateJuniorCourseClassName){
            this.undergraduateJuniorCourseClassName = undergraduateJuniorCourseClassName;
        }
        public String getUndergraduateJuniorCourseClassName(){
            return this.undergraduateJuniorCourseClassName;
        }
        public void setUndergraduateJuniorCourseWeekly(String undergraduateJuniorCourseWeekly){
            this.undergraduateJuniorCourseWeekly = undergraduateJuniorCourseWeekly;
        }
        public String getUndergraduateJuniorCourseWeekly(){
            return this.undergraduateJuniorCourseWeekly;
        }
        public void setUndergraduateJuniorCourseSingleDoubleWeek(String undergraduateJuniorCourseSingleDoubleWeek){
            this.undergraduateJuniorCourseSingleDoubleWeek = undergraduateJuniorCourseSingleDoubleWeek;
        }
        public String getUndergraduateJuniorCourseSingleDoubleWeek(){
            return this.undergraduateJuniorCourseSingleDoubleWeek;
        }
        public void setUndergraduateJuniorCourseSection(String undergraduateJuniorCourseSection){
            this.undergraduateJuniorCourseSection = undergraduateJuniorCourseSection;
        }
        public String getUndergraduateJuniorCourseSection(){
            return this.undergraduateJuniorCourseSection;
        }
        public void setUndergraduateJuniorCourseAdressCode(String undergraduateJuniorCourseAdressCode){
            this.undergraduateJuniorCourseAdressCode = undergraduateJuniorCourseAdressCode;
        }
        public String getUndergraduateJuniorCourseAdressCode(){
            return this.undergraduateJuniorCourseAdressCode;
        }
        public void setUndergraduateJuniorCourseAdressName(String undergraduateJuniorCourseAdressName){
            this.undergraduateJuniorCourseAdressName = undergraduateJuniorCourseAdressName;
        }
        public String getUndergraduateJuniorCourseAdressName(){
            return this.undergraduateJuniorCourseAdressName;
        }
        public void setUndergraduateJuniorCoursePeople(String undergraduateJuniorCoursePeople){
            this.undergraduateJuniorCoursePeople = undergraduateJuniorCoursePeople;
        }
        public String getUndergraduateJuniorCoursePeople(){
            return this.undergraduateJuniorCoursePeople;
        }
        public void setUndergraduateJuniorCourseClassRoom(String undergraduateJuniorCourseClassRoom){
            this.undergraduateJuniorCourseClassRoom = undergraduateJuniorCourseClassRoom;
        }
        public String getUndergraduateJuniorCourseClassRoom(){
            return this.undergraduateJuniorCourseClassRoom;
        }

    }

}
