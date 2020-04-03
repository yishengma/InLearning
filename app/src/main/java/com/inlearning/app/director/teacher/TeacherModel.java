package com.inlearning.app.director.teacher;

import android.util.Log;

import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.bean.TeacherCourse;
import com.inlearning.app.common.util.ThreadMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherModel {
    private static final String TAG = "TeacherModel";

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void getTeacherList(final Callback<List<Teacher>> callback) {
        BmobQuery<Teacher> query = new BmobQuery<>();
        query.findObjects(new FindListener<Teacher>() {
            @Override
            public void done(List<Teacher> list, BmobException e) {
                if (e == null) {
                    callback.onResult(true, list);
                }
                Log.e(TAG, "" + e);
            }
        });
    }

    public static void addTeacher(final Teacher teacher, final Callback<Teacher> callback) {
        if (teacher == null) {
            return;
        }
        teacher.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    teacher.setObjectId(s);
                    callback.onResult(true, teacher);
                }
                Log.e(TAG, "addTeacher" + e);
            }
        });
    }


    public static void updateTeacher(final Teacher teacher, final List<Course2> course2s, final Callback<Teacher> callback) {
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                final AtomicInteger integer = new AtomicInteger(0);
                if (teacher == null) {
                    return;
                }
                teacher.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            integer.incrementAndGet();
                        }
                    }
                });
                List<TeacherCourse> teacherCourses = new ArrayList<>();
                for (Course2 course2 : course2s) {
                    teacherCourses.add(new TeacherCourse(teacher, course2));
                }
                if (teacherCourses.isEmpty()) {
                    integer.incrementAndGet();
                    while (integer.get() != 2) {

                    }
                    callback.onResult(true, teacher);
                    return;
                }
                List<BmobObject> list = new ArrayList<BmobObject>(teacherCourses);
                new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {
                    @Override
                    public void done(List<BatchResult> list, BmobException e) {
                        if (e == null) {
                            integer.incrementAndGet();
                        }
                    }
                });
                while (integer.get() != 2) {

                }
                callback.onResult(true, teacher);
            }
        });
    }

    public static void addTeacherList(List<Teacher> teachers, final Callback<List<Teacher>> callback) {
        List<BmobObject> list = new ArrayList<BmobObject>(teachers);
        new BmobBatch().updateBatch(list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                Log.e("done", "" + e);
                if (e == null) {
                    callback.onResult(true, null);
                }
            }
        });

    }


    public static void deleteTeacher(Teacher teacher, final Callback<Teacher> callback) {
        teacher.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true, null);
                }
            }
        });
    }

    public static void getTeacherCourse(final Callback<List<TeacherCourse>> callback) {
        BmobQuery<TeacherCourse> query = new BmobQuery<>();
        query.include("mTeacher,mCourse2");
        query.findObjects(new FindListener<TeacherCourse>() {
            @Override
            public void done(List<TeacherCourse> list, BmobException e) {
                if (e == null) {
                    callback.onResult(true, list);
                }
                Log.e(TAG, "getTeacherCourse" + e);
            }
        });
    }
}
