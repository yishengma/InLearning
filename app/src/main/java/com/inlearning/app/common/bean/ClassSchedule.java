package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class ClassSchedule extends BmobObject {
    private ClassInfo mClassInfo;
    private Course2 mCourse2;
    private Teacher mTeacher;


    public ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public ClassSchedule setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
        return this;
    }

    public Course2 getCourse2() {
        return mCourse2;
    }

    public ClassSchedule setCourse2(Course2 course2) {
        mCourse2 = course2;
        return this;
    }

    public Teacher getTeacher() {
        return mTeacher;
    }

    public ClassSchedule setTeacher(Teacher teacher) {
        mTeacher = teacher;
        return this;
    }
}
