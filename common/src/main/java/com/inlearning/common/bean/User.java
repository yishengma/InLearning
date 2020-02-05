package com.inlearning.common.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public abstract class User extends BmobObject {
    public String mAccount;
    public String mPassword;
    public String mName;
    public BmobFile mProfilePhoto;
    private String mProfilePhotoUrl;

    @Type
    public int mType;

    @IntDef({Type.STUDENT, Type.TEACHER, Type.DIRECTOR})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Type {
        int STUDENT = 0;
        int TEACHER = 1;
        int DIRECTOR = 2;
    }

    public String getAccount() {
        return mAccount == null ? "" : mAccount;
    }

    public User setAccount(String account) {
        mAccount = account;
        return this;
    }

    public String getPassword() {
        return mPassword == null ? "" : mPassword;
    }

    public User setPassword(String password) {
        mPassword = password;
        return this;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public User setName(String name) {
        mName = name;
        return this;
    }

    public BmobFile getProfilePhoto() {
        return mProfilePhoto;
    }

    public User setProfilePhoto(BmobFile profilePhoto) {
        mProfilePhoto = profilePhoto;
        return this;
    }

    public  @Type
    int getType() {
        return mType;
    }

    public User setType(@Type int type) {
        mType = type;
        return this;
    }

    public String getProfilePhotoUrl() {
        return getProfilePhoto().getUrl();
    }

}
