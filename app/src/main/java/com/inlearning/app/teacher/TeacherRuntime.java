package com.inlearning.app.teacher;

import com.inlearning.app.common.bean.ClassSchedule;

import java.util.ArrayList;
import java.util.List;

public class TeacherRuntime {

    private static List<ClassSchedule> mSchedules;

    public static List<ClassSchedule> getSchedules() {
        if (mSchedules == null) {
            return new ArrayList<>();
        }
        return mSchedules;
    }

    public static void setSchedules(List<ClassSchedule> mSchedules) {
        TeacherRuntime.mSchedules = mSchedules;
    }
}
