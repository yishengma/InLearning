package com.inlearning.app.common.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Answer extends BmobObject {
    private List<String> mChoiceAnswers;
    private String mImageUrl;
    private Question mQuestion;
    private Student mStudent;
    private CourseChapter mChapter;
    private ClassInfo mClassInfo;

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

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public CourseChapter getChapter() {
        return mChapter;
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }

    public ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public Answer setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
        return this;
    }
}
