package com.inlearning.app.director.person.coursemanager.speciality;

import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.Speciality;
import com.inlearning.app.common.bean.SpecialitySchedule;
import com.inlearning.app.common.bean.Teacher;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SpecialityScheduleModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void addSpecialitySchedule(final SpecialitySchedule schedule, final Callback<SpecialitySchedule> callback) {
        schedule.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    schedule.setObjectId(s);
                    callback.onResult(schedule);
                } else {
                    callback.onResult(null);
                }
            }
        });
    }


    public static void getSpecialitySchedule(Speciality speciality, final Callback<List<SpecialitySchedule>> callback) {
        BmobQuery<SpecialitySchedule> query = new BmobQuery<>();
        query.addWhereEqualTo("mSpeciality", speciality);
        query.include("mSpeciality,mCourse2");
//        query.addWhereExists("mCourse2");
        BmobQuery<Course2> inQuery = new BmobQuery<>();
        inQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mCourse2", "Course2", inQuery);
        query.findObjects(new FindListener<SpecialitySchedule>() {
            @Override
            public void done(List<SpecialitySchedule> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }

    public static void deleteSpecialitySchedule(final SpecialitySchedule schedule, final Callback<SpecialitySchedule> callback) {
        schedule.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(schedule);
                }
            }
        });
    }
}
