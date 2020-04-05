package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class StudentCourseChapter extends BmobObject {
    private Student mStudent;
    private CourseChapter mCourseChapter;

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public CourseChapter getCourseChapter() {
        return mCourseChapter;
    }

    public void setCourseChapter(CourseChapter courseChapter) {
        mCourseChapter = courseChapter;
    }
}
