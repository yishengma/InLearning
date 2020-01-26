package com.yishengma.inlearning.bean;

import java.util.List;

public class CourseBean {
    private String mId;
    private String mName;
    private String mTime;
    private int mType;//课程类别，专业课，选修课
    private int mStudentCount;//人数
    private String mIconUrl;
    private TeacherBean mTeacher;
    private List<ChapterBean> mChapters;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public int getStudentCount() {
        return mStudentCount;
    }

    public void setStudentCount(int studentCount) {
        this.mStudentCount = studentCount;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public TeacherBean getTeacher() {
        return mTeacher;
    }

    public void setTeacher(TeacherBean teacher) {
        this.mTeacher = teacher;
    }

    public List<ChapterBean> getChapters() {
        return mChapters;
    }

    public void setChapters(List<ChapterBean> chapters) {
        this.mChapters = chapters;
    }
}
