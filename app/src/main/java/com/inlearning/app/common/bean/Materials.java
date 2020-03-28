package com.inlearning.app.common.bean;

import cn.bmob.v3.datatype.BmobFile;

public class Materials extends BmobFile {
    private CourseChapter mChapter;
    private BmobFile mMaterialFile;
    private String mMaterialName;

    public CourseChapter getChapter() {
        return mChapter;
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }

    public BmobFile getMaterialFile() {
        return mMaterialFile;
    }

    public void setMaterialFile(BmobFile materialFile) {
        mMaterialFile = materialFile;
    }

    public String getMaterialName() {
        return mMaterialName;
    }

    public void setMaterialName(String materialName) {
        mMaterialName = materialName;
    }
}
