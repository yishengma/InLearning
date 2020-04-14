package com.inlearning.app.common.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Teacher extends BmobObject {
    private String mAccount;
    private String mPassword;
    private String mTitle;
    private boolean isSelected;//不做Bmob
    public String mName;
    public BmobFile mProfilePhoto;
    private String mProfilePhotoUrl;
    private BmobRelation mCourses;

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

    public String getAccount() {
        return mAccount;
    }

    public Teacher setAccount(String account) {
        mAccount = account;
        return this;
    }

    public String getPassword() {
        return mPassword;
    }

    public Teacher setPassword(String password) {
        mPassword = password;
        return this;
    }

    public BmobRelation getCourses() {
        return mCourses;
    }

    public void setCourses(BmobRelation courses) {
        mCourses = courses;
    }
}