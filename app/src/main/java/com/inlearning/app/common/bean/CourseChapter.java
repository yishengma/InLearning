package com.inlearning.app.common.bean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;

//一个章节对应一门课，一门课下有多个章节

public class CourseChapter extends BmobObject {
    private Teacher mTeacher;
    private Course2 mCourse2;
    private int mChapterNum;
    private String mChapterName;
    private BmobFile mVideoFile;
    private long mVideoDuration;
    private String mDeadLine;
    private Integer mMaterialCount;
    private Integer mExerciseCount;
    private Integer mHomeworkCount;
    private Integer mDiscussCount;


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

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
    }

    public void setCourse2(Course2 course2) {
        mCourse2 = course2;
    }

    public int getMaterialCount() {
        return mMaterialCount == null ? 0 : mMaterialCount;
    }

    public void setMaterialCount(int materialCount) {
        mMaterialCount = materialCount;
    }

    public int getExerciseCount() {
        return mExerciseCount == null ? 0 : mExerciseCount;
    }

    public void setExerciseCount(int exercisCount) {
        mExerciseCount = exercisCount;
    }

    public int getHomeworkCount() {
        return mHomeworkCount == null ? 0 : mHomeworkCount;
    }

    public void setHomeworkCount(int homeworkCount) {
        mHomeworkCount = homeworkCount;
    }

    public int getDiscussCount() {
        return mDiscussCount == null ? 0 : mDiscussCount;
    }

    public void setDiscussCount(int discussCount) {
        mDiscussCount = discussCount;
    }

    public long getVideoDuration() {
        return mVideoDuration;
    }

    public void setVideoDuration(long videoDuration) {
        mVideoDuration = videoDuration;
    }
}
