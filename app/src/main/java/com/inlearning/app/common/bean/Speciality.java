package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class Speciality extends BmobObject {
    private String mName;
    private String mShortName;


    public String getName() {
        return mName == null ? "" : mName;
    }

    public Speciality setName(String name) {
        mName = name;
        return this;
    }

    public String getShortName() {
        return mShortName == null ? "" : mShortName;
    }

    public Speciality setShortName(String shortName) {
        mShortName = shortName;
        return this;
    }
}
