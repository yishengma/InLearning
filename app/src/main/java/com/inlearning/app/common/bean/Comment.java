package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

    private String mContent;
    private String mImageUrl;
    private Post mPost;
    private Student mStudent;
    private Teacher mTeacher;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public Post getPost() {
        return mPost;
    }

    public void setPost(Post post) {
        mPost = post;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public Teacher getTeacher() {
        return mTeacher;
    }

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
    }
}
