package com.inlearning.app.teacher.classes;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.teacher.TeacherRuntime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import io.reactivex.internal.operators.single.SingleContains;

public class ClassModel {
    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getClassSchedule(final Callback<List<ClassScheduleProxy>> callback) {
        BmobQuery<ClassSchedule> query = new BmobQuery<>();
        query.include("mClassInfo,mCourse2");
        query.addWhereExists("mClassInfo");
        query.addWhereExists("mCourse2");
        query.addWhereEqualTo("mTeacher", TeacherRuntime.getCurrentTeacher());

        BmobQuery<ClassInfo> inClassQuery = new BmobQuery<>();
        inClassQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mClassInfo", "ClassInfo", inClassQuery);

        BmobQuery<Course2> inCourseQuery = new BmobQuery<>();
        inCourseQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mCourse2", "Course2", inCourseQuery);


        query.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                if (list == null) {
                    callback.onResult(new ArrayList<>());
                    return;
                }
                if (e == null) {
                    callback.onResult(new ArrayList<>());
                    return;
                }
                Map<String, List<ClassSchedule>> map = new HashMap<>();
                for (ClassSchedule schedule : list) {
                    if (map.keySet().contains(schedule.getCourse2().getName())) {
                        map.get(schedule.getCourse2().getName()).add(schedule);
                    } else {
                        map.put(schedule.getCourse2().getName(), new ArrayList<ClassSchedule>(Collections.singletonList(schedule)));
                    }
                }
                List<ClassScheduleProxy> proxies = new ArrayList<>();
                for (String key : map.keySet()) {
                    if (map.get(key) == null) {
                        continue;
                    }
                    proxies.add(new ClassScheduleProxy().setCourse(key).setType(ClassScheduleProxy.ITEM_SEPARATE));
                    for (ClassSchedule schedule : map.get(key)) {
                        proxies.add(new ClassScheduleProxy().setSchedule(schedule).setType(ClassScheduleProxy.ITEM_CLASS_INFO));
                    }
                }
                callback.onResult(proxies);
            }
        });
    }
}
