package com.inlearning.app.common.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Materials extends BmobObject {
    @IntDef({Materials.Type.PPT, Materials.Type.PDF, Materials.Type.DOC})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Type {
        int PPT = 0;
        int PDF = 1;
        int DOC = 2;
    }

    private CourseChapter mChapter;
    private BmobFile mMaterialFile;
    private String mMaterialName;
    @Type
    private int mType;

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

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public static int getType(String fileName) {
        if (fileName.endsWith("ppt") || fileName.endsWith("pptx")) {
            return Type.PPT;
        } else if (fileName.endsWith("pdf")) {
            return Type.PDF;
        } else if (fileName.endsWith("doc") || fileName.endsWith("docx")) {
            return Type.DOC;
        }
        return -1;
    }
}
