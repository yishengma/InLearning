package com.inlearning.app.teacher;

import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherRuntime {

    private static List<ClassSchedule> mSchedules;
    private static Teacher mCurrentTeacher;

    public static List<ClassSchedule> getSchedules() {
        if (mSchedules == null) {
            return new ArrayList<>();
        }
        return mSchedules;
    }

    public static void setSchedules(List<ClassSchedule> mSchedules) {
        TeacherRuntime.mSchedules = mSchedules;
    }


    public static Teacher getCurrentTeacher() {
        return mCurrentTeacher;
    }

    public static void setCurrentTeacher(Teacher mCurrentTeacher) {
        TeacherRuntime.mCurrentTeacher = mCurrentTeacher;
    }
}
