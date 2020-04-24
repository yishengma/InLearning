package com.inlearning.app.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class Speciality extends BmobObject {
    private String mName;
    private String mShortName;
    private int mClassCount;

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

    public int getClassCount() {
        return mClassCount;
    }

    public Speciality setClassCount(int classCount) {
        mClassCount = classCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speciality that = (Speciality) o;
        return Objects.equals(mName, that.mName) &&
                Objects.equals(mShortName, that.mShortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mShortName);
    }
}
