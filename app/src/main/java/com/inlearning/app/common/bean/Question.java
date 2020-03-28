package com.inlearning.app.common.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;


public class Question extends BmobObject {

    @IntDef({Type.CHOICE_QUESTION, Type.RESPONSE_QUESTION})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Type {
        int CHOICE_QUESTION = 0;
        int RESPONSE_QUESTION = 1;
    }

    @IntDef({ChoiceAnswer.A, ChoiceAnswer.B, ChoiceAnswer.C, ChoiceAnswer.D, ChoiceAnswer.E, ChoiceAnswer.F, ChoiceAnswer.G})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface ChoiceAnswer {
        int A = 0;
        int B = 1;
        int C = 2;
        int D = 3;
        int E = 4;
        int F = 5;
        int G = 6;
    }

    private BmobPointer mCourseChapter;
    private int mNum;
    private String mQuestionTitle;
    private BmobObject mQuestionImage;
    @Type
    private int mType;
    private List<Integer> mChoiceAnswers;


    public BmobPointer getCourseChapter() {
        return mCourseChapter;
    }

    public void setCourseChapter(BmobPointer courseChapter) {
        mCourseChapter = courseChapter;
    }

    public int getNum() {
        return mNum;
    }

    public void setNum(int num) {
        mNum = num;
    }

    public String getQuestionTitle() {
        return mQuestionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        mQuestionTitle = questionTitle;
    }

    public BmobObject getQuestionImage() {
        return mQuestionImage;
    }

    public void setQuestionImage(BmobObject questionImage) {
        mQuestionImage = questionImage;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public List<Integer> getChoiceAnswers() {
        return mChoiceAnswers;
    }

    public void setChoiceAnswers(List<Integer> choiceAnswers) {
        mChoiceAnswers = choiceAnswers;
    }
}
