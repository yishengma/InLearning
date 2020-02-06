package com.inlearning.app.common.bean;

public class Student extends User {
    private String mClass;
    public String getStudentClass() {
        return mClass == null ? "" : mClass;
    }

    public Student setStudentClass(String aClass) {
        mClass = aClass;
        return this;
    }
}

