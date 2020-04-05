package com.inlearning.app.student;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;

public class StudentRuntime {
    private static ClassInfo mClassInfo;
    private static Student mStudent;

    public static ClassInfo getClassInfo() {
        return mClassInfo;
    }

    public static void setClassInfo(ClassInfo classInfo) {
        mClassInfo = classInfo;
    }

    public static Student getStudent() {
        return mStudent;
    }

    public static void setStudent(Student student) {
        mClassInfo = student.getClassInfo();
        mStudent = student;
    }
}
