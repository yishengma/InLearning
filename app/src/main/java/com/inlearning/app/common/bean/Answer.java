package com.inlearning.app.common.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Answer extends BmobObject {
    private List<String> mChoiceAnswers;
    private String mImageUrl;
    private Question mQuestion;

    public List<String> getChoiceAnswers() {
        return mChoiceAnswers;
    }

    public void setChoiceAnswers(List<String> choiceAnswers) {
        mChoiceAnswers = choiceAnswers;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public Question getQuestion() {
        return mQuestion;
    }

    public void setQuestion(Question question) {
        mQuestion = question;
    }
}
