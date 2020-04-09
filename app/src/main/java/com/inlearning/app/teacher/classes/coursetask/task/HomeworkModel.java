package com.inlearning.app.teacher.classes.coursetask.task;

import android.util.Log;

import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.ClassInfo;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.util.ThreadMgr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class HomeworkModel {


    public interface Callback<K, T> {
        void callback(K k, T t);
    }

    public interface Callback2<T> {
        void callback(T t);
    }


    public static void getHomeworkProgress(CourseChapter chapter, ClassInfo classInfo, Callback<List<Question>, List<Integer>> callback) {
        ArrayList<Question> questions = new ArrayList<>();
        ArrayList<Integer> progress = new ArrayList<>();
        ThreadMgr.getInstance().postToSubThread(new Runnable() {
            @Override
            public void run() {
                AtomicInteger atomicInteger = new AtomicInteger(0);
                BmobQuery<Question> query = new BmobQuery<>();
                query.addWhereEqualTo("mCourseChapter", chapter);
                query.findObjects(new FindListener<Question>() {
                    @Override
                    public void done(List<Question> list, BmobException e) {
                        atomicInteger.incrementAndGet();
                        questions.addAll(list);
                    }
                });
                BmobQuery<Answer> answerQuery = new BmobQuery<>();
                String bql = "select *,count(*) from Answer " +
                        "where mChapter = pointer('CourseChapter', '" + chapter.getObjectId() + "') " +
                        "and mClassInfo = pointer('ClassInfo', '" + classInfo.getObjectId() + "') " +
                        "group by mStudent ";
                answerQuery.doStatisticQuery(bql, new QueryListener<JSONArray>() {
                    @Override
                    public void done(JSONArray jsonArray, BmobException e) {

                        try {
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    progress.add(jsonObject.getInt("_count"));
                                }
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                        atomicInteger.incrementAndGet();
                    }
                });
                while (atomicInteger.get() != 2) {

                }
                callback.callback(questions, progress);
                Log.e("ethan", questions.toString() + progress.toString());
            }
        });
    }

    public static void getAnswerByClassInfo(ClassInfo classInfo, Question question, Callback2<List<Answer>> callback) {
        BmobQuery<Answer> query = new BmobQuery<>();
        query.include("mStudent,mChapter");
        query.addWhereEqualTo("mClassInfo", classInfo);
        query.addWhereEqualTo("mQuestion", question);
        query.findObjects(new FindListener<Answer>() {
            @Override
            public void done(List<Answer> list, BmobException e) {
                if (e == null) {
                    callback.callback(list);
                }
                Log.e("ethan", "" + list.size());
            }
        });
    }
}
