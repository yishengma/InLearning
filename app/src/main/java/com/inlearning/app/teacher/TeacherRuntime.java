package com.inlearning.app.teacher;

import android.content.Context;

import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

public class TeacherRuntime {

    private static List<ClassSchedule> mSchedules;
    private static Teacher mCurrentTeacher;
    private static Context sContext;

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


    public static void setApplicationContext(Context mContext) {
        sContext = mContext;
    }
}
