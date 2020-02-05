package com.inlearning.common.bean;

public class Teacher extends User{
    private String mTitle;

    public String getTitle() {
        return mTitle == null ? "" : mTitle;
    }

    public Teacher setTitle(String title) {
        mTitle = title;
        return this;
    }
}
