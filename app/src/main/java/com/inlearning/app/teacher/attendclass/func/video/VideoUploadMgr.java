package com.inlearning.app.teacher.attendclass.func.video;


import com.inlearning.app.common.bean.Course;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.teacher.attendclass.ChapterModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import cn.bmob.v3.datatype.BmobFile;

public class VideoUploadMgr {

    public interface UploadListener {
        void onProgress(CourseChapter chapter, int progress);

        void onUploadDone(CourseChapter chapter, BmobFile file);
    }

    private static VideoUploadMgr mUploadMgr;
    private ArrayList<WeakReference<UploadListener>> mReferences;

    private VideoUploadMgr() {
        mReferences = new ArrayList<>();
    }

    public static VideoUploadMgr getInstance() {
        if (mUploadMgr == null) {
            mUploadMgr = new VideoUploadMgr();

        }
        return mUploadMgr;
    }

    public synchronized void addListener(UploadListener listener) {
        mReferences.add(new WeakReference<UploadListener>(listener));
    }

    public synchronized void removeListener(UploadListener listener) {
        Iterator iterator = mReferences.iterator();
        while (iterator.hasNext()) {
            WeakReference<UploadListener> i = (WeakReference<UploadListener>) iterator.next();
            if (i.get() == listener) {
                iterator.remove();
            }
        }
    }

    public synchronized void notifyProgress(CourseChapter chapter, int progess) {
        for (WeakReference<UploadListener> reference : mReferences) {
            if (reference != null && reference.get() != null) {
                reference.get().onProgress(chapter, progess);
            }
        }
    }

    public synchronized void uploadDone(CourseChapter chapter, final BmobFile file) {
        chapter.setVideoFile(file);
        ChapterModel.updateCourseChapter(chapter, new ChapterModel.Callback<CourseChapter>() {
            @Override
            public void onResult(CourseChapter chapter) {
                if (chapter != null) {
                    for (WeakReference<UploadListener> reference : mReferences) {
                        if (reference != null && reference.get() != null) {
                            reference.get().onUploadDone(chapter, file);
                        }
                    }
                }
            }
        });

    }
}
