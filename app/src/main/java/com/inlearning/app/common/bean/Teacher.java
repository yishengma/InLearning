package com.inlearning.app.common.bean;

import java.util.List;

public class Teacher extends User {
    private String mJobNumber;
    private String mTitle;
    private boolean isSelected;//不做Bmob
    private List<Course2> mCourse2s;

    public String getTitle() {
        return mTitle == null ? "" : mTitle;
    }

    public Teacher setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public Teacher setName(String name) {
        mName = name;
        return this;
    }

    public String getJobNumber() {
        return mJobNumber == null ? "" : mJobNumber;
    }

    public Teacher setJobNumber(String jobNumber) {
        mJobNumber = jobNumber;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Teacher setSeleted(boolean seleted) {
        isSelected = seleted;
        return this;
    }

    public List<Course2> getCourse2s() {
        return mCourse2s;
    }

    public void setCourse2s(List<Course2> course2s) {
        mCourse2s = course2s;
    }
}