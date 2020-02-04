package com.inlearning.student.bean;

public class QuestionBean {
    private String mImageUrl;
    private String mContent;

    private String getImageUrl() {
        return mImageUrl == null ? "" : mImageUrl;
    }

    private QuestionBean setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        return this;
    }

    private String getContent() {
        return mContent == null ? "" : mContent;
    }

    private QuestionBean setContent(String content) {
        mContent = content;
        return this;
    }
}
