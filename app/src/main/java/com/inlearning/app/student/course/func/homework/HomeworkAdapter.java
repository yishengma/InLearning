package com.inlearning.app.student.course.func.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.inlearning.app.R;
import com.inlearning.app.common.bean.Answer;
import com.inlearning.app.common.bean.Course2;
import com.inlearning.app.common.bean.CourseChapter;
import com.inlearning.app.common.bean.Question;
import com.inlearning.app.student.StudentRuntime;

import java.util.ArrayList;
import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {

    private List<Homework> mHomeworkList;
    private Context mContext;
    private CourseChapter mChapter;

    public HomeworkAdapter(List<Homework> homework, Context context, CourseChapter chapter) {
        mHomeworkList = homework;
        mContext = context;
        mChapter = chapter;
    }

    public interface ClickListener {
        void onAddImage(Homework homework);

        void onDeleteImage(Homework homework);

        void onSaveAnswer(Homework homework);
    }

    private ClickListener mClickListener;

    public HomeworkAdapter setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student_homework_question, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Homework homework = mHomeworkList.get(i);
        final Question question = homework.getQuestion();
        Answer answer = homework.getAnswer();
        if (answer == null) {
            answer = new Answer();
            answer.setQuestion(question);
            answer.setChapter(question.getCourseChapter());
            answer.setStudent(StudentRuntime.getStudent());
            answer.setClassInfo(StudentRuntime.getClassInfo());
            homework.setAnswer(answer);
        }
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
            if (answer.getChoiceAnswers() != null) {
                setCheckBox(viewHolder.mCheckBoxView, answer.getChoiceAnswers());
            }
        } else {
            if (!TextUtils.isEmpty(answer.getImageUrl())) {
                viewHolder.mViewAnswer.setVisibility(View.VISIBLE);
                viewHolder.mAnswerImageView.setVisibility(View.VISIBLE);
                viewHolder.mImageDeleteView.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.itemView.getContext()).load(answer.getImageUrl()).into(viewHolder.mAnswerImageView);
                viewHolder.mAddView.setVisibility(View.GONE);
                viewHolder.mImageDeleteView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mClickListener != null) {
                            mClickListener.onDeleteImage(homework);
                        }
                    }
                });
            } else {
                viewHolder.mViewAnswer.setVisibility(View.GONE);
                viewHolder.mAnswerImageView.setVisibility(View.GONE);
                viewHolder.mImageDeleteView.setVisibility(View.GONE);
                viewHolder.mAddView.setVisibility(View.VISIBLE);
                viewHolder.mAddView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mClickListener != null) {
                            mClickListener.onAddImage(homework);
                        }
                    }
                });
            }
            viewHolder.mCheckBoxView.setVisibility(View.GONE);
            setCheckBox(viewHolder.mCheckBoxView, new ArrayList<String>());
        }
        final Answer finalAnswer = answer;
        final Answer finalAnswer1 = answer;
        if (!TextUtils.isEmpty(mChapter.getDeadLine())) {
            long deadline = Long.valueOf(mChapter.getDeadLine());
            if (deadline < System.currentTimeMillis()) {
                viewHolder.mUploadView.setText("已过期");
                viewHolder.mUploadView.setBackground(mContext.getDrawable(R.drawable.bg_edit_gray_shape));
                viewHolder.mUploadView.setEnabled(false);
            } else {
                if (!TextUtils.isEmpty(answer.getObjectId())) {
                    viewHolder.mUploadView.setText("已提交");
                    viewHolder.mUploadView.setBackground(mContext.getDrawable(R.drawable.bg_edit_gray_shape));
                    viewHolder.mUploadView.setEnabled(false);
                    viewHolder.mImageDeleteView.setEnabled(false);
                    viewHolder.mImageDeleteView.setVisibility(View.GONE);
                    viewHolder.mCheckBoxView.setEnabled(false);
                    setCheckBox(viewHolder.mCheckBoxView, false);
                } else {
                    viewHolder.mImageDeleteView.setVisibility(View.VISIBLE);
                    viewHolder.mImageDeleteView.setEnabled(true);
                    viewHolder.mCheckBoxView.setEnabled(true);
                    viewHolder.mUploadView.setEnabled(true);
                    viewHolder.mUploadView.setBackground(mContext.getDrawable(R.drawable.bg_edit_blue_shape));
                    setCheckBox(viewHolder.mCheckBoxView, true);
                }
            }
        }
        viewHolder.mUploadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (question.getType() == Question.Type.CHOICE_QUESTION) {
                    List<String> answerList = getCheckBoxAnswer(viewHolder.mCheckBoxView);
                    if (answerList.isEmpty()) {
                        Toast.makeText(mContext, "请输入答案", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    finalAnswer.setChoiceAnswers(answerList);
                }
                if (question.getType() == Question.Type.RESPONSE_QUESTION) {
                    if (TextUtils.isEmpty(finalAnswer.getImageUrl())) {
                        Toast.makeText(mContext, "请上传答案", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (mClickListener != null) {
                    homework.setQuestion(question);
                    homework.setAnswer(finalAnswer1);
                    mClickListener.onSaveAnswer(homework);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHomeworkList.size();
    }

    private List<String> getCheckBoxAnswer(LinearLayout linearLayout) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            CheckBox box = (CheckBox) linearLayout.getChildAt(i);
            if (box.isChecked()) {
                list.add(box.getText().toString());
            }
        }
        return list;
    }

    private void setCheckBox(LinearLayout layout, List<String> list) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            CheckBox box = (CheckBox) layout.getChildAt(i);
            if (list.contains(box.getText().toString())) {
                box.setChecked(true);
            } else {
                box.setChecked(false);
            }
        }
    }

    private void setCheckBox(LinearLayout layout, boolean enable) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            CheckBox box = (CheckBox) layout.getChildAt(i);
            box.setEnabled(enable);
        }
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
