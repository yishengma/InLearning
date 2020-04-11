package com.inlearning.app.common.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Teacher extends BmobObject {
    private String mJobNumber;
    private String mTitle;
    private boolean isSelected;//不做Bmob
    public String mName;
    public BmobFile mProfilePhoto;
    private String mProfilePhotoUrl;

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

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public BmobFile getProfilePhoto() {
        return mProfilePhoto;
    }

    public void setProfilePhoto(BmobFile profilePhoto) {
        mProfilePhoto = profilePhoto;
    }

    public String getProfilePhotoUrl() {
        return mProfilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        mProfilePhotoUrl = profilePhotoUrl;
    }
}