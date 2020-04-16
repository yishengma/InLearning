package com.inlearning.app.director.person.coursemanager.classes;


import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ClassCourseModel {

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void getClassCourse(ClassInfo classInfo, final Callback<List<ClassSchedule>> callback) {
        BmobQuery<ClassSchedule> query = new BmobQuery<>();
        query.include("mCourse2,mTeacher");
        query.addWhereEqualTo("mClassInfo", classInfo);

        BmobQuery<Teacher> inTeacherQuery = new BmobQuery<>();
        inTeacherQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mTeacher", "Teacher", inTeacherQuery);

        BmobQuery<Course2> inCourseQuery = new BmobQuery<>();
        inCourseQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mCourse2", "Course2", inCourseQuery);

        query.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(final List<ClassSchedule> list, final BmobException e) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {

                        if (e == null) {
                            callback.onResult(true, list);
                            return;
                        }
                    }
                });
            }
        });
    }

    public static void deleteClassCourse(final ClassSchedule schedule, final Callback<ClassSchedule> callback) {
        schedule.delete(new UpdateListener() {
            @Override
            public void done(final BmobException e) {
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (e == null) {
                            callback.onResult(true, schedule);
                        }
                    }
                });
            }
        });
    }

    public static void addClassCourse(final ClassSchedule schedule, final Callback<ClassSchedule> callback) {
        schedule.save(new SaveListener<String>() {
            @Override
            public void done(final String s, BmobException e) {

                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (e == null) {
                            schedule.setObjectId(s);
                            callback.onResult(true, schedule);
                        } else {
                            callback.onResult(false, null);
                        }

                    }
                });

            }
        });
    }

    public static void getClassInfo(Speciality speciality, Callback<List<ClassInfo>> callback) {
        BmobQuery<ClassInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("mSpeciality", speciality);
        query.include("mSpeciality");
        query.findObjects(new FindListener<ClassInfo>() {
            @Override
            public void done(List<ClassInfo> list, BmobException e) {
                if (e == null) {
                    callback.onResult(true, list);
                }
            }
        });
    }
}
