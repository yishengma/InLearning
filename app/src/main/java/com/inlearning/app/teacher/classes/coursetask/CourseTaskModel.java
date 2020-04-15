package com.inlearning.app.teacher.classes.coursetask;

import android.util.Log;

import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CourseTaskModel {

    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getVideoStudyProgress(final CourseChapter chapter, final ClassInfo classInfo, final Callback<List<ChapterProgress>> callback) {
        final BmobQuery<ChapterProgress> query = new BmobQuery<>();
        query.addWhereEqualTo("mChapter", chapter);
//        query.addWhereEqualTo("mClassInfo",);
        query.include("mStudent");
        query.addWhereExists("mStudent");

        BmobQuery<Student> inQuery = new BmobQuery<>();
        inQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mStudent", "Student", inQuery);

        query.findObjects(new FindListener<ChapterProgress>() {
            @Override
            public void done(List<ChapterProgress> list, BmobException e) {
                Log.e("ethan", e + "" + list.size());
                if (e == null) {
                    callback.onResult(list);
                }
            }
        });
    }
}
