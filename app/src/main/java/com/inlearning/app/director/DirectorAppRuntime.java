package com.inlearning.app.director;

import android.content.Context;
import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.bean.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectorAppRuntime {
    private static Context sContext;
    private static List<Speciality> sSpecialities;
    private static List<Teacher> sTeachers;
    private static List<Course2> sCourse2s;
    private static HashMap<Speciality, List<ClassInfo>> sClassInfo = new HashMap<>();
    private static HashMap<Speciality, List<SpecialitySchedule>> sSchedule = new HashMap<>();
    private static Director sDirector;

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

    public static Director getsDirector() {
        return sDirector;
    }

    public static void setsDirector(Director sDirector) {
        DirectorAppRuntime.sDirector = sDirector;
    }

    public static synchronized List<ClassInfo> getsClassInfo() {
        List<ClassInfo> classInfos = new ArrayList<>();
        for (Speciality s : sClassInfo.keySet()) {
            classInfos.addAll(sClassInfo.get(s));
        }
        return classInfos;
    }

    public static synchronized void setsClassInfo(Speciality speciality, List<ClassInfo> classInfos) {
        sClassInfo.put(speciality, classInfos);
    }

    public static List<SpecialitySchedule> getsSchedule(Speciality speciality) {
        List<SpecialitySchedule> schedules = new ArrayList<>();
        schedules.addAll(sSchedule.get(speciality) == null ? new ArrayList<>() : sSchedule.get(speciality));
        return schedules;
    }

    public static void setsSchedule(Speciality speciality, List<SpecialitySchedule> schedules) {
        sSchedule.put(speciality, schedules);
    }
}
