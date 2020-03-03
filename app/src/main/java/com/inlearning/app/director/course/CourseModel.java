package com.inlearning.app.director.course;

import com.inlearning.app.common.bean.Course2;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CourseModel {

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void saveCourseInfo(final Course2 course, final Callback<Course2> callback) {
        if (course == null) {
            return;
        }
        course.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    course.setObjectId(s);
                    callback.onResult(true, course);
                }
            }
        });
    }

    public static void getCourseList(final Callback<List<Course2>> callback) {
        BmobQuery<Course2> query = new BmobQuery<>();
        query.findObjects(new FindListener<Course2>() {
            @Override
            public void done(List<Course2> list, BmobException e) {
                if (e == null) {
                    callback.onResult(true, list);
                }
            }
        });
    }
}
