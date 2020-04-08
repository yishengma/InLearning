package com.inlearning.app.teacher.classes;

import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.teacher.TeacherRuntime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ClassModel {
    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getClassSchedule(final Callback<List<ClassScheduleProxy>> callback) {
        BmobQuery<ClassSchedule> query = new BmobQuery<>();
        query.include("mClassInfo,mCourse2");
        query.addWhereEqualTo("mTeacher", TeacherRuntime.getCurrentTeacher());
        query.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                Map<String, List<ClassSchedule>> map = new HashMap<>();
                for (ClassSchedule schedule : list) {
                    if (map.keySet().contains(schedule.getCourse2().getName())) {
                        map.get(schedule.getCourse2().getName()).add(schedule);
                    } else {
                        map.put(schedule.getCourse2().getName(), new ArrayList<ClassSchedule>());
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