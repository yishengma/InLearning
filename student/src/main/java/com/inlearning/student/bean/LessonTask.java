package com.inlearning.student.bean;

public class LessonTask {
    private String mLessonName;
    private String mLessonIconUrl;
    private String mLessonState;
    private String mTeacherName;
    private String mTeacherIconUrl;
    private int mTotalChapters;
    private int mHasLearnedChapters;

    //for test
    public LessonTask(String lessonName, String lessonIconUrl, String lessonState, String teacherName, String teacherIconUrl, int totalChapters, int hasLearnedChapters) {
        mLessonName = lessonName;
        mLessonIconUrl = lessonIconUrl;
        mLessonState = lessonState;
        mTeacherName = teacherName;
        mTeacherIconUrl = teacherIconUrl;
        mTotalChapters = totalChapters;
        mHasLearnedChapters = hasLearnedChapters;
    }

    public String getLessonName() {
        return mLessonName;
    }

    public void setLessonName(String lessonName) {
        mLessonName = lessonName;
    }

    public String getLessonIconUrl() {
        return mLessonIconUrl;
    }

    public void setLessonIconUrl(String lessonIconUrl) {
        mLessonIconUrl = lessonIconUrl;
    }

    public String getLessonState() {
        return mLessonState;
    }

    public void setLessonState(String lessonState) {
        mLessonState = lessonState;
    }

    public String getTeacherName() {
        return mTeacherName;
    }

    public void setTeacherName(String teacherName) {
        mTeacherName = teacherName;
    }

    public String getTeacherIconUrl() {
        return mTeacherIconUrl;
    }

    public void setTeacherIconUrl(String teacherIconUrl) {
        mTeacherIconUrl = teacherIconUrl;
    }

    public int getTotalChapters() {
        return mTotalChapters;
    }

    public void setTotalChapters(int totalChapters) {
        mTotalChapters = totalChapters;
    }

    public int getHasLearnedChapters() {
        return mHasLearnedChapters;
    }

    public void setHasLearnedChapters(int hasLearnedChapters) {
        mHasLearnedChapters = hasLearnedChapters;
    }
}
