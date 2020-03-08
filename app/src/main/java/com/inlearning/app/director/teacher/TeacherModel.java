package com.inlearning.app.director.teacher;

import android.util.Log;

import com.inlearning.app.common.bean.Teacher;

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



    public static void updateTeacher(final Teacher teacher, final Callback<Teacher> callback) {
        if (teacher == null) {
            return;
        }
        teacher.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true,teacher);
                }
            }
        });
    }

    public static void addTeacherList(List<Teacher> teachers, final Callback<List<Teacher>> callback) {
        List<BmobObject> list = new ArrayList<BmobObject>(teachers);
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
}
