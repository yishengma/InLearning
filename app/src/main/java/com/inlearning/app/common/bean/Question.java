package com.inlearning.app.common.bean;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;


public class Question extends BmobObject {

    @IntDef({Type.CHOICE_QUESTION, Type.RESPONSE_QUESTION})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Type {
        int CHOICE_QUESTION = 2;
        int RESPONSE_QUESTION = 3;
    }

    @StringDef({ChoiceAnswer.A, ChoiceAnswer.B, ChoiceAnswer.C, ChoiceAnswer.D, ChoiceAnswer.E, ChoiceAnswer.F, ChoiceAnswer.G})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    public @interface ChoiceAnswer {
        String A = "A";
        String B = "B";
        String C = "C";
        String D = "D";
        String E = "E";
        String F = "F";
        String G = "G";

    }

    private CourseChapter mCourseChapter;
    private int mNum;
    private String mQuestionTitle;
    private String mQuestionImage;
    @Type
    private int mType;
    private List<String> mChoiceAnswers;


    public CourseChapter getCourseChapter() {
        return mCourseChapter;
    }

    public void setCourseChapter(CourseChapter courseChapter) {
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

    public String getQuestionImage() {
        return mQuestionImage;
    }

    public void setQuestionImage(String questionImage) {
        mQuestionImage = questionImage;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public List<String> getChoiceAnswers() {
        return mChoiceAnswers;
    }

    public void setChoiceAnswers(List<String> choiceAnswers) {
        mChoiceAnswers = choiceAnswers;
    }
}
