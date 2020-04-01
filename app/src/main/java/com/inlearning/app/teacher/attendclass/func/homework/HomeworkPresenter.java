package com.inlearning.app.teacher.attendclass.func.homework;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.Question;

public class HomeworkPresenter {
    private ChooseQuesView mChooseQuesView;
    private ResponseQuesView mResponseQuesView;
    private BaseQuesFunc mQuesFunc;

    private Activity mContext;

    public HomeworkPresenter(Activity context, View rootView) {
        mChooseQuesView = rootView.findViewById(R.id.view_choose_ques_homework_func);
        mResponseQuesView = rootView.findViewById(R.id.view_res_ques_homework_func);
        mResponseQuesView.setActivity(context);
        mChooseQuesView.setActivity(context);
    }

    public void show(@Question.Type int type) {
        if (type == Question.Type.CHOICE_QUESTION) {
            mChooseQuesView.setVisibility(View.VISIBLE);
            mResponseQuesView.setVisibility(View.GONE);
            mQuesFunc = mChooseQuesView;
        } else {
            mChooseQuesView.setVisibility(View.GONE);
            mResponseQuesView.setVisibility(View.VISIBLE);
            mQuesFunc = mResponseQuesView;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mQuesFunc.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mQuesFunc.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
