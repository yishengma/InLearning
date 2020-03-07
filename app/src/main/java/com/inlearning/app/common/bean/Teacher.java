package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class Teacher extends BmobObject {
    private String mJobNumber;
    private String mTitle;
    private String mName;
    private boolean isSelected;//不做Bmob
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
}