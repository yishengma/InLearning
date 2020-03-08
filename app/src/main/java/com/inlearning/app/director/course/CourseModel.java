package com.inlearning.app.director.course;

import android.util.Log;

import com.inlearning.app.common.bean.Course2;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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


    public static void updateCourse(final Course2 course, final Callback<Course2> callback) {
        if (course == null) {
            return;
        }
        course.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true,course);
                }
            }
        });
    }


    public static void addCourseList(List<Course2> course2s, final Callback<List<Course2>> callback) {
        List<BmobObject> list = new ArrayList<BmobObject>(course2s);
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                Log.e("done", "" + e);
                if (e == null) {
                    callback.onResult(true, null);
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
