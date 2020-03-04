package com.inlearning.app.director.teacher;

import android.util.Log;

import com.inlearning.app.common.bean.Teacher;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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
                Log.e(TAG,""+e);
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
                Log.e(TAG,"addTeacher"+e);
            }
        });
    }
}
