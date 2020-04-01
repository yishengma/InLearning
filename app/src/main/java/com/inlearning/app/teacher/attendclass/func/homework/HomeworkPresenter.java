package com.inlearning.app.teacher.attendclass.func.homework;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.inlearning.app.R;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;

import java.util.ArrayList;
import java.util.List;


public class HomeworkPresenter implements BaseQuesFunc.ClickListener {

    private HomeworkFuncView mHomeworkFuncView;
    private List<Question> mQuestions;
    private HomeworkAdapter mHomeworkAdapter;
    private ResponseQuesView mResponseQuesView;
    private ChooseQuesView mChooseQuesView;
    private HomeworkEditView mHomeworkEditView;
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

            @Override
            public void onAddChoiceQues() {
                mHomeworkFuncView.hideFuncView();
                mChooseQuesView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAddResponseQues() {
                mHomeworkFuncView.hideFuncView();
                mResponseQuesView.setVisibility(View.VISIBLE);
            }
        });
        mQuestions = new ArrayList<>();
        mHomeworkAdapter = new HomeworkAdapter(mQuestions, context);
        mHomeworkAdapter.setClickListener(new HomeworkAdapter.ClickListener() {
            @Override
            public void onDeleteClick(Question question) {
                showDeleteDialog(question);
            }

            @Override
            public void onEditClick(Question question) {
                mHomeworkFuncView.hideFuncView();
                mHomeworkEditView.show(question);
            }
        });
        mHomeworkFuncView.getRvHomework().setAdapter(mHomeworkAdapter);
        mResponseQuesView = mHomeworkFuncView.getResponseQuesView();
        mChooseQuesView = mHomeworkFuncView.getChooseQuesView();
        mResponseQuesView.setClickListener(this);
        mChooseQuesView.setClickListener(this);
        mChapter = chapter;
        QuestionModel.getQuesionList(mChapter, new QuestionModel.Callback<List<Question>>() {
            @Override
            public void onResult(List<Question> questions) {
                mQuestions.clear();
                mQuestions.addAll(questions);
                mHomeworkAdapter.notifyDataSetChanged();
                mHomeworkFuncView.refreshUI(!mQuestions.isEmpty());

            }
        });
        mHomeworkEditView = mHomeworkFuncView.getHomeworkEditView();
        mHomeworkEditView.setClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mResponseQuesView.getVisibility() == View.VISIBLE) {
            mResponseQuesView.onActivityResult(requestCode, resultCode, data);
        }
        if (mChooseQuesView.getVisibility() == View.VISIBLE) {
            mChooseQuesView.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mResponseQuesView.getVisibility() == View.VISIBLE) {
            mResponseQuesView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        if (mChooseQuesView.getVisibility() == View.VISIBLE) {
            mChooseQuesView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public void show() {
        mHomeworkFuncView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mHomeworkFuncView.setVisibility(View.GONE);
    }

    @Override
    public void onUpload(final Question question) {
        question.setCourseChapter(mChapter);
        QuestionModel.addQuestion(question, new QuestionModel.Callback<Question>() {
            @Override
            public void onResult(Question question) {
                mQuestions.add(question);
                mHomeworkAdapter.notifyDataSetChanged();
                mHomeworkFuncView.refreshUI(!mQuestions.isEmpty());

                onBack();
            }
        });
    }

    @Override
    public void onBack() {
        mChooseQuesView.setVisibility(View.GONE);
        mResponseQuesView.setVisibility(View.GONE);
        mHomeworkEditView.hide();
        mHomeworkFuncView.showFuncView();
    }

    private void showDeleteDialog(final Question question) {
        final Dialog dialog = new Dialog(mContext, R.style.SimpleDialog);//SimpleDialog
        dialog.setContentView(R.layout.dialog_delete);
        TextView titleView = dialog.findViewById(R.id.tv_title);
        titleView.setText("删除");
        TextView infoView = dialog.findViewById(R.id.tv_content);
        infoView.setText("确定删除该作业？删除后不可恢复");
        TextView cancelView = dialog.findViewById(R.id.tv_cancel);
        TextView confirmView = dialog.findViewById(R.id.tv_confirm);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionModel.deleteQuestion(question, new QuestionModel.Callback<Boolean>() {
                    @Override
                    public void onResult(Boolean aBoolean) {
                        mQuestions.remove(question);
                        mHomeworkAdapter.notifyDataSetChanged();
                        mHomeworkFuncView.refreshUI(!mQuestions.isEmpty());
                    }
                });
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onUpdate(Question question) {
        QuestionModel.updateQuestion(question, new QuestionModel.Callback<Question>() {
            @Override
            public void onResult(Question question) {
                mHomeworkAdapter.notifyDataSetChanged();
            }
        });
    }
}
