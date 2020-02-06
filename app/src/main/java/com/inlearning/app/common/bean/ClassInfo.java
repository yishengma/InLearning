package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class ClassInfo extends BmobObject {
    private String mName;
    private int mCount;
    private int mType;

    public String getName() {
        return mName == null ? "" : mName;
    }

    public ClassInfo setName(String name) {
        mName = name;
        return this;
    }

    public int getCount() {
        return mCount;
    }

    public int getType() {
        return mType;
    }

    public ClassInfo setType(int type) {
        mType = type;
        return this;
    }

    public ClassInfo setCount(int count) {
        mCount = count;
        return this;
    }


}
