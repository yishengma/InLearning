package com.inlearning.app.common.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class Speciality extends BmobObject {
    private String mName;
    private String mShortName;
    private int mClassCount;
    private List<ClassInfo> mClassInfoList;

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

    public List<ClassInfo> getClassInfoList() {
        if (mClassInfoList == null) {
            return new ArrayList<>();
        }
        return mClassInfoList;
    }

    public Speciality setClassInfoList(List<ClassInfo> classInfoList) {
        mClassInfoList = classInfoList;
        return this;
    }

    public int getClassCount() {
        return mClassCount;
    }

    public Speciality setClassCount(int classCount) {
        mClassCount = classCount;
        return this;
    }
}
