package com.inlearning.app.director.person.coursemanager.classes;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;

public class ClassScheduletBean {

    private ClassInfo mClassInfo;

    private ClassSchedule mClassSchedule;


    public ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public ClassScheduletBean setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
        return this;
    }

    public ClassSchedule getClassSchedule() {
        return mClassSchedule;
    }

    public ClassScheduletBean setClassSchedule(ClassSchedule classSchedule) {
        mClassSchedule = classSchedule;
        return this;
    }
}
