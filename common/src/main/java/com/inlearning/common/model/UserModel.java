package com.inlearning.common.model;

import com.inlearning.common.bean.Director;
import com.inlearning.common.bean.Student;
import com.inlearning.common.bean.Teacher;
import com.inlearning.common.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class UserModel {
    private static User sUser;

    public interface Callback {
        void onResult(User user);
    }

    public static User onLogin(@User.Type int userType, String account, String password, final Callback callback) {
        switch (userType) {
            case User.Type.STUDENT:
                BmobQuery<Student> studentBeanBmobQuery = new BmobQuery<>();
                studentBeanBmobQuery.addWhereEqualTo("mAccount", account);
                studentBeanBmobQuery.addWhereEqualTo("mPassword", password);
                studentBeanBmobQuery.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> list, BmobException e) {
                        if (list == null || list.size() == 0) {
                            sUser = null;
                        } else {
                            sUser = list.get(0);
                            sUser.setType(User.Type.STUDENT);
                        }
                        callback.onResult(sUser);
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
                            sUser = null;
                        } else {
                            sUser = list.get(0);
                            sUser.setType(User.Type.TEACHER);
                        }
                        callback.onResult(sUser);
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
                            sUser = null;
                        } else {
                            sUser = list.get(0);
                            sUser.setType(User.Type.DIRECTOR);
                        }
                        callback.onResult(sUser);
                    }
                });
                break;
            default:
                break;
        }
        return sUser;
    }
}
