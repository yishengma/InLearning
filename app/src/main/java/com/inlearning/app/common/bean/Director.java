package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Director extends BmobObject {
    public String mAccount;
    public String mPassword;
    public String mName;
    public BmobFile mProfilePhoto;
    private String mProfilePhotoUrl;

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
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