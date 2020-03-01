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

public class ClassInfoModel {

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void getStudents(ClassInfo classInfo, final Callback<List<Student>> callback) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("mClassInfo", classInfo);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                Log.e("done",""+e);
                if (e == null && list != null) {
                    callback.onResult(true, list);
                }
            }
        });
    }

    public static void saveStudents(ClassInfo classInfo, List<Student> students) {
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
                Log.e("done",""+e);
            }
        });

    }
}
