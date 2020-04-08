package com.inlearning.app.common.bean;

import cn.bmob.v3.BmobObject;

public class ChapterProgress extends BmobObject {

    private CourseChapter mChapter;
    private Student mStudent;
    private long mStudyDuration;
    private String mVideoUrl;
    private boolean mDone;
    private ClassInfo mClassInfo;

    public long getStudyDuration() {
        return mStudyDuration;
    }

    public ChapterProgress setStudyDuration(long studyDuration) {
        mStudyDuration = studyDuration;
        return this;
    }

    public CourseChapter getChapter() {
        return mChapter;
    }

    public ChapterProgress setChapter(CourseChapter chapter) {
        mChapter = chapter;
        return this;
    }

    public Student getStudent() {
        return mStudent;
    }

    public ChapterProgress setStudent(Student student) {
        mStudent = student;
        return this;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public ChapterProgress setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
        return this;
    }

    public boolean isDone() {
        return mDone;
    }

    public ChapterProgress setDone(boolean done) {
        mDone = done;
        return this;
    }

    public ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public void setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
    }
}
