package com.inlearning.student.bean;

public class AnswerBean {
    private String mImageUrl;
    private String mContent;
    private String mDateTime;
    private int mAgreeCount;

    private String getImageUrl() {
        return mImageUrl == null ? "" : mImageUrl;
    }

    private AnswerBean setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        return this;
    }

    private String getContent() {
        return mContent == null ? "" : mContent;
    }

    private AnswerBean setContent(String content) {
        mContent = content;
        return this;
    }

    private String getDateTime() {
        return mDateTime == null ? "" : mDateTime;
    }

    private AnswerBean setDateTime(String dateTime) {
        mDateTime = dateTime;
        return this;
    }

    private int getAgreeCount() {
        return mAgreeCount;
    }

    private AnswerBean setAgreeCount(int agreeCount) {
        mAgreeCount = agreeCount;
        return this;
    }
}
