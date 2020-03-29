package com.inlearning.app.teacher.attendclass;

import com.inlearning.app.common.bean.CourseChapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterProxy {
    private CourseChapter mChapter;
    private int progress;


    public ChapterProxy(CourseChapter chapter, int progress) {
        mChapter = chapter;
        this.progress = progress;
    }

    public CourseChapter getChapter() {
        return mChapter;
    }

    public void setChapter(CourseChapter chapter) {
        mChapter = chapter;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public static ArrayList transfer(List<CourseChapter> list) {
        ArrayList<ChapterProxy> proxy = new ArrayList<>();
        for (CourseChapter c : list) {
            proxy.add(new ChapterProxy(c, 0));
        }
        return proxy;
    }

    public static ChapterProxy getChapterProxy(List<ChapterProxy> proxies, CourseChapter chapter) {
        for (ChapterProxy proxy : proxies) {
            if (proxy.mChapter.getChapterName().equals(chapter.getChapterName())) {
                return proxy;
            }
        }
        return null;
    }
}
