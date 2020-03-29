package com.inlearning.app.teacher.attendclass;

import com.inlearning.app.common.bean.ClassSchedule;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Teacher;
import com.inlearning.app.teacher.TeacherRuntime;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChapterModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getClassSchedule(final Callback<List<ClassSchedule>> callback) {
        //todo
        Teacher teacher = new Teacher();
        teacher.setObjectId("7746c34330");
        BmobQuery<ClassSchedule> scheduleBmobQuery = new BmobQuery<>();
        scheduleBmobQuery.include("mClassInfo");
        scheduleBmobQuery.include("mCourse2");
        scheduleBmobQuery.addWhereEqualTo("mTeacher", teacher);
        scheduleBmobQuery.findObjects(new FindListener<ClassSchedule>() {
            @Override
            public void done(List<ClassSchedule> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });

    }

    public static void getCourseChapter(Course2 course2, final Callback<List<CourseChapter>> callback) {
        BmobQuery<CourseChapter> query = new BmobQuery<>();
        query.addWhereEqualTo("mTeacher", TeacherRuntime.getCurrentTeacher());
        query.addWhereEqualTo("mCourse2", course2);
        query.findObjects(new FindListener<CourseChapter>() {
            @Override
            public void done(List<CourseChapter> list, BmobException e) {
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }

    public static void deleteCourseChapter(CourseChapter chapter, final Callback<Boolean> callback) {
        chapter.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(true);
                }
            }
        });
    }

    public static void updateCourseChapter(final CourseChapter chapter, final Callback<CourseChapter> callback) {
        chapter.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onResult(chapter);
                }
            }
        });
    }

    public static void addCourseChapter(final CourseChapter chapter, final Callback<CourseChapter> callback) {
        chapter.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    chapter.setObjectId(s);
                    callback.onResult(chapter);
                }
            }
        });
    }
}
