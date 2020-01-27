package com.yishengma.inlearning.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yishengma.inlearning.R;
import com.yishengma.inlearning.bean.CourseBean;
import com.yishengma.inlearning.bean.LessonTask;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.ViewHolder> {
    private List<CourseBean> mLessonTasks;
    private OnClickListener mOnClickListener;

    public LessonsAdapter(List<CourseBean> lessonTasks) {
        mLessonTasks = lessonTasks;
    }

    public interface OnClickListener {
        void onClick(CourseBean task);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_class_lession, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        if (mLessonTasks == null) {
            return 0;
        }
        return mLessonTasks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLessonNameView;
        private ImageView mLessonIconView;
        private TextView mLessonStateView;
        private ImageView mTeacherIconView;
        private TextView mTeacherNameView;
        private TextView mProgressTextView;
        private ProgressBar mLessonProgressView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLessonNameView = itemView.findViewById(R.id.lesson_name);
            mLessonIconView = itemView.findViewById(R.id.lesson_icon);
            mLessonStateView = itemView.findViewById(R.id.lesson_state);
            mTeacherIconView = itemView.findViewById(R.id.lesson_teacher_icon);
            mTeacherNameView = itemView.findViewById(R.id.lesson_teacher_name);
            mProgressTextView = itemView.findViewById(R.id.lesson_progress_text);
            mLessonProgressView = itemView.findViewById(R.id.lesson_study_progress);

        }
    }
}
