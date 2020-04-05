package com.inlearning.app.student.course;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CourseModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getCourse(final ClassInfo classInfo, final Callback<List<ClassSchedule>> callback) {
        BmobQuery<ClassSchedule> schedule = new BmobQuery<>();
        schedule.addWhereEqualTo("mClassInfo", classInfo.getObjectId());
        schedule.include("mCourse2,mTeacher");
        schedule.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                Log.e("ethan",list+""+e+""+classInfo);
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }
}
