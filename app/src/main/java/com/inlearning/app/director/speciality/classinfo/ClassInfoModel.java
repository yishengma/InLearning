package com.inlearning.app.director.speciality.classinfo;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;

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

public class ClassInfoModel {

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public interface Callback2<T> {
        void onResult(boolean suc, int ret, T t);
    }

    public static void getStudents(ClassInfo classInfo, final Callback<List<Student>> callback) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("mClassInfo", classInfo);
        query.order("mAccount");
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (e == null && list != null) {
                    ClassInfo bmobClassInfo = new ClassInfo();
                    bmobClassInfo.setObjectId(classInfo.getObjectId());
                    bmobClassInfo.setCount(list.size());
                    bmobClassInfo.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                    callback.onResult(true, list);
                }
            }
        });
    }

    public static void saveStudent(final Student student, final Callback2<Student> callback) {
        student.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    student.setObjectId(s);
                    callback.onResult(true,0, student);
                }else {
                    callback.onResult(false,e.getErrorCode(),null);
                }
            }
        });
    }


    public static void updateStudent(final Student student, final Callback<Student> callback) {
        student.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true, student);
                }
            }
        });
    }

    public static void saveStudents(ClassInfo classInfo, List<Student> students, final Callback<Student> callback) {
        List<BmobObject> list = new ArrayList<>();
        ClassInfo newClassInfo = new ClassInfo();
        newClassInfo.setObjectId(classInfo.getObjectId());
        for (Student student : students) {
            student.setClassInfo(newClassInfo);
            list.add(student);
        }
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    callback.onResult(true, null);
                }
            }
        });

    }


    public static void deleteStudent(Student student, final Callback<Student> callback) {
        student.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true, null);
                }
            }
        });
    }
}
