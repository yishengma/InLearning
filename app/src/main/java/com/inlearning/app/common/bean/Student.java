package com.inlearning.app.common.bean;

public class Student extends User {
    private ClassInfo mClassInfo;
    private String mSex;
    private boolean mIsSelected;

    public ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public Student setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
        return this;
    }

    public String getSex() {
        return mSex == null ? "" : mSex;
    }

    public Student setSex(String sex) {
        mSex = sex;
        return this;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public Student setSelected(boolean selected) {
        mIsSelected = selected;
        return this;
    }
}

