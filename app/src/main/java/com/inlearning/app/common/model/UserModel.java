package com.inlearning.app.common.model;


import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.Director;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.common.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UserModel {
    private static Director sDirector;
    private static Student sStudent;
    private static Teacher sTeacher;

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void onLogin(@User.Type int userType, String account, String password, final Callback callback) {
        switch (userType) {
            case User.Type.STUDENT:
                BmobQuery<Student> studentBeanBmobQuery = new BmobQuery<>();
                studentBeanBmobQuery.addWhereEqualTo("mAccount", account);
                studentBeanBmobQuery.addWhereEqualTo("mPassword", password);
                studentBeanBmobQuery.include("mClassInfo");
                BmobQuery<ClassInfo> inQuery = new BmobQuery<>();
                inQuery.addWhereExists("objectId");
                studentBeanBmobQuery.addWhereMatchesQuery("mClassInfo", "ClassInfo", inQuery);
                studentBeanBmobQuery.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> list, BmobException e) {

                        if (list == null || list.size() == 0) {
                            sStudent = null;
                        } else {
                            sStudent = list.get(0);
                        }
                        callback.onResult(sStudent);
                    }
                });
                break;
            case User.Type.TEACHER:

                BmobQuery<Teacher> teacherBeanBmobQuery = new BmobQuery<>();
                teacherBeanBmobQuery.addWhereEqualTo("mAccount", account);
                teacherBeanBmobQuery.addWhereEqualTo("mPassword", account);
                teacherBeanBmobQuery.findObjects(new FindListener<Teacher>() {
                    @Override
                    public void done(List<Teacher> list, BmobException e) {
                        if (list == null || list.size() == 0) {
                            sTeacher = null;
                        } else {
                            sTeacher = list.get(0);
                        }
                        callback.onResult(sTeacher);
                    }
                });
                break;
            case User.Type.DIRECTOR:

                BmobQuery<Director> directorBeanBmobQuery = new BmobQuery<>();
                directorBeanBmobQuery.addWhereEqualTo("mAccount", account);
                directorBeanBmobQuery.addWhereEqualTo("mPassword", account);
                directorBeanBmobQuery.findObjects(new FindListener<Director>() {
                    @Override
                    public void done(List<Director> list, BmobException e) {
                        if (list == null || list.size() == 0) {
                            sDirector = null;
                        } else {
                            sDirector = list.get(0);
                        }
                        callback.onResult(sDirector);
                    }
                });
                break;
            default:
                break;
        }
    }

    public static Director getDirector() {
        return sDirector;
    }

    public static Student getStudent() {
        return sStudent;
    }

    public static Teacher getTeacher() {
        return sTeacher;
    }
}
