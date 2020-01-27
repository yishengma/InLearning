package com.yishengma.inlearning.bean;

import java.util.List;

public class CourseBean {
    private String mId;
    private String mName;
    private String mTime;
    private String mType;//课程类别，专业课，选修课
    private int mStudentCount;//人数
    private String mIconUrl;
    private TeacherBean mTeacher;
    private List<ChapterBean> mChapters;

    public CourseBean() {
        mName = "计算机科学与技术";
        mTime = "第7-9周";
        mType = "专业课";
        mStudentCount = 456;
        mIconUrl = "";
    }

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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
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
