package com.inlearning.app.teacher.attendclass.func.homework;

import android.text.TextUtils;
import android.util.Log;

import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.util.ThreadMgr;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class QuestionModel {


    public interface Callback<T> {
        void onResult(T t);
    }


    public static void getQuesionList(CourseChapter chapter, final Callback<List<Question>> callback) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.addWhereEqualTo("mCourseChapter", chapter);
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(final List<Question> list, BmobException e) {
                if (e == null) {
                    ThreadMgr.getInstance().postToUIThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResult(list);
                        }
                    });
                }
            }
        });
    }

    public static void deleteQuestion(final Question question, final Callback<Boolean> callback) {
        question.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                question.getCourseChapter().increment("mHomeworkCount", -1);
                question.getCourseChapter().update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(true);
                    }
                });
            }
        });
    }

    public static void addQuestion(final Question question, final Callback<Question> callback) {
        Log.e("ethan", "addQuestion");
        if (TextUtils.isEmpty(question.getQuestionImage())) {
            question.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    question.setObjectId(s);
                    question.getCourseChapter().increment("mHomeworkCount", 1);
                    question.getCourseChapter().update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });

                }
            });
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(question.getQuestionImage()));
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("ethan", "done");
                            question.setQuestionImage(bmobFile.getFileUrl());
                            question.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    question.setObjectId(s);
                                    ThreadMgr.getInstance().postToUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onResult(question);
                                        }
                                    });
                                }
                            });
                            question.getCourseChapter().increment("mHomeworkCount", 1);
                            question.getCourseChapter().update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                        super.onProgress(value);
                        Log.e("ethan", "" + value);
                    }

                    @Override
                    public void doneError(int code, String msg) {
                        super.doneError(code, msg);
                        Log.e("ethan", msg);
                    }
                });
            }
        });
    }

    public static void updateQuestion(final Question question, final Callback<Question> callback) {
        if (question.getQuestionImage().startsWith("http") || TextUtils.isEmpty(question.getQuestionImage())) {
            question.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        ThreadMgr.getInstance().postToUIThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onResult(question);
                            }
                        });
                    }
                }
            });
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(question.getQuestionImage()));
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("ethan", "done");
                            question.setQuestionImage(bmobFile.getFileUrl());
                            question.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    ThreadMgr.getInstance().postToUIThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onResult(question);
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                        super.onProgress(value);
                        Log.e("ethan", "" + value);
                    }

                    @Override
                    public void doneError(int code, String msg) {
                        super.doneError(code, msg);
                        Log.e("ethan", msg);
                    }
                });
            }
        });
    }
}
