package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class StudentQuestion extends BmobObject {
    private Student mStudent;
    private ClassInfo mClassInfo;
    private Course2 mCourse2;
    private Question mQuestion;
    private CourseChapter mChapter;

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
    }

    public Course2 getCourse2() {
        return mCourse2;
    }

    public void setCourse2(Course2 course2) {
        mCourse2 = course2;
    }

    public Question getQuestion() {
        return mQuestion;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }

    public CourseChapter getChapter() {
        return mChapter;
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }
}
