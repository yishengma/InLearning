package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class ChapterProgress extends BmobObject {

    private CourseChapter mChapter;
    private Student mStudent;
    private long mStudyDuration;
    private String mVideoUrl;
    private boolean mDone;

    public long getStudyDuration() {
        return mStudyDuration;
    }

    public void setStudyDuration(long studyDuration) {
        mStudyDuration = studyDuration;
    }

    public CourseChapter getChapter() {
        return mChapter;
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }
}
