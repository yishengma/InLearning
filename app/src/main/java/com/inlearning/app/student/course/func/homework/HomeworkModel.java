package com.inlearning.app.student.course.func.homework;

import android.text.TextUtils;
import android.util.Log;

import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.util.ThreadMgr;
import com.inlearning.app.student.StudentRuntime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class HomeworkModel {


    public interface Callback<T> {
        void onResult(T t);
    }


    public static void getHomework(final CourseChapter chapter, final Callback<List<Homework>> callback) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.addWhereEqualTo("mCourseChapter", chapter);
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(final List<Question> list, BmobException e) {
                if (e == null) {
                    getAnswerList(chapter, list, callback);

                }
            }
        });
    }

    private static void getAnswerList(CourseChapter chapter, final List<Question> questions, final Callback<List<Homework>> callback) {
        BmobQuery<Answer> query = new BmobQuery<>();
        query.include("mQuestion");
        query.addWhereEqualTo("mChapter", chapter);
        query.addWhereEqualTo("mStudent", StudentRuntime.getStudent());
        query.addWhereExists("mQuestion");

        BmobQuery<Question> inQuery = new BmobQuery<>();
        inQuery.addWhereExists("objectId");
        query.addWhereMatchesQuery("mQuestion", "Question", inQuery);

        query.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> answers, BmobException e) {
                if (e != null) {
                    return;
                }
                final List<Homework> homeworkList = new ArrayList<>();
                boolean has = false;
                for (Question q : questions) {
                    has = false;
                    for (Answer a : answers) {
                        if (q.getObjectId().equals(a.getQuestion().getObjectId())) {
                            has = true;
                            homeworkList.add(new Homework().setAnswer(a).setQuestion(q));
                            break;
                        }
                    }
                    if (!has) {
                        homeworkList.add(new Homework().setQuestion(q));
                    }
                }
                ThreadMgr.getInstance().postToUIThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(homeworkList);
                    }
                });
            }
        });
    }

    public static void uploadAnswer(final Answer answer, final Callback<Answer> callback) {
        if (answer.getQuestion().getType() == Question.Type.CHOICE_QUESTION) {
            if (!TextUtils.isEmpty(answer.getObjectId())) {
                answer.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        callback.onResult(answer);
                    }
                });
            } else {
                answer.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        answer.setObjectId(s);
                        callback.onResult(answer);
                    }
                });
            }
        }
        if (answer.getQuestion().getType() == Question.Type.RESPONSE_QUESTION) {
            final BmobFile bmobFile = new BmobFile(new File(answer.getImageUrl()));
            ThreadMgr.getInstance().postToSubThread(new Runnable() {
                @Override
                public void run() {
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            answer.setImageUrl(bmobFile.getUrl());
                            if (!TextUtils.isEmpty(answer.getObjectId())) {
                                answer.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        callback.onResult(answer);
                                    }
                                });
                            } else {
                                answer.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        answer.setObjectId(s);
                                        callback.onResult(answer);
                                    }
                                });
                            }
                        }
                    });
                }
            });

        }
    }
}
