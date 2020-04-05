package com.inlearning.app.student;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginModel {


    public interface Callback<T> {
        void onResult(T t);
    }

    public static void login(final Callback<Student> callback) {
        //// TODO: 2020-04-05 mock
        BmobQuery<Student> query = new BmobQuery<>();
        query.include("mClassInfo");
        query.addWhereEqualTo("mAccount", "3.116004657E10");
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (e == null && !list.isEmpty()) {
                    callback.onResult(list.get(0));
                }
            }
        });

    }
}
