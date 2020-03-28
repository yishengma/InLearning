package com.inlearning.app.common.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

//一个章节对应一门课，一门课下有多个章节

public class CourseChapter extends BmobObject {
    private BmobPointer mTeacher;
    private BmobPointer mCourse2;
    private int mChapterNum;
    private String mChapterName;
    private BmobFile mVideoFile;
    private String mDeadLine;
    private int mMaterialCount;
    private int mExerciseCount;
    private int mHomeworkCount;
    private int mDiscussCount;


    public int getChapterNum() {
        return mChapterNum;
    }

    public void setChapterNum(int chapterNum) {
        mChapterNum = chapterNum;
    }

    public String getChapterName() {
        return mChapterName;
    }

    public void setChapterName(String chapterName) {
        mChapterName = chapterName;
    }

    public BmobFile getVideoFile() {
        return mVideoFile;
    }

    public void setVideoFile(BmobFile videoFile) {
        mVideoFile = videoFile;
    }

    public String getDeadLine() {
        return mDeadLine;
    }

    public void setDeadLine(String deadLine) {
        mDeadLine = deadLine;
    }

    public BmobPointer getTeacher() {
        return mTeacher;
    }

    public void setTeacher(BmobPointer teacher) {
        mTeacher = teacher;
    }

    public BmobPointer getCourse2() {
        return mCourse2;
    }

    public void setCourse2(BmobPointer course2) {
        mCourse2 = course2;
    }

    public int getMaterialCount() {
        return mMaterialCount;
    }

    public void setMaterialCount(int materialCount) {
        mMaterialCount = materialCount;
    }

    public int getExerciseCount() {
        return mExerciseCount;
    }

    public void setExerciseCount(int exercisCount) {
        mExerciseCount = exercisCount;
    }

    public int getHomeworkCount() {
        return mHomeworkCount;
    }

    public void setHomeworkCount(int homeworkCount) {
        mHomeworkCount = homeworkCount;
    }

    public int getDiscussCount() {
        return mDiscussCount;
    }

    public void setDiscussCount(int discussCount) {
        mDiscussCount = discussCount;
    }
}
