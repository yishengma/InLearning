package com.inlearning.app.student.course.func.homework;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.common.bean.Teacher;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.util.V;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    private List<Homework> mHomeworkList;
    private Context mContext;

    public HomeworkAdapter(List<Homework> homework, Context context) {
        mHomeworkList = homework;
        mContext = context;
    }

    public interface ClickListener {

        void onAddImage();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student_homework_question, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Homework homework = mHomeworkList.get(i);
        Question question = homework.getQuestion();
        Answer answer = homework.getAnswer();
        if (!TextUtils.isEmpty(question.getQuestionTitle())) {
            viewHolder.mQuesView.setText(question.getQuestionTitle());
            viewHolder.mQuesHintView.setVisibility(View.VISIBLE);
            viewHolder.mQuesView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mQuesHintView.setVisibility(View.GONE);
            viewHolder.mQuesView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(question.getQuestionImage())) {
            Glide.with(viewHolder.itemView.getContext()).load(question.getQuestionImage()).into(viewHolder.mQuesImageView);
            viewHolder.mQuesImageHintView.setVisibility(View.VISIBLE);
            viewHolder.mQuesImageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mQuesImageHintView.setVisibility(View.GONE);
            viewHolder.mQuesImageView.setVisibility(View.GONE);
        }
        if (question.getType() == Question.Type.CHOICE_QUESTION) {
            viewHolder.mViewAnswer.setVisibility(View.GONE);
            viewHolder.mAnswerImageView.setVisibility(View.GONE);
            viewHolder.mImageDeleteView.setVisibility(View.GONE);
            viewHolder.mAddView.setVisibility(View.GONE);
            viewHolder.mCheckBoxView.setVisibility(View.VISIBLE);
        } else {
            if (answer != null && !TextUtils.isEmpty(answer.getImageUrl())) {
                viewHolder.mViewAnswer.setVisibility(View.VISIBLE);
                viewHolder.mAnswerImageView.setVisibility(View.VISIBLE);
                viewHolder.mImageDeleteView.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.itemView.getContext()).load(answer.getImageUrl()).into(viewHolder.mAnswerImageView);
                viewHolder.mAddView.setVisibility(View.GONE);
            } else {
                viewHolder.mViewAnswer.setVisibility(View.GONE);
                viewHolder.mAnswerImageView.setVisibility(View.GONE);
                viewHolder.mImageDeleteView.setVisibility(View.GONE);
                viewHolder.mAddView.setVisibility(View.VISIBLE);
            }
            viewHolder.mCheckBoxView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mHomeworkList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuesHintView;
        private TextView mQuesView;
        private TextView mQuesImageHintView;
        private ImageView mQuesImageView;
        private FrameLayout mViewAnswer;
        private ImageView mAnswerImageView;
        private ImageView mImageDeleteView;
        private TextView mAddView;
        private LinearLayout mCheckBoxView;
        private TextView mUploadView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuesHintView = itemView.findViewById(R.id.tv_question_hint);
            mQuesView = itemView.findViewById(R.id.tv_question);
            mQuesImageHintView = itemView.findViewById(R.id.tv_image_tips);
            mQuesImageView = itemView.findViewById(R.id.imv_question_image);
            mViewAnswer = itemView.findViewById(R.id.view_answer);
            mAnswerImageView = itemView.findViewById(R.id.imv_answer_image);
            mImageDeleteView = itemView.findViewById(R.id.imv_image_delete);
            mAddView = itemView.findViewById(R.id.tv_add_image);
            mCheckBoxView = itemView.findViewById(R.id.view_checkbox);
            mUploadView = itemView.findViewById(R.id.tv_upload);
        }
    }
}
