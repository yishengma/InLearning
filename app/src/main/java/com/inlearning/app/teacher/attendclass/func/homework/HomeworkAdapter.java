package com.inlearning.app.teacher.attendclass.func.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Question;

import java.util.Arrays;
import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    private List<Question> mQuestions;
    private Context mContext;

    public HomeworkAdapter(List<Question> questions, Context context) {
        mQuestions = questions;
        mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homework_question, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Question question = mQuestions.get(i);
        viewHolder.mQuesInputLayout.setHint("题目");
        String questionTitle = question.getQuestionTitle();
        viewHolder.mQuesEditText.setText(TextUtils.isEmpty(questionTitle) ? "如图" : questionTitle);
        viewHolder.mQuesEditText.setEnabled(false);
        String questionImage = question.getQuestionImage();
        if (TextUtils.isEmpty(questionImage)) {
            viewHolder.mImageTipsView.setVisibility(View.GONE);
            viewHolder.mQuesImageView.setVisibility(View.GONE);
        } else {
            viewHolder.mImageTipsView.setVisibility(View.VISIBLE);
            viewHolder.mQuesImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(questionImage).into(viewHolder.mQuesImageView);
        }
        if (question.getType() == Question.Type.CHOICE_QUESTION) {
            viewHolder.mAnswerView.setVisibility(View.VISIBLE);
            viewHolder.mAnswerView.setText(String.format("答案%s", Arrays.toString(question.getChoiceAnswers().toArray())));
        } else {
            viewHolder.mAnswerView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextInputLayout mQuesInputLayout;
        private TextInputEditText mQuesEditText;
        private TextView mImageTipsView;
        private ImageView mQuesImageView;
        private TextView mAnswerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuesInputLayout = itemView.findViewById(R.id.question_layout);
            mQuesEditText = itemView.findViewById(R.id.et_question);
            mImageTipsView = itemView.findViewById(R.id.tv_image_tips);
            mQuesImageView = itemView.findViewById(R.id.imv_question_image);
            mAnswerView = itemView.findViewById(R.id.tv_answer);

        }
    }
}
