package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class StudentClassSchedule extends BmobObject {
    private Student mStudent;
    private ClassSchedule mClassSchedule;

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public ClassSchedule getClassSchedule() {
        return mClassSchedule;
    }

    public void setClassSchedule(ClassSchedule classSchedule) {
        mClassSchedule = classSchedule;
    }
}
