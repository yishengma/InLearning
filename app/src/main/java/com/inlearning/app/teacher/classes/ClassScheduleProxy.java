package com.inlearning.app.teacher.classes;

import com.inlearning.app.common.bean.ClassSchedule;

public class ClassScheduleProxy {
    public static final int ITEM_SEPARATE = 1;
    public static final int ITEM_CLASS_INFO = 2;
    private String mCourse;
    private ClassSchedule mSchedule;
    private int mType ;

    public String getCourse() {
        return mCourse == null ? "" : mCourse;
    }

    public ClassScheduleProxy setCourse(String course) {
        mCourse = course;
        return this;
    }

    public ClassSchedule getSchedule() {
        return mSchedule;
    }

    public ClassScheduleProxy setSchedule(ClassSchedule schedule) {
        mSchedule = schedule;
        return this;
    }

    public int getType() {
        return mType;
    }

    public ClassScheduleProxy setType(int type) {
        mType = type;
        return this;
    }
}
