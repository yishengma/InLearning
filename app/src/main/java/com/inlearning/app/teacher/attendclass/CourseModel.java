package com.inlearning.app.teacher.attendclass;

import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CourseModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getClassSchedule(final Callback<List<ClassSchedule>> callback) {
        //todo
        Teacher teacher = new Teacher();
        teacher.setObjectId("7746c34330");
        BmobQuery<ClassSchedule> scheduleBmobQuery = new BmobQuery<>();
        scheduleBmobQuery.include("mClassInfo");
        scheduleBmobQuery.include("mCourse2");
        scheduleBmobQuery.addWhereEqualTo("mTeacher",teacher);
        scheduleBmobQuery.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });

    }
}
