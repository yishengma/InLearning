package com.inlearning.app.common.bean;

public class HomeworkProgress {
    private Student mStudent;
    private int mProgress;

    public HomeworkProgress(Student student, int progress) {
        mStudent = student;
        mProgress = progress;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }
}
