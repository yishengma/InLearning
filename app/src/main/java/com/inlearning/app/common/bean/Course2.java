package com.inlearning.app.common.bean;

import androidx.annotation.Nullable;

import java.util.Objects;

import cn.bmob.v3.BmobObject;

public class Course2 extends BmobObject {
    private String mName;
    private String mType;
    private String mTime;
    private String mScore;
    private boolean isSelected;

    public String getName() {
        return mName == null ? "" : mName;
    }

    public Course2 setName(String name) {
        mName = name;
        return this;
    }

    public String getType() {
        return mType == null ? "" : mType;
    }

    public Course2 setType(String type) {
        mType = type;
        return this;
    }

    public String getTime() {
        return mTime == null ? "" : mTime;
    }

    public Course2 setTime(String time) {
        mTime = time;
        return this;
    }

    public String getScore() {
        return mScore == null ? "" : mScore;
    }

    public Course2 setScore(String score) {
        mScore = score;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Course2 setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    @Override
    public String toString() {
        return "Course2{" +
                "mName='" + mName + '\'' +
                ", mType='" + mType + '\'' +
                ", mTime='" + mTime + '\'' +
                ", mScore='" + mScore + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course2 course2 = (Course2) o;
        return isSelected == course2.isSelected &&
                Objects.equals(mName, course2.mName) &&
                Objects.equals(mType, course2.mType) &&
                Objects.equals(mTime, course2.mTime) &&
                Objects.equals(mScore, course2.mScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mType, mTime, mScore, isSelected);
    }
}
