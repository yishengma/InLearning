package com.inlearning.app.student.course.func.homework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;

import java.util.ArrayList;
import java.util.List;


public class HomeworkPresenter implements BaseAnswerFunc.ClickListener {

    private HomeworkFuncView mHomeworkFuncView;
    private List<Homework> mHomeworkList;
    private HomeworkAdapter mHomeworkAdapter;
    private CourseChapter mChapter;
    private Context mContext;

    public HomeworkPresenter(final Activity context, View rootView, CourseChapter chapter) {
        mContext = context;
        mHomeworkFuncView = rootView.findViewById(R.id.view_homework_func);
        mHomeworkFuncView.setClickListener(new HomeworkFuncView.ClickListener() {
            @Override
            public void onBack() {
                context.finish();
            }
        });
        mHomeworkList = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(mHomeworkList, context);

        mHomeworkFuncView.getRvHomework().setAdapter(mHomeworkAdapter);
        mChapter = chapter;
        HomeworkModel.getHomework(mChapter, new HomeworkModel.Callback<List<Homework>>() {
            @Override
            public void onResult(List<Homework> homeworkList) {
                mHomeworkList.clear();
                mHomeworkList.addAll(homeworkList);
                mHomeworkAdapter.notifyDataSetChanged();
                mHomeworkFuncView.refreshUI(!mHomeworkList.isEmpty());

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public void show() {
        mHomeworkFuncView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mHomeworkFuncView.setVisibility(View.GONE);
    }


    @Override
    public void onBack() {
        mHomeworkFuncView.showFuncView();
    }
}
