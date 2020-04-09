package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class Post extends BmobObject {
    private String mTitle;
    private String mContent;
    private Student mStudent;//发布者
    private String mImageUrl;
    private CourseChapter mChapter;


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public CourseChapter getChapter() {
        return mChapter;
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }
}
