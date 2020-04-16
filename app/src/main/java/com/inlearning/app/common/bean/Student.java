package com.inlearning.app.common.bean;



import java.util.Objects;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Student extends BmobObject {
    private ClassInfo mClassInfo;
    private String mSex;
    private boolean mIsSelected;
    public String mAccount;
    public String mPassword;
    public String mName;
    public BmobFile mProfilePhoto;
    private String mProfilePhotoUrl;
    
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

    
    public String getAccount() {
        return mAccount;
    }

    
    public Student setAccount(String account) {
        mAccount = account;
        return this;
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

    
    public Student setName(String name) {
        mName = name;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getObjectId(), student.getObjectId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObjectId());
    }
}

