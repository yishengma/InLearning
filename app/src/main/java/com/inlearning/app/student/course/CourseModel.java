package com.inlearning.app.student.course;

import android.util.Log;

import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Student;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.teacher.TeacherRuntime;
import com.inlearning.app.teacher.attendclass.ChapterModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CourseModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getCourse(final ClassInfo classInfo, final Callback<List<ClassSchedule>> callback) {
        BmobQuery<ClassSchedule> schedule = new BmobQuery<>();
        schedule.addWhereEqualTo("mClassInfo", classInfo);
        schedule.include("mCourse2,mTeacher");
        schedule.addWhereExists("mCourse2");
        schedule.addWhereExists("mTeacher");


        BmobQuery<Course2> inStuQuery = new BmobQuery<>();
        inStuQuery.addWhereExists("objectId");
        schedule.addWhereMatchesQuery("mCourse2", "Course2", inStuQuery);

        BmobQuery<Teacher> inTeaQuery = new BmobQuery<>();
        inTeaQuery.addWhereExists("objectId");
        schedule.addWhereMatchesQuery("mTeacher", "Teacher", inTeaQuery);

        schedule.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                Log.e("ethan", list + "" + e + "" + classInfo);
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }


    public static void getCourseChapter(ClassSchedule schedule, final ChapterModel.Callback<List<CourseChapter>> callback) {
        BmobQuery<CourseChapter> query = new BmobQuery<>();
        query.addWhereEqualTo("mTeacher", schedule.getTeacher());
        query.addWhereEqualTo("mCourse2", schedule.getCourse2());
        query.findObjects(new FindListener<CourseChapter>() {
            @Override
            public void done(List<CourseChapter> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }
}
