package com.inlearning.app.director;

import android.content.Context;

import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

public class DirectorAppRuntime {
    private static Context sContext;
    private static List<Speciality> sSpecialities;
    private static List<Teacher> sTeachers;
    private static List<Course2> sCourse2s;

    public static Context getApplicationContext() {
        return sContext;
    }

    public static void setApplicationContext(Context mContext) {
        DirectorAppRuntime.sContext = mContext;
    }

    public static List<Speciality> getSpecialities() {
        if (sSpecialities == null) {
            return new ArrayList<>();
        }
        return sSpecialities;
    }

    public static void setSpecialities(List<Speciality> specialities) {
        sSpecialities = specialities;
    }

    public static List<Teacher> getTeachers() {
        if (sTeachers == null) {
            return new ArrayList<>();
        }
        return sTeachers;
    }

    public static void setTeachers(List<Teacher> teachers) {
        sTeachers = teachers;
    }

    public static List<Course2> getCourse2s() {
        if (sCourse2s == null) {
            return new ArrayList<>();
        }
        return sCourse2s;
    }

    public static void setCourse2s(List<Course2> course2s) {
        sCourse2s = course2s;
    }
}
