package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class TeacherCourse extends BmobObject {

    private Teacher mTeacher;
    private Course2 mCourse2;

    public TeacherCourse(Teacher teacher, Course2 course2) {
        mTeacher = teacher;
        mCourse2 = course2;
    }

    public Teacher getTeacher() {
        return mTeacher;
    }

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
    }

    public Course2 getCourse2() {
        return mCourse2;
    }

    public void setCourse2(Course2 course2) {
        mCourse2 = course2;
    }
}
