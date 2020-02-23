package com.inlearning.app.director.speciality.classinfo;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ClassInfoModel {

    public interface Callback<T> {
        void onResult(boolean suc, T t);
    }

    public static void getStudents(ClassInfo classInfo, final Callback<List<Student>> callback) {
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("mClassInfo", new BmobPointer(classInfo));
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (e == null && list != null) {
                    callback.onResult(true, list);
                }
            }
        });

    }
}
