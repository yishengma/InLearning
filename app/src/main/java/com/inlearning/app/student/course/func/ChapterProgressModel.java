package com.inlearning.app.student.course.func;

import android.util.Log;

import com.inlearning.app.common.bean.ChapterProgress;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Student;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChapterProgressModel {


    public interface Callback<T> {
        void onResult(T t);
    }

    public static void getVideoStudyProgress(final CourseChapter chapter, final Student student, final Callback<ChapterProgress> callback) {
        final BmobQuery<ChapterProgress> query = new BmobQuery<>();
        query.addWhereEqualTo("mChapter", chapter);
        query.addWhereEqualTo("mStudent", student);
        query.findObjects(new FindListener<ChapterProgress>() {
            @Override
            public void done(List<ChapterProgress> list, BmobException e) {
                Log.e("ethan", e + "BmobException " + chapter + student);
                if (e == null && list.isEmpty() || e == null && !chapter.getVideoFile().getFileUrl().equals(list.get(0).getVideoUrl())) {
                    final ChapterProgress progress = new ChapterProgress();
                    progress.setStudent(student);
                    progress.setChapter(chapter);
                    progress.setVideoUrl(chapter.getVideoFile().getFileUrl());
                    progress.setDone(false);
                    progress.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            progress.setObjectId(s);
                            callback.onResult(progress);
                            Log.e("ethan", e + "progress ");
                        }
                    });
                } else if (e == null && !list.isEmpty()) {
                    callback.onResult(list.get(0));
                    Log.e("ethan", e + "isEmpty");
                }

            }
        });
    }

    public static void updateStudyProgress(ChapterProgress progress, long currentDuration, long duration) {
        if (progress.isDone()) {
            return;
        }
        Log.e("ethan", "updateStudyProgress: "+currentDuration );
        Log.e("ethan", "updateStudyProgress: ."+duration );
        progress.setStudyDuration(currentDuration);
        progress.setDone(Math.abs(duration-currentDuration) <= 1000);
        progress.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }


}
